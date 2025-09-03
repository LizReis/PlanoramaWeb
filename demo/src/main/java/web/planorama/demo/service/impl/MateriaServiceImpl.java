package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.repository.MateriaRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.MateriaService;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService{

    private final MateriaRepository materiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaMapper mapper;
    
    @Override
    public MateriaDTO save(String nomeMateria, List<AssuntoEntity> listaAssuntos) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if(principal instanceof UserDetails){
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        }else{
            emailUsuarioLogado = principal.toString();
        }

        UsuarioEntity usuarioCriador = usuarioRepository.findByEmail(emailUsuarioLogado).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        MateriaEntity novaMateriaEntity = new MateriaEntity();
        novaMateriaEntity.setNomeMateria(nomeMateria);
        novaMateriaEntity.setCriadoPor(usuarioCriador);

        //Fazemos um for para pegar cada assunto na listaAssunto e vai salvando no List
        for(AssuntoEntity nomeAssunto : listaAssuntos){
            nomeAssunto.setMateriaEntity(novaMateriaEntity);
            novaMateriaEntity.getListaAssuntos().add(nomeAssunto);
        }

        materiaRepository.save(novaMateriaEntity);
        return mapper.toMateriaDTO(novaMateriaEntity);
    }

    @Override
    public MateriaDTO findById(UUID id) {
       var entity = materiaRepository.findById(id).orElseThrow();
       return mapper.toMateriaDTO(entity);
    }

    @Override
    public List<MateriaDTO> findAll() {
        return materiaRepository.findAll()
                .stream()
                .map(mapper::toMateriaDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if(materiaRepository.existsById(id)){
            materiaRepository.deleteById(id);
        }
    }

    

}
