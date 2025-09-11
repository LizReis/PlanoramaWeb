package web.planorama.demo.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.PapelEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.exceptions.RecursoDuplicadoException;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.PapelRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.UsuarioService;

import static web.planorama.demo.enums.PapeisUsuario.*;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final PapelRepository papelRepository;

    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        var usuarioDB = usuarioRepository.findByEmail(usuarioDTO.email()).orElse(null);

        if ((usuarioDTO.id() != null && usuarioDB != null && !usuarioDTO.id().equals(usuarioDB.getId()))
                || (usuarioDTO.id() == null && usuarioDB != null)) {
            throw new RecursoDuplicadoException("E-mail duplicado");
        }

        List<PapelEntity> papeis = null;
        if (usuarioDTO.id() == null) {
            papeis = new ArrayList<>();

            papeis.add(papelRepository.findById(ESTUDANTE.getId()).orElseThrow());
            if (usuarioRepository.count() == 0) {
                papeis.add(papelRepository.findById(ADMIN.getId()).orElseThrow());
            }
        }
         
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());

        var usuarioParaSalvar = usuarioMapper.toUsuarioEntity(usuarioDTO, papeis);
        usuarioParaSalvar.setSenha(senhaCriptografada);

        usuarioRepository.save(usuarioParaSalvar);

        return usuarioMapper.toUsuarioDTO(usuarioParaSalvar);
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toUsuarioDTO)
                .toList();
    }

    @Override
    public List<UsuarioDTO> findAllEstudantes() {
        return usuarioRepository.findByPapeisNome("ESTUDANTE")
                .stream()
                .map(usuarioMapper::toUsuarioDTO)
                .toList();
    }

    @Override
    public List<UsuarioDTO> findAllAdmins() {
        return usuarioRepository.findByPapeisNome("ADMIN")
                .stream()
                .map(usuarioMapper::toUsuarioDTO)
                .toList();
    }

    @Override
    public UsuarioDTO findByEmail(String email) {

        var usuarioEntity = usuarioRepository.findByEmail(email).orElse(null);
        if (usuarioEntity == null) {
            return null;
        }

        return usuarioMapper.toUsuarioDTO(usuarioEntity);
    }

    @Override
    public UsuarioDTO findOne(UUID id) {
        var entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));
        return usuarioMapper.toUsuarioDTO(entity);
    }

    @Override
    public void remove(UUID id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new MyNotFoundException("Usuário não encontrado para remoção");
        }
    }

    @Override
    public void alterarDadoUsuario(UUID usuarioParaAlterar, String novoNomeUsuario, String novoEmailUsuario,
            String senhaADM) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        } else {
            emailUsuarioLogado = principal.toString();
        }

        UsuarioEntity adminLogado = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if (!passwordEncoder.matches(senhaADM, adminLogado.getSenha())) {
            throw new RuntimeException("A senha atual ADM está incorreta");
        }

        UsuarioEntity usuarioSelecionado = usuarioRepository.findById(usuarioParaAlterar)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // if(usuarioSelecionado.getPapeis().getFirst().equals(ADMIN))

        usuarioSelecionado.setNome(novoNomeUsuario);
        usuarioSelecionado.setEmail(novoEmailUsuario);
        usuarioRepository.save(usuarioSelecionado);
    }

    @Override
    public void alterarSenha(String senhaAtual, String novaSenha) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        } else {
            emailUsuarioLogado = principal.toString();
        }

        UsuarioEntity estudante = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        // Verifica se a senha atual é igual a senha que está no banco de dados
        if (!passwordEncoder.matches(senhaAtual, estudante.getSenha())) {
            throw new RuntimeException("A senha atual está incorreta");
        }

        // Pega a senha nova digitada e criptografa
        String novaSenhaCriptografada = passwordEncoder.encode(novaSenha);

        estudante.setSenha(novaSenhaCriptografada);
        usuarioRepository.save(estudante);
    }

    @Override
    public void alterarEmail(String novoEmail, String senhaAtual) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        } else {
            emailUsuarioLogado = principal.toString();
        }

        UsuarioEntity estudante = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if (!passwordEncoder.matches(senhaAtual, estudante.getSenha())) {
            throw new RuntimeException("A senha atual está incorreta");
        }

        estudante.setEmail(novoEmail);
        usuarioRepository.save(estudante);
    }

    @Override
    public void alterarNomeUsuario(String novoNome, String senhaAtual) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        } else {
            emailUsuarioLogado = principal.toString();
        }

        UsuarioEntity estudante = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if (!passwordEncoder.matches(senhaAtual, estudante.getSenha())) {
            throw new RuntimeException("A senha atual está incorreta");
        }

        estudante.setNome(novoNome);
        usuarioRepository.save(estudante);
    }

    @Override
    public void alterarDescricao(String novaDescricao, String senhaAtual) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        } else {
            emailUsuarioLogado = principal.toString();
        }

        UsuarioEntity estudante = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        if (!passwordEncoder.matches(senhaAtual, estudante.getSenha())) {
            throw new RuntimeException("A senha atual está incorreta");
        }

        estudante.setDescricaoUsuario(novaDescricao);
        usuarioRepository.save(estudante);
    }

    @Override
    public void alterarFotoUsuario(String emailUsuarioLogado, String novaFoto) {
        UsuarioEntity estudante = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));

        estudante.setFotoUsuario(novaFoto);
        usuarioRepository.save(estudante);
    }

}