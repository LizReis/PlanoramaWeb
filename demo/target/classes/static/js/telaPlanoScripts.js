//SCRIPT PARA COLOCAR AS MATÉRIAS DINÂMICAMENTE
//NO GRÁFICO ROSCA, BASICAMENTE, TEM QUE VER COMO 
//VAI PEGAR OS DADOS NO BANCO DE DADOS, MAS DEIXEI AQ UM EXEMPLO PRONTO SÓ PARA APARECER NA TELA


document.addEventListener("DOMContentLoaded", function () {
    console.log("Script JS carregado com sucesso");
    //materias é um array de objetos e cada objeto tem 
    //nome e o tempo de duração
    let materias = [
        { nome: "Web", tempo: 50 },
        { nome: "Engenharia de SW", tempo: 60 },
        { nome: "Banco de Dados", tempo: 50 },
        { nome: "Segurança da Informação", tempo: 60 },
        { nome: "Inglês", tempo: 50 },
        { nome: "Sistemas Distribuidos", tempo: 60 },
        { nome: "Português", tempo: 50 },
        { nome: "Matemática", tempo: 60 },

    ];
    //cores é um array de cores para alterar 
    let cores = ["#7e5686", "#e8f9a2", "#ba3c3d", "#a5aad9", "#f8a13f", "#CAFFD9"];
    // o .reduce() soma o tempo de cada matéria e acumula na 
    //variável soma e depois quarda esse resultado
    // em tempoTotal
    let tempoTotal = materias.reduce((soma, materiaAtual) => soma + materiaAtual.tempo, 0);

    let tempoAtual = 0;
    let gradient = "";

    let partesDoGradiente = [];

    //muda o span para mostrar o tempo total no centro do gráfico
    document.getElementById("spanHoraTotalGrafico").innerHTML = tempoTotal + "h";

    materias.forEach((materia, index) => {
        let inicio = (tempoAtual / tempoTotal) * 100;
        let fim = ((tempoAtual + materia.tempo) / tempoTotal) * 100;
        let cor = cores[index % cores.length];

        partesDoGradiente.push(`${cor} ${inicio}% ${fim}%`);
        tempoAtual += materia.tempo;
    });

    document.getElementById("grafico").style.background = `conic-gradient(${partesDoGradiente.join(", ")})`;
});


