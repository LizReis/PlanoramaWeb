package web.planorama.demo.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.AdministradorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdministradorDTO save(AdministradorDTO administradorDTO) {
        if (repository.existsByEmail(administradorDTO.email())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        } else {
            var entity = mapper.toAdministradorEntity(administradorDTO);

            String senhaCriptografada = passwordEncoder.encode(administradorDTO.senha());
            entity.setSenha(senhaCriptografada);

            var savedEntity = repository.save(entity);

            return mapper.toAdministradorDTO(savedEntity);
        }
    }

    @Override
    public AdministradorDTO findOne(UUID id) {
        var entity = repository.findById(id).orElseThrow();
        return mapper.toAdministradorDTO((AdministradorEntity) entity);
    }

    @Override
    public List<AdministradorDTO> findAll() {
        return repository.findAll()
                .stream()
                .filter(AdministradorEntity.class::isInstance)
                .map(AdministradorEntity.class::cast)
                .map(mapper::toAdministradorDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    public void alterarDadoUsuario(UUID usuarioParaAlterar, String novoNomeUsuario, String novoEmailUsuario, String senhaADM) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;


        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails)principal).getUsername();
        }else{
            emailUsuarioLogado = principal.toString();
        }

        AdministradorEntity admin = (AdministradorEntity) repository.findByEmail(emailUsuarioLogado).orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if(!passwordEncoder.matches(senhaADM, admin.getSenha())){
            throw new RuntimeException("A senha atual ADM está incorreta");
        }

        UsuarioEntity usuarioSelecionado = repository.findById(usuarioParaAlterar).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        
        usuarioSelecionado.setNome(novoNomeUsuario);
        usuarioSelecionado.setEmail(novoEmailUsuario);
        repository.save(usuarioSelecionado);
    }
}
