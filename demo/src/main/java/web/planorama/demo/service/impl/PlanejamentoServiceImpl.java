package web.planorama.demo.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoProgressDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.RegistrarEstudoEntity;
import web.planorama.demo.entity.SessaoEstudoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.mapping.SessaoEstudoMapper;
import web.planorama.demo.repository.MateriaRepository;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.RegistrarEstudoRepository;
import web.planorama.demo.repository.SessaoEstudoRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.PlanejamentoService;

import static web.planorama.demo.enums.PapeisUsuario.*;

@Service
@RequiredArgsConstructor
public class PlanejamentoServiceImpl implements PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;
    private final SessaoEstudoRepository sessaoEstudoRepository;
    private final RegistrarEstudoRepository registrarEstudoRepository;

    private final PlanejamentoMapper mapper;
    private final MateriaPlanejamentoMapper materiaPlanejamentoMapper;
    private final SessaoEstudoMapper sessaoEstudoMapper;

    @Override
    @Transactional
    public PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO) {

        UsuarioEntity criador = pegaUsuarioLogado();
        PlanejamentoEntity planejamentoEntity;

        boolean deveRegenerarCiclo = false;

        if (planejamentoDTO.getId() != null) {
            planejamentoEntity = planejamentoRepository.findById(planejamentoDTO.getId())
                    .orElseThrow(() -> new MyNotFoundException(
                            "Planejamento com ID " + planejamentoDTO.getId() + " não encontrado."));

            // VERIFICA se a carga horária foi alterada.
            if (planejamentoEntity.getHorasDiarias() != planejamentoDTO.getHorasDiarias()) {
                deveRegenerarCiclo = true;
            }

        } else {
            planejamentoEntity = new PlanejamentoEntity();
            planejamentoEntity.setCriador(criador);
            planejamentoEntity.setPlanoArquivado(false);
            boolean isAdmin = criador.getPapeis().stream().anyMatch(papel -> papel.getNome().equals(ADMIN.name()));
            planejamentoEntity.setPreDefinidoAdm(isAdmin);
            deveRegenerarCiclo = true; // Sempre gera o ciclo para um plano novo.
        }

        planejamentoEntity.setNomePlanejamento(planejamentoDTO.getNomePlanejamento());
        planejamentoEntity.setCargo(planejamentoDTO.getCargo());
        planejamentoEntity.setAnoAplicacao(planejamentoDTO.getAnoAplicacao());
        planejamentoEntity.setHorasDiarias(planejamentoDTO.getHorasDiarias());

        if (planejamentoDTO.getId() == null) {
            planejamentoEntity.setDisponibilidade(planejamentoDTO.getDisponibilidade());
            if (planejamentoDTO.getMaterias() != null) {
                List<MateriaPlanejamentoEntity> materiasDoPlanejamento = planejamentoDTO.getMaterias().stream()
                        .map(materiaPlano -> {
                            MateriaPlanejamentoEntity materiaPlanejamento = materiaPlanejamentoMapper
                                    .toMateriaPlanejamentoEntity(materiaPlano);
                            materiaPlanejamento.setNivelConhecimento(materiaPlano.getNivelConhecimento());
                            materiaPlanejamento.setCargaHorariaMateriaPlano(materiaPlano.getCargaHorariaMateriaPlano());
                            materiaPlanejamento.setPlanejamentoEntity(planejamentoEntity);

                            MateriaEntity materiaEntity = materiaRepository.findById(materiaPlano.getIdMateriaDTO())
                                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada."));

                            materiaPlanejamento.setMateriaEntity(materiaEntity);
                            materiaPlanejamento.setPlanejamentoEntity(planejamentoEntity);

                            return materiaPlanejamento;
                        }).collect(Collectors.toList());

                planejamentoEntity.setMaterias(materiasDoPlanejamento);
            }
        }

        var planoSalvo = planejamentoRepository.save(planejamentoEntity);

        if (deveRegenerarCiclo) {
            List<SessaoEstudoEntity> sessoesAntigas = sessaoEstudoRepository.findByPlanejamentoEntity(planoSalvo);
            if (sessoesAntigas != null && !sessoesAntigas.isEmpty()) {
                sessaoEstudoRepository.deleteAllInBatch(sessoesAntigas); // Mais eficiente para listas grandes
            }

            this.gerarCicloDeEstudos(planoSalvo.getId());
        }

        return mapper.toPlanejamentoDTO(planoSalvo);
    }

    @Override
    public PlanejamentoDTO findOne(UUID id) {
        var entity = planejamentoRepository.findById(id).orElseThrow();
        return mapper.toPlanejamentoDTO(entity);
    }

    @Override
    public List<PlanejamentoDTO> findAll() {
        return planejamentoRepository.findAll()
                .stream()
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public List<PlanejamentoDTO> findAllOfAdm() {
        return planejamentoRepository.findAll()
                .stream()
                .filter(plano -> Boolean.TRUE.equals(plano.isPreDefinidoAdm()))
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public List<PlanejamentoDTO> findAllOfEstudante(UsuarioDTO usuarioDTO) {
        UsuarioEntity criador = new UsuarioEntity();
        criador.setId(usuarioDTO.id());

        return planejamentoRepository.findAllByCriador(criador)
                .stream()
                .filter(plano -> !plano.isPlanoArquivado())
                .map(mapper::toPlanejamentoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(UUID id) {
        if (planejamentoRepository.existsById(id)) {
            planejamentoRepository.deleteById(id);
        } else {
            throw new MyNotFoundException("Planejamento não encontrado.");
        }
    }

    @Override
    @Transactional
    public PlanejamentoDTO arquivarPlanoDeEstudos(PlanejamentoDTO planejamentoParaArquivar) {
        PlanejamentoEntity entity = planejamentoRepository.findById(planejamentoParaArquivar.getId())
                .orElseThrow(() -> new MyNotFoundException(
                        "Planejamento com ID " + planejamentoParaArquivar.getId() + " não encontrado."));
        entity.setPlanoArquivado(true);
        PlanejamentoEntity updatedEntity = planejamentoRepository.save(entity);
        return mapper.toPlanejamentoDTO(updatedEntity);
    }

    @Override
    @Transactional
    public PlanejamentoDTO desarquivarPlanoDeEstudos(PlanejamentoDTO planejamentoParaArquivar) {
        PlanejamentoEntity entity = planejamentoRepository.findById(planejamentoParaArquivar.getId())
                .orElseThrow(() -> new MyNotFoundException(
                        "Planejamento com ID " + planejamentoParaArquivar.getId() + " não encontrado."));
        entity.setPlanoArquivado(false);
        PlanejamentoEntity updatedEntity = planejamentoRepository.save(entity);
        return mapper.toPlanejamentoDTO(updatedEntity);
    }

    @Override
    public List<PlanejamentoDTO> findAllPlanejamentoIsArquivado() {

        UsuarioEntity criador = pegaUsuarioLogado();

        return planejamentoRepository.findAllByPlanoArquivado(true)
                .stream()
                .filter(plano -> plano.getCriador() != null && plano.getCriador().getId().equals(criador.getId()))
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<SessaoEstudoDTO> gerarCicloDeEstudos(UUID planejamentoId) {
        PlanejamentoEntity planejamentoEntity = planejamentoRepository.findById(planejamentoId)
                .orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado."));

        if (planejamentoEntity.getMaterias() == null || planejamentoEntity.getMaterias().isEmpty()) {
            return new ArrayList<>();
        }

        int horasSemanais = planejamentoEntity.getHorasDiarias() * planejamentoEntity.getDisponibilidade().size();

        int horaCadaMateria = horasSemanais / planejamentoEntity.getMaterias().size();

        List<MateriaPlanejamentoEntity> materiasDoPlano = planejamentoEntity.getMaterias();

        int horasAtribuidas = 0;
        for (MateriaPlanejamentoEntity materiaPlano : materiasDoPlano) {
            int cargaHorariaCalculada;
            if (materiaPlano.getNivelConhecimento() == 1 || materiaPlano.getNivelConhecimento() == 2) {
                cargaHorariaCalculada = horaCadaMateria;
            } else if (materiaPlano.getNivelConhecimento() == 3) {
                cargaHorariaCalculada = (int) (horaCadaMateria - (horaCadaMateria * 0.2));
            } else {
                cargaHorariaCalculada = (int) (horaCadaMateria - (horaCadaMateria * 0.4));
            }
            materiaPlano.setCargaHorariaMateriaPlano(cargaHorariaCalculada);
            horasAtribuidas += cargaHorariaCalculada;
        }

        int horasRestantes = horasSemanais - horasAtribuidas;
        while (horasRestantes > 0) {
            boolean distribuiuHoras = false;
            for (MateriaPlanejamentoEntity materiaPlano : materiasDoPlano) {
                if (materiaPlano.getNivelConhecimento() <= 2) {
                    materiaPlano.setCargaHorariaMateriaPlano(materiaPlano.getCargaHorariaMateriaPlano() + 1);
                    horasRestantes--;
                    distribuiuHoras = true;
                    if (horasRestantes == 0) {
                        break;
                    }
                }
            }
            if (!distribuiuHoras) {
                materiasDoPlano.get(0)
                        .setCargaHorariaMateriaPlano(materiasDoPlano.get(0).getCargaHorariaMateriaPlano() + 1);
                horasRestantes--;
            }
        }

        List<SessaoEstudoDTO> sessoesGeradas = new ArrayList<>();
        final int DURACAO_BLOCO_MINUTOS = 50; // cada sessão terá 50min obrigatoriamente

        for (MateriaPlanejamentoEntity materiaPlano : materiasDoPlano) {
            int cargaHorariaMateriaMinutos = materiaPlano.getCargaHorariaMateriaPlano() * 60;

            int numeroSessoesMateria = cargaHorariaMateriaMinutos / DURACAO_BLOCO_MINUTOS;

            for (int i = 0; i < numeroSessoesMateria; i++) {
                SessaoEstudoEntity sessao = new SessaoEstudoEntity();

                sessao.setMateriaPlanejamento(materiaPlano);

                sessao.setPlanejamentoEntity(planejamentoEntity);

                sessao.setDuracaoSessao(DURACAO_BLOCO_MINUTOS);

                SessaoEstudoEntity sessaoSalva = sessaoEstudoRepository.save(sessao);

                sessoesGeradas.add(sessaoEstudoMapper.toSessaoEstudoDTO(sessaoSalva));
            }
        }

        Collections.shuffle(sessoesGeradas);

        planejamentoRepository.save(planejamentoEntity);

        return sessoesGeradas;
    }

    @Override
    public PlanejamentoDTO selecionarPlanoPredefinido(UUID idPlanejamento) {
        UsuarioEntity usuarioLogado = pegaUsuarioLogado();

        PlanejamentoEntity planejamentoOriginal = planejamentoRepository.findById(idPlanejamento)
                .orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado para seleção."));

        PlanejamentoEntity planejamentoCopia = new PlanejamentoEntity();

        planejamentoCopia.setNomePlanejamento(planejamentoOriginal.getNomePlanejamento());
        planejamentoCopia.setDisponibilidade(new ArrayList<>(planejamentoOriginal.getDisponibilidade()));
        planejamentoCopia.setCargo(planejamentoOriginal.getCargo());
        planejamentoCopia.setAnoAplicacao(planejamentoOriginal.getAnoAplicacao());
        planejamentoCopia.setHorasDiarias(planejamentoOriginal.getHorasDiarias());
        planejamentoCopia.setPlanoArquivado(planejamentoOriginal.isPlanoArquivado());

        planejamentoCopia.setCriador(usuarioLogado);
        planejamentoCopia.setPlanoArquivado(false);
        planejamentoCopia.setPreDefinidoAdm(false);

        if (planejamentoOriginal.getMaterias() != null) {
            List<MateriaPlanejamentoEntity> materiasCopiadas = planejamentoOriginal.getMaterias().stream()
                    .map(materiaOriginal -> {
                        MateriaPlanejamentoEntity materiaCopia = new MateriaPlanejamentoEntity();

                        materiaCopia.setMateriaEntity(materiaOriginal.getMateriaEntity());
                        materiaCopia.setPlanejamentoEntity(planejamentoCopia);
                        materiaCopia.setNivelConhecimento(materiaOriginal.getNivelConhecimento());
                        materiaCopia.setCargaHorariaMateriaPlano(materiaOriginal.getCargaHorariaMateriaPlano());

                        return materiaCopia;
                    }).collect(Collectors.toList());

            planejamentoCopia.setMaterias(materiasCopiadas);
        }

        return mapper.toPlanejamentoDTO(planejamentoRepository.save(planejamentoCopia));
    }

    public UsuarioEntity pegaUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String emailLogado = ((UserDetails) principal).getUsername();

        UsuarioEntity usuarioLogado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));

        return usuarioLogado;
    }

    @Override
    public List<SessaoEstudoDTO> buscarCicloEstudo(UUID planejamentoId) {
        PlanejamentoEntity planejamentoEntity = planejamentoRepository.findById(planejamentoId)
                .orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado."));

        return sessaoEstudoRepository.findByPlanejamentoEntity(planejamentoEntity)
                .stream()
                .map(sessaoEstudoMapper::toSessaoEstudoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanejamentoProgressDTO> findAllComProgressoByUsuario(UsuarioEntity usuarioEntity) {
        List<PlanejamentoEntity> planejamentosNaoArquivados = planejamentoRepository.findAllByCriador(usuarioEntity)
                .stream()
                .filter(p -> !p.isPlanoArquivado())
                .collect(Collectors.toList());

        List<PlanejamentoProgressDTO> planejamentosComProgresso = new ArrayList<>();

        for (PlanejamentoEntity plano : planejamentosNaoArquivados) {

            // Tive que converter o dia da semana do Java (em inglês) para português
            DayOfWeek diaDaSemanaJava = LocalDate.now().getDayOfWeek();
            String diaDaSemanaFormatado = "";
            switch (diaDaSemanaJava) {
                case MONDAY:
                    diaDaSemanaFormatado = "segunda";
                    break;
                case TUESDAY:
                    diaDaSemanaFormatado = "terca";
                    break;
                case WEDNESDAY:
                    diaDaSemanaFormatado = "quarta";
                    break;
                case THURSDAY:
                    diaDaSemanaFormatado = "quinta";
                    break;
                case FRIDAY:
                    diaDaSemanaFormatado = "sexta";
                    break;
                case SATURDAY:
                    diaDaSemanaFormatado = "sabado";
                    break;
                case SUNDAY:
                    diaDaSemanaFormatado = "domingo";
                    break;
            }

            double metaDiariaHoras = 0;
            if (plano.getDisponibilidade().contains(diaDaSemanaFormatado)) {
                metaDiariaHoras = plano.getHorasDiarias();
            }

            LocalDateTime inicioDoDia = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime fimDoDia = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            List<RegistrarEstudoEntity> registrosDeHoje = registrarEstudoRepository
                    .findAllByMateriaPlanejamento_PlanejamentoEntity_IdAndDataRegistroBetween(plano.getId(),
                            inicioDoDia, fimDoDia);

            double minutosEstudadosHoje = registrosDeHoje.stream()
                    .mapToDouble(RegistrarEstudoEntity::getDuracaoEmMinutos)
                    .sum();

            double horasEstudadasHoje = minutosEstudadosHoje / 60.0;

            int progressoPorcentagem = 0;
            if (metaDiariaHoras > 0) {
                double horasParaCalcular = Math.min(horasEstudadasHoje, metaDiariaHoras);
                progressoPorcentagem = (int) ((horasParaCalcular / metaDiariaHoras) * 100);
            }

            boolean isConcluido = verificarSePlanoEstaConcluido(plano.getId());

            planejamentosComProgresso.add(
                    new PlanejamentoProgressDTO(
                            mapper.toPlanejamentoDTO(plano),
                            progressoPorcentagem,
                            isConcluido));
        }

        return planejamentosComProgresso;
    }

    @Override
    public boolean verificarSePlanoEstaConcluido(UUID planejamentoId) {
        PlanejamentoEntity planejamento = planejamentoRepository.findById(planejamentoId)
                .orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado."));

        List<MateriaPlanejamentoEntity> materiasDoPlano = planejamento.getMaterias();

        if (materiasDoPlano.isEmpty()) {
            return false;
        }

        // Itera sobre cada matéria do plano
        for (MateriaPlanejamentoEntity materiaPlano : materiasDoPlano) {

            int totalMinutosEstudados = registrarEstudoRepository
                    .findAllByMateriaPlanejamento_Id(materiaPlano.getId())
                    .stream()
                    .mapToInt(r -> ((RegistrarEstudoEntity) r).getDuracaoEmMinutos()) // faz cast, se o tipo for mais
                                                                                      // genérico
                    .sum();
            int cargaHorariaPlanejadaEmMinutos = materiaPlano.getCargaHorariaMateriaPlano() * 60;

            // Se a carga horária planejada for 0, ignore esta matéria na verificação.
            if (cargaHorariaPlanejadaEmMinutos <= 0) {
                continue;
            }

            int minutosRestantes = cargaHorariaPlanejadaEmMinutos - totalMinutosEstudados;

            if (minutosRestantes >= 50) {
                return false;
            }
        }

        return true;
    }

    @Override
    public PlanejamentoDTO refazerPlanejamento(UUID idPlanejamento) {
        PlanejamentoEntity planejamento = planejamentoRepository.findById(idPlanejamento)
                .orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado."));

        List<SessaoEstudoEntity> sessoesAntigas = sessaoEstudoRepository.findByPlanejamentoEntity(planejamento);
        if (sessoesAntigas != null && !sessoesAntigas.isEmpty()) {
            sessaoEstudoRepository.deleteAllInBatch(sessoesAntigas); // deleteAllInBatch é mais eficiente
        }

        this.gerarCicloDeEstudos(planejamento.getId());

        return mapper.toPlanejamentoDTO(planejamento);

    }
}
