package web.planorama.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class GeradorSenhaAdm {
    public static void main(String[] args) {
        // Criamos uma instância do  encoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // A senha para criptografar
        // Como serão 4 adms, cada adm coloca a senha aqui, clica em run e pega a senha criptografada
        // e coloca no data.sql
        String senhaPura = "123"; 
        
        // Gera o hash
        String senhaCriptografada = passwordEncoder.encode(senhaPura);
        
        // Imprime o resultado no console
        System.out.println("Sua senha criptografada é: " + senhaCriptografada);
    }
}
