package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.EstudanteRepository;
import web.planorama.demo.service.EstudanteService;

@Service
@RequiredArgsConstructor
public class EstudanteServiceImpl implements EstudanteService{

    private final EstudanteRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EstudanteDTO save(EstudanteDTO estudanteDTO) {
        if (repository.existsByEmail(estudanteDTO.email())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        } else {
            var entity = mapper.toEstudanteEntity(estudanteDTO);

            String senhaCriptograda = passwordEncoder.encode(estudanteDTO.senha());
            entity.setSenha(senhaCriptograda);

            var savedEntity = repository.save(entity);

            return mapper.toEstudanteDTO(savedEntity);
        }
    }

    @Override
    public EstudanteDTO findOne(UUID id) {
        var entity = repository.findById(id).orElseThrow();
        return mapper.toEstudanteDTO((EstudanteEntity) entity);
    }

    @Override
    public List<EstudanteDTO> findAll() {
        return repository.findAll()
                .stream()
                .filter(EstudanteEntity.class::isInstance)
                .map(EstudanteEntity.class::cast)
                .map(mapper::toEstudanteDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
    
    @Override
    public EstudanteDTO findByEmail(String email) {

        var estudanteEntity = repository.findByEmail(email).orElse(null); 
        if (estudanteEntity == null) {
            return null;
        }

        return mapper.toEstudanteDTO(estudanteEntity);
    }

    @Override
    public void alterarSenha(String senhaAtual, String novaSenha) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if(principal instanceof UserDetails){
            emailUsuarioLogado = ((UserDetails)principal).getUsername();
        }else{
            emailUsuarioLogado = principal.toString();
        }

        EstudanteEntity estudante = (EstudanteEntity) repository.findByEmail(emailUsuarioLogado).orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        //Verifica se a senha atual é igual a senha que está no banco de dados
        if(!passwordEncoder.matches(senhaAtual, estudante.getSenha())){
            throw new RuntimeException("A senha atual está incorreta");
        }

        //Pega a senha nova digitada e criptografa
        String novaSenhaCriptografada = passwordEncoder.encode(novaSenha);

        estudante.setSenha(novaSenhaCriptografada);
        repository.save(estudante);
    }

    @Override
    public void alterarEmail(String novoEmail, String senhaAtual) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        }else{
            emailUsuarioLogado = principal.toString();
        }

        EstudanteEntity estudante = (EstudanteEntity) repository.findByEmail(emailUsuarioLogado).orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if(!passwordEncoder.matches(senhaAtual, estudante.getSenha())){
            throw new RuntimeException("A senha atual está incorreta");
        }

        estudante.setEmail(novoEmail);
        repository.save(estudante);
    }

    @Override
    public void alterarNomeUsuario(String novoNome, String senhaAtual) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails)principal).getUsername();
        }else{
            emailUsuarioLogado = principal.toString();
        }

        EstudanteEntity estudante = (EstudanteEntity) repository.findByEmail(emailUsuarioLogado).orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if(!passwordEncoder.matches(senhaAtual, estudante.getSenha())){
            throw new RuntimeException("A senha atual está incorreta");
        }

        estudante.setNome(novoNome);
        repository.save(estudante);
    }

    @Override
    public void alterarDescricao(String novaDescricao, String senhaAtual) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails)principal).getUsername();
        }else{
            emailUsuarioLogado = principal.toString();
        }

        EstudanteEntity estudante = (EstudanteEntity) repository.findByEmail(emailUsuarioLogado).orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if(!passwordEncoder.matches(senhaAtual, estudante.getSenha())){
            throw new RuntimeException("A senha atual está incorreta");
        }

        estudante.setDescricaoUsuario(novaDescricao);
        repository.save(estudante);
    }
}
