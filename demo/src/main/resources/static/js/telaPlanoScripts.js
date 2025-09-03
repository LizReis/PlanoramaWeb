document.addEventListener('DOMContentLoaded', function() {
    const cicloContainer = document.getElementById('ciclo-visual');
    const listaMateriasContainer = document.querySelector('#lista-materias ul');

    // Mapeia nomes de matérias para cores para consistência visual
    const coresMaterias = {};
    let proximaCor = 0;
    const paletaDeCores = ['#4CAF50', '#2196F3', '#FFC107', '#F44336', '#9C27B0', '#009688', '#FF5722'];

    // 1. Processar os dados e desenhar o ciclo
    if (cicloData && cicloData.length > 0) {
        cicloData.forEach((sessao, index) => {
            const nomeMateria = sessao.nomeMateria;

            // Define uma cor para cada matéria
            if (!coresMaterias[nomeMateria]) {
                coresMaterias[nomeMateria] = paletaDeCores[proximaCor % paletaDeCores.length];
                proximaCor++;
            }

            // Cria a "fatia" do ciclo
            const fatia = document.createElement('div');
            fatia.className = 'fatia-ciclo';
            fatia.textContent = nomeMateria;
            fatia.style.backgroundColor = coresMaterias[nomeMateria];
            fatia.dataset.materia = nomeMateria; // Guarda o nome da matéria
            fatia.dataset.duracao = sessao.duracaoSessao; // Guarda a duração em minutos

            // Adiciona o evento de clique para iniciar o cronômetro
            fatia.addEventListener('click', iniciarSessao);

            cicloContainer.appendChild(fatia);
        });

        // 2. Preencher a lista de matérias (legenda)
        Object.keys(coresMaterias).forEach(materia => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `<span class="cor-legenda" style="background-color: ${coresMaterias[materia]}"></span> ${materia}`;
            listaMateriasContainer.appendChild(listItem);
        });

    } else {
        cicloContainer.innerHTML = '<p>Nenhuma sessão de estudo para este plano. Verifique as cargas horárias das matérias.</p>';
    }

    // --- LÓGICA DO CRONÔMETRO ---
    const cronometroContainer = document.getElementById('cronometro-container');
    const cronometroDisplay = document.getElementById('cronometro-display');
    const cronometroMateriaTitulo = document.getElementById('cronometro-materia');
    let tempoRestante;
    let timer;

    function iniciarSessao(event) {
        const fatiaClicada = event.currentTarget;
        const materia = fatiaClicada.dataset.materia;
        const duracaoMinutos = parseInt(fatiaClicada.dataset.duracao);

        // Mostra o cronômetro
        cronometroMateriaTitulo.textContent = `Estudando: ${materia}`;
        cronometroContainer.style.display = 'block';

        // Desabilita o clique em outras fatias
        document.querySelectorAll('.fatia-ciclo').forEach(f => f.style.pointerEvents = 'none');

        // Inicia a contagem regressiva
        tempoRestante = duracaoMinutos * 60; // Converte para segundos
        clearInterval(timer); // Limpa qualquer timer anterior
        timer = setInterval(atualizarCronometro, 1000);

        fatiaClicada.classList.add('estudando');
    }

    function atualizarCronometro() {
        const minutos = Math.floor(tempoRestante / 60);
        let segundos = tempoRestante % 60;
        segundos = segundos < 10 ? '0' + segundos : segundos;

        cronometroDisplay.textContent = `${minutos}:${segundos}`;

        if (tempoRestante > 0) {
            tempoRestante--;
        } else {
            clearInterval(timer);
            alert("Sessão concluída!");
            // Aqui você pode adicionar a lógica para marcar a fatia como concluída
            document.querySelector('.fatia-ciclo.estudando').classList.add('concluida');
            cronometroContainer.style.display = 'none';
            document.querySelectorAll('.fatia-ciclo').forEach(f => f.style.pointerEvents = 'auto'); // Reabilita o clique
        }
    }
});