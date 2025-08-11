document.addEventListener('DOMContentLoaded', () => {
    let calendarGrid = document.getElementById('gridCalendario');
    let monthNameEl = document.getElementById('nomeMes');
    let streakTotalEl = document.getElementById('contagemTotal');

    let today = new Date();
    // Para testar em outro mês, mude aqui. Ex: let today = new Date(2025, 7, 5); (Mês 7 = Agosto)
    let currentMonth = today.getMonth();
    let currentYear = today.getFullYear();

    // Nomes dos meses em português
    let monthNames = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

    function renderCalendar() {
        // Limpa o calendário antes de renderizar
        calendarGrid.innerHTML = '';
        
        // Define o nome do mês no título
        monthNameEl.textContent = monthNames[currentMonth];

        // Lógica para descobrir o primeiro dia da semana e o total de dias no mês
        let firstDayOfMonth = new Date(currentYear, currentMonth, 1).getDay(); // 0=Domingo, 1=Segunda,...
        let daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

        // 1. Adiciona células vazias para os dias antes do início do mês
        // Se o mês começa na Quarta (3), adiciona 3 células vazias (Dom, Seg, Ter)
        for (let i = 0; i < firstDayOfMonth; i++) {
            let emptyCell = document.createElement('div');
            emptyCell.classList.add('diaCalendario', 'diaVazio');
            calendarGrid.appendChild(emptyCell);
        }

        // 2. Adiciona as células para cada dia do mês
        for (let day = 1; day <= daysInMonth; day++) {
            let dayCell = document.createElement('div');
            dayCell.classList.add('diaCalendario');
            dayCell.textContent = day;
            dayCell.dataset.day = day; // Guarda o número do dia no próprio elemento

            // Adiciona um listener de clique para cada dia
            dayCell.addEventListener('click', () => toggleStudyStatus(dayCell));

            calendarGrid.appendChild(dayCell);
        }
        
        updateStreak();
    }

    function toggleStudyStatus(dayCell) {
        // Lógica de clique:
        // Se não tem classe -> vira 'studied'
        // Se é 'studied' -> vira 'not-studied'
        // Se é 'not-studied' -> remove as classes (limpa)
        if (dayCell.classList.contains('studied')) {
            dayCell.classList.remove('studied');
            dayCell.classList.add('not-studied');
        } else if (dayCell.classList.contains('not-studied')) {
            dayCell.classList.remove('not-studied');
        } else {
            dayCell.classList.add('studied');
        }
        updateStreak();
    }
    
    function updateStreak() {
        // Conta quantos elementos têm a classe 'studied'
        let studiedDaysCount = document.querySelectorAll('.diaCalendario.studied').length;
        streakTotalEl.textContent = studiedDaysCount;
    }

    // Inicia o calendário
    renderCalendar();
});