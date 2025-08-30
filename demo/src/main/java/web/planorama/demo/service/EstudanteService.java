package web.planorama.demo.service;

import web.planorama.demo.dto.EstudanteDTO;

import java.util.List;
import java.util.UUID;


public interface EstudanteService {

    EstudanteDTO save(EstudanteDTO estudanteDTO);
    EstudanteDTO findOne(UUID id);
    List<EstudanteDTO> findAll();
    void remove(UUID id);

    EstudanteDTO findByEmail(String email);

    void alterarFotoUsuario(String emailUsuarioLogado, String novaFoto);
    void alterarSenha(String senhaAtual, String novaSenha);
    void alterarEmail(String novoEmail, String senhaAtual);
    void alterarNomeUsuario(String novoNome, String senhaAtual);
    void alterarDescricao(String novaDescricao, String senhaAtual);
}
