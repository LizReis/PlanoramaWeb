//SCRIPT PARA O TEXTO E IMAGEM DA TELA INCIAL
//FAZ ELA SURGIR NA TELA---------------------
//SCRIPT QUE APARECE A DIV QUE POSSUI O BOTAO LOGIN INICIAL
let imagemInicial = document.querySelector("img");
let paragInicial = document.querySelectorAll("p");
let h2Inicial = document.querySelector("h2");
let divBotaoTexto = document.getElementById("divBotaoTexto");

window.addEventListener("load", function (){
    imagemInicial.classList.add("aparecer");
    h2Inicial.classList.add("aparecer");
    paragInicial.forEach(paragrafo => {
        paragrafo.classList.add("aparecer");
    });

    divBotaoTexto.style.visibility = "visible";
});


