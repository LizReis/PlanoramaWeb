let currentTab = 0; // O passo atual é o 0
showTab(currentTab); // Exibe o passo atual

function showTab(n) {
    const tabs = document.getElementsByClassName("tab");
    tabs[n].style.display = "block";
    
    document.getElementById("prevBtn").style.display = n === 0 ? "none" : "inline";
    document.getElementById("nextBtn").innerHTML = n === (tabs.length - 1) ? "Salvar Plano" : "Próximo";

    fixStepIndicator(n);
}

function nextPrev(n) {
    const tabs = document.getElementsByClassName("tab");
    
    // Esconde o passo atual
    tabs[currentTab].style.display = "none";
    
    // Avança para o próximo passo
    currentTab = currentTab + n;
    
    // Se chegou ao fim do formulário...
    if (currentTab >= tabs.length) {
        // ...chama a função para enviar os dados.
        submitForm();
        return false;
    }
    
    // Caso contrário, exibe o próximo passo
    showTab(currentTab);
}

function fixStepIndicator(n) {
    const steps = document.getElementsByClassName("step");
    for (let i = 0; i < steps.length; i++) {
        steps[i].className = steps[i].className.replace(" active", "");
    }
    steps[n].className += " active";
}

//Logica para add materias

document.getElementById("addMateriaBtn").addEventListener("click", function() {
    const container = document.getElementById("materias-container");
    const materiaIndex = container.getElementsByClassName("materia-item").length;

    const newMateriaHTML = `
        <div class="materia-item">
            <h4>Matéria ${materiaIndex + 1}</h4>
            <p><input type="text" placeholder="Nome da Matéria" class="nomeMateria"></p>
            <p>Carga Horária Semanal: <input type="number" class="cargaHorariaSemanal" value="1" min="1"></p>
            <p>Proficiência (1 a 5): <input type="number" class="proficiencia" value="3" min="1" max="5"></p>
            <p>Tempo da Sessão (minutos): <input type="number" class="tempoSessao" value="50" min="10"></p>
        </div>
    `;
    container.innerHTML += newMateriaHTML;
});

// Logica para enviar os dados

async function submitForm() {
    // 1. Coletar todos os dados do formulário
    const planejamentoData = {
        nomePlanejamento: document.getElementById("nomePlanejamento").value,
        cargo: document.getElementById("cargo").value,
        anoAplicacao: parseInt(document.getElementById("anoAplicacao").value),
        
        // Lê o valor do campo escondido que o Thymeleaf preencheu.
        criadorId: document.getElementById("usuarioId").value,

        planoArquivado: false,
        preDefinidoAdm: false,
        
        disponibilidade: {
            horasSegunda: parseInt(document.getElementById("horasSegunda").value),
            horasTerca: parseInt(document.getElementById("horasTerca").value),
            horasQuarta: parseInt(document.getElementById("horasQuarta").value),
            horasQuinta: parseInt(document.getElementById("horasQuinta").value),
            horasSexta: parseInt(document.getElementById("horasSexta").value),
            horasSabado: parseInt(document.getElementById("horasSabado").value),
            horasDomingo: parseInt(document.getElementById("horasDomingo").value)
        },
        
        materias: []
    };

    const materiaItems = document.getElementsByClassName("materia-item");
    for (let i = 0; i < materiaItems.length; i++) {
        const materia = {
            nomeMateria: materiaItems[i].querySelector(".nomeMateria").value,
            cargaHorariaSemanal: parseInt(materiaItems[i].querySelector(".cargaHorariaSemanal").value),
            proficiencia: parseInt(materiaItems[i].querySelector(".proficiencia").value),
            tempoSessao: parseInt(materiaItems[i].querySelector(".tempoSessao").value)
        };
        planejamentoData.materias.push(materia);
    }

    console.log("Enviando JSON:", JSON.stringify(planejamentoData, null, 2));

    // 2. Enviar para o backend com a API Fetch
    try {
        const response = await fetch('/api/planejamentos', { // ATENÇÃO: Precisamos criar este endpoint!
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(planejamentoData),
        });

        if (response.ok) {
            const result = await response.json();
            alert("Plano de estudos salvo com sucesso!");
            window.location.href = "/home"; // Redireciona para a home
        } else {
            const error = await response.text();
            alert("Erro ao salvar o plano: " + error);
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert("Ocorreu um erro de comunicação com o servidor.");
    }
}