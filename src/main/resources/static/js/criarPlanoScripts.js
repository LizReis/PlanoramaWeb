//SCRIPT PARA SABER QUAL DIAS FORAM SELECIONADOS PARA A DISPONIBILIDADE DO USUÁRIO

document.querySelectorAll(".dia").forEach(dia => {
    dia.addEventListener("click", () => {
        dia.classList.toggle("selecionado");
    });
});

//SCRIPT PARA PEGAR OS DIAS QUE O USUÁRIO SELECIONOU 
//pego os dias que foram selecionados e converto em um array de dias
//o .map() vai passar por cada dia selecionado e pegar o nome do dia
//d é o dia atual que ele está navegando d.dataset.dia pega o nome do dia em data-dia no html
let diasSelecionados = Array.from(document.querySelectorAll(".dia.selecionado")).map(d => d.dataset.dia);

console.log(diasSelecionados);

//-------------------------------------------------------------

//SCRIPT PARA SABER QUAIS MATÉRIAS FORAM SELECIONADAS PARA O PLANO
//QUE O USUÁRIO ACABOU DE CRIAR 
document.querySelectorAll(".materia").forEach(materia => {
    materia.addEventListener("click", () => {
        materia.classList.toggle("selecionado");
    });
});

//SCRIPT PARA SABER QUAIS MATÉRIAS FORAM SELECIONADAS PELO USUÁRIO
//É O MESMO ESQUEMA DOS DIAS DA SEMANA ACIMA

let materiasSelecionadas = Array.from(document.querySelectorAll(".materia.selecionado")).map(m => m.dataset.materia);

console.log(materiasSelecionadas);
//--------------------------------------------------------------


