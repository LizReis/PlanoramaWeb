//SCRIPT PARA SABER QUAL DIAS FORAM SELECIONADOS PARA A DISPONIBILIDADE DO USUÁRIO

//SCRIPT PARA SABER QUAIS MATÉRIAS FORAM SELECIONADAS PARA O PLANO
//QUE O USUÁRIO ACABOU DE CRIAR 
document.body.addEventListener('click', async function (event) {

    // Se o elemento clicado for um 'dia' da disponibilidade
    if (event.target.matches(".dia")) {
        event.target.classList.toggle("selecionado");
    }

    // Se o elemento clicado for uma 'materia' no segundo card
    if (event.target.matches(".materia")) {
        event.target.classList.toggle("selecionado");
    }

    //A diferença dessa abordagem é que pega o link mesmo que o click seja no svg
    const linkCriarMateria = event.target.closest(".abrir-sub-modal");
    if (linkCriarMateria) {
        event.preventDefault();

        const url = linkCriarMateria.getAttribute("href");
        const modalContainer = document.getElementById("modal-container");

        try {
            const response = await fetch(url);

            const conteudoCardMateria = await response.text();
            if (modalContainer) {
                modalContainer.innerHTML = conteudoCardMateria;
            }
        } catch (error) {
            console.error("Erro ao carregar o card de criação da matéria", error);
        }
    }

    //SCRIPT PARA ADICIONAR O INPUT ASSUNTOS A CADA CLICK
    if (event.target.id === 'btn-adicionar-assunto') {
        let containerAssunto = document.getElementById("assuntos-container");
        if (!containerAssunto) return; // Garante que o container existe

        let novoAssuntoDiv = document.createElement("div");
        novoAssuntoDiv.classList.add("assunto-input-group");

        let novoInput = document.createElement("input");
        novoInput.type = "text";
        novoInput.name = "assuntos"
        novoInput.placeholder = "Nome do assunto";
        novoInput.required = true;

        let botaoRemover = document.createElement("button");
        botaoRemover.type = "button";
        botaoRemover.textContent = "Remover";
        botaoRemover.onclick = function () {
            containerAssunto.removeChild(novoAssuntoDiv);
        };

        novoAssuntoDiv.appendChild(novoInput);
        novoAssuntoDiv.appendChild(botaoRemover);

        // AQUI ESTÁ A CORREÇÃO LÓGICA: adiciona a nova div ao container principal
        containerAssunto.appendChild(novoAssuntoDiv);
    }

});

// Este listener garante que o código só rodará quando o formulário estiver na tela,
// mesmo que ele seja carregado dinamicamente.
document.body.addEventListener('submit', async function (event) {

    // --- LÓGICA PARA O FORMULÁRIO DO PRIMEIRO CARD ---
    if (event.target.id === 'formCriarPlanejamento') {

        // Previne o envio tradicional que recarrega a página
        event.preventDefault();

        const form = event.target;
        const modalContainer = document.getElementById('modal-container');

        // *** AQUI ESTÁ A JUNÇÃO QUE VOCÊ QUERIA ***
        // 1. Buscamos os dias com a classe '.selecionado' NO MOMENTO EXATO do submit.
        const diasSelecionados = Array.from(document.querySelectorAll(".dia.selecionado")).map(d => d.dataset.dia);
        console.log("Enviando os seguintes dias:", diasSelecionados); // Para depuração

        // 2. Limpamos quaisquer inputs antigos para não acumular
        const inputsAntigos = form.querySelectorAll("input[name='disponibilidade']");
        inputsAntigos.forEach(input => input.remove());

        // 3. Adicionamos os dias selecionados como inputs ocultos no formulário
        diasSelecionados.forEach(dia => {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "disponibilidade";
            hiddenInput.value = dia;
            form.appendChild(hiddenInput);
        });

        // 4. Enviamos o formulário preparado via Fetch (AJAX)
        try {
            const formData = new FormData(form);
            const response = await fetch(form.action, {
                method: form.method,
                body: new URLSearchParams(formData)
            });

            // 5. Tratamos a resposta do servidor
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                const htmlComErros = await response.text();
                if (modalContainer) {
                    modalContainer.innerHTML = htmlComErros;
                }
            }
        } catch (error) {
            console.error("Erro ao enviar o formulário:", error);
        }
    }



    //========ENVIO DO SEGUNDO FORM 
    if (event.target.id === "formCriarPlanejamento_2") {
        event.preventDefault();

        const form = event.target;
        const modalContainer = document.getElementById("modal-container");

        const materiasSelecionadas = Array.from(document.querySelectorAll(".materia.selecionada")).map(m => m.dataset.materiaId);

        console.log("Ids das matérias: ", materiasSelecionadas);

        const inputsAntigos = form.querySelectorAll("input[name='materiasIds']");
        inputsAntigos.forEach(input => input.remove());

        materiasSelecionadas.forEach(id => {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "materiaIds";//É O NAME QUE O CONTROLLER RECEBERÁ
            hiddenInput.value = id;
            form.appendChild(hiddenInput);
        });

        try {
            const formData = new FormData(form);
            const response = await fetch(form.action, {
                method: form.method,
                body: new URLSearchParams(formData)
            });

            const novoCardHtml = await response.text();

            if (modalContainer) {
                modalContainer.innerHTML = novoCardHtml;
            }
        } catch (error) {
            console.error("Erro ao enviar o formulário de matérias: ", error);
        }

    }

    //=====SCRIPT DO FORM CRIAR MATÉRIA
    if (event.target.id === "formCriarMateria") {
        event.preventDefault();

        const form = event.target;
        const modalContainer = document.getElementById("modal-container");

        try {
            const formData = new FormData(form);
            const response = await fetch(form.action, {
                method: form.method,
                body: new URLSearchParams(formData)
            });

            const segundoCardHtml = await response.text();
            if (modalContainer) {
                modalContainer.innerHTML = segundoCardHtml;
            }
        } catch (error) {
            console.error("Não foi possível carregar o segundo card: ", error);
        }
    }

});


