document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/desempenho')
        .then(response => {
            if (!response.ok) {
                throw new Error(`A resposta da rede não foi OK: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            // Se chegamos aqui, o JSON foi lido com sucesso.
            console.log('Dados recebidos e parseados com sucesso:', data);

            try {
                const chartContainer = document.querySelector('.chart-container');

                if (data && data.length > 0) {
                    const materias = data.map(item => item.materia);
                    const tempoEstudado = data.map(item => item.tempoEstudado);

                    // Verificação no console
                    console.log('Rótulos para o gráfico (Matérias):', materias);
                    console.log('Dados para o gráfico (Tempo):', tempoEstudado);

                    const ctx = document.getElementById('desempenhoChart').getContext('2d');
                    if (!ctx) {
                       throw new Error("Elemento canvas com id 'desempenhoChart' não foi encontrado!");
                    }

                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: materias,
                            datasets: [{
                                label: 'Minutos Estudados',
                                data: tempoEstudado,
                                backgroundColor: 'rgba(75, 192, 192, 0.6)', // Cor um pouco mais forte
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    ticks: {
                                        color: '#333' // Cor do texto do eixo Y
                                    }
                                },
                                x: {
                                    ticks: {
                                        color: '#333' // Cor do texto do eixo X
                                    }
                                }
                            },
                            plugins: {
                                legend: {
                                    labels: {
                                        color: '#333' // Cor do texto da legenda
                                    }
                                }
                            }
                        }
                    });
                    console.log('Gráfico criado com sucesso!');
                } else {
                    // Se não houver dados, exibe uma mensagem
                    console.log('Dados vazios, exibindo mensagem de "sem dados".');
                    chartContainer.innerHTML = '<p style="text-align: center; color: #7C2A2A; font-size: 20px;">Nenhum dado de estudo registrado ainda. Comece a registrar seus estudos para ver seu desempenho!</p>';
                }
            } catch (chartError) {
                // Este bloco captura erros que acontecem DENTRO da lógica de criação do gráfico
                console.error('ERRO AO CRIAR O GRÁFICO:', chartError);
                const chartContainer = document.querySelector('.chart-container');
                chartContainer.innerHTML = `<p style="color: red; text-align: center;">Ocorreu um erro ao renderizar o gráfico: ${chartError.message}</p>`;
            }
        })
        .catch(error => {
            // Este bloco captura erros de rede ou de parse do JSON
            console.error('ERRO NO FETCH OU NO PARSE DO JSON:', error);
            const chartContainer = document.querySelector('.chart-container');
            chartContainer.innerHTML = '<p style="color: red; text-align: center;">Ocorreu um erro ao carregar os dados. Verifique o console do navegador para mais detalhes.</p>';
        });
});