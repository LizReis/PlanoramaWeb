# 📚 Planorama - Sistema de Planejamento Acadêmico

O **Planorama** é um sistema web projetado para auxiliar estudantes no **planejamento e acompanhamento dos estudos**.  
A plataforma permite criar **planos de estudo personalizados**, registrar sessões e visualizar estatísticas de desempenho.

Desenvolvido em **Java + Spring Boot** com **Thymeleaf** para renderização no servidor.

---

## ✨ Funcionalidades

### 👩‍🎓 Para Estudantes
- 🔐 **Autenticação Segura**: Cadastro e login com controle de acesso.  
- 📝 **Criação de Planejamentos**: Defina nome, período (início e fim) e organize sua rotina.  
- 📚 **Gerenciamento de Matérias e Assuntos**: Estruture cada plano em tópicos de estudo.  
- ⏱ **Registro de Sessões de Estudo**: Salve tempo dedicado e anotações por assunto.  
- 📊 **Acompanhamento de Progresso**: Visualize barras de progresso por matéria e plano.  
- 📈 **Painel de Desempenho**: Gráficos e estatísticas de tempo de estudo por matéria.  
- ⚙️ **Gerenciamento de Conta**: Atualize nome, e-mail e foto de perfil.  

### 🛠 Para Administradores
- 📋 **Dashboard Administrativo**: Visão geral do sistema.  
- 👥 **Gerenciamento de Usuários**: Listar, editar e remover contas.  
- 🗂 **Gerenciamento de Planos**: Monitorar todos os planejamentos criados.  

---

## 🏗 Arquitetura

A aplicação segue o padrão **MVC (Model-View-Controller)**.  

### 🔧 Backend
- [Spring Boot](https://spring.io/projects/spring-boot) → Framework principal.  
- [Spring Security](https://spring.io/projects/spring-security) → Autenticação e autorização.  
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) → Persistência de dados.  
- [Hibernate](https://hibernate.org/) → Implementação JPA.  
- [H2 Database](https://www.h2database.com/) → Banco em memória (dev/teste).  
- [Maven](https://maven.apache.org/) → Build e gerenciamento de dependências.  

### 🎨 Frontend
- [Thymeleaf](https://www.thymeleaf.org/) → Templates dinâmicos no servidor.  
- **HTML + CSS + JavaScript** → Estrutura, estilo e interatividade.  

---
 
## 🚀 Como Executar

### Passos
```sh
# Clone ou baixe o projeto
git clone <url-do-repositorio>

# Acesse a pasta demo (exemplo)
cd C:/Users/usuario/planoramaweb/PlanoramaWeb/demo/

# Execute o projeto
mvn spring-boot:run
```
---

### Guia de Uso da Aplicação
- ☕ **Após a inicialização, acesse a aplicação no navegador:**  
- **👉 http://localhost:8081**  


