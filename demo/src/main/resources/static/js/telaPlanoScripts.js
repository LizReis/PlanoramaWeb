document.addEventListener('DOMContentLoaded', function () {

    console.log("Dados recebidos do backend:", cicloData);

    const graficoContainer = document.getElementById('grafico');
    const containerDisciplinas = document.querySelector('.containerDisciplinas');

    // Verifica se os dados do ciclo e os elementos existem
    if (typeof cicloData === 'undefined' || cicloData.length === 0) {
        console.log("Nenhuma sessão de estudo para exibir no gráfico.");
        graficoContainer.innerHTML += '<p class="aviso-sem-dados">Não há sessões para exibir. Verifique a carga horária das matérias.</p>';
        return;
    }

    // 1. Agrupar sessões por matéria para calcular o tempo total de cada uma
    const materiasAgrupadas = cicloData.reduce((acc, sessao) => {
        // AJUSTE AQUI: Ler de dentro do materiaDTO
        const nome = sessao.materiaDTO.nomeMateria;
        acc[nome] = (acc[nome] || 0) + sessao.duracaoSessao;
        return acc;
    }, {});

    const tempoTotalMinutos = cicloData.reduce((total, sessao) => total + sessao.duracaoSessao, 0);

    // 2. Mapear cores consistentes para cada matéria
    const coresMaterias = {};
    const paletaDeCores = ['#4CAF50', '#2196F3', '#FFC107', '#F44336', '#9C27B0', '#009688', '#FF5722', '#795548'];
    let corIndex = 0;
    for (const nomeMateria in materiasAgrupadas) {
        coresMaterias[nomeMateria] = paletaDeCores[corIndex % paletaDeCores.length];
        corIndex++;
    }

    // 3. Construir a string do CSS conic-gradient
    let gradientString = 'conic-gradient(';
    let acumulado = 0;
    const espacoEntreFatias = 0.4; // Aumentei um pouco para ficar mais visível
    // Pega a cor de fundo definida no CSS. O padrão é 'white' se não encontrar.
    const corDeFundo = getComputedStyle(graficoContainer).backgroundColor || 'white';

    for (const nomeMateria in materiasAgrupadas) {
        const duracaoMateria = materiasAgrupadas[nomeMateria];
        const cor = coresMaterias[nomeMateria];
        let porcentagem = (duracaoMateria / tempoTotalMinutos) * 100;

        // Garante que a fatia não seja menor que o espaço
        if (porcentagem <= espacoEntreFatias) {
            // Se for muito pequena, desenha sem espaço
            gradientString += `${cor} ${acumulado}% ${acumulado + porcentagem}%, `;
        } else {
            // Desenha o espaço com a cor de fundo
            gradientString += `${corDeFundo} ${acumulado}% ${acumulado + espacoEntreFatias}%, `;
            // Desenha a fatia colorida no restante do espaço
            gradientString += `${cor} ${acumulado + espacoEntreFatias}% ${acumulado + porcentagem}%, `;
        }

        // O acumulado avança a porcentagem total para a próxima fatia
        acumulado += porcentagem;
    }
    // Remove a última vírgula e espaço e fecha o parêntese
    gradientString = gradientString.slice(0, -2) + ')';

    // 4. Aplicar o gradiente ao gráfico
    graficoContainer.style.background = gradientString;

    // 5. Limpar a lista de disciplinas estática e criar a legenda dinâmica
    const divDisciplinaContainer = containerDisciplinas.querySelector('.divDisciplina');
    divDisciplinaContainer.innerHTML = ''; // Limpa o conteúdo estático

    const materiasCompletas = cicloData.reduce((acc, sessao) => {
        // AJUSTE AQUI: Ler de dentro do materiaDTO
        const nome = sessao.materiaDTO.nomeMateria;
        if (!acc[nome]) {
            acc[nome] = {
                // AJUSTE AQUI: Ler de dentro do materiaDTO
                idMateria: sessao.materiaDTO.id,
                duracaoTotal: 0,
                nomeMateria: nome
            };
        }
        acc[nome].duracaoTotal += sessao.duracaoSessao;
        return acc;
    }, {});

    // Agora iteramos sobre os dados agrupados
    for (const nomeMateria in materiasCompletas) {
        const materiaInfo = materiasCompletas[nomeMateria];
        const cor = coresMaterias[nomeMateria];

        const url = `/registrar-estudo/${materiaInfo.idMateria}`;

        const cardHtml = `
        <a class="abrir-modal" href="${url}">
            <div class="cardDisciplina" 
             style="border-left: 5px solid ${cor}; cursor: pointer;" 
             data-materia-id="${materiaInfo.idMateria}">
            <div class="divDisciplinaNome">
                <span class="nomeDisciplina">${materiaInfo.nomeMateria}</span>
            </div>
            <span class="tempoDisciplina">${formatarTempo(materiaInfo.duracaoTotal)}</span>
            </div>
        </a>
    `;
        divDisciplinaContainer.innerHTML += cardHtml;
    }

});

/**
 * Função auxiliar para formatar minutos em horas e minutos
 * Ex: 110 minutos -> "1h 50min"
 */
function formatarTempo(totalMinutos) {
    if (totalMinutos < 60) {
        return `${totalMinutos}min`;
    }
    const horas = Math.floor(totalMinutos / 60);
    const minutos = totalMinutos % 60;
    return `${horas}h ${minutos > 0 ? minutos + 'min' : ''}`.trim();
}