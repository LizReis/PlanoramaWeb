package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.UsuarioDTO;

public interface UsuarioService {
    
    UsuarioDTO save(UsuarioDTO usuarioDTO);
    UsuarioDTO findOne(UUID id);
    UsuarioDTO findByEmail(String email);
    List<UsuarioDTO> findAll();
    List<UsuarioDTO> findAllEstudantes();
    List<UsuarioDTO> findAllAdmins();
    void remove(UUID id);

    void alterarDadoUsuario(UUID usuarioParaAlterar, String novoNomeUsuario, String novoEmailUsuario, String senhaADM);

    void alterarFotoUsuario(String emailUsuarioLogado, String novaFoto);
    void alterarSenha(String senhaAtual, String novaSenha);
    void alterarEmail(String novoEmail, String senhaAtual);
    void alterarNomeUsuario(String novoNome, String senhaAtual);
    void alterarDescricao(String novaDescricao, String senhaAtual);
}
