document.addEventListener('DOMContentLoaded', () => {
    // Função para formatar o título, removendo acentos, espaços e convertendo para minúsculas
    function formatarTitulo(titulo) {
        return titulo
            .normalize('NFD') // Normaliza o texto
            .replace(/[\u0300-\u036f]/g, '') // Remove acentos
            .replace(/ /g, '-') // Substitui espaços por hifens
            .toLowerCase(); // Converte tudo para minúsculas
    }
	fetch('/back-end/pegarResenhas') // Certifique-se que o endpoint está correto
	    .then(response => response.json())
	    .then(resenhas => {
	        // Percorrer as resenhas e alocar cada uma na posição de destaque
	        resenhas.forEach(resenha => {
	            // Verificar se destaque_posicao é 0 e, se for, mudar para 1
	            let posicao = resenha.destaque_posicao;
	            if (posicao === 0) {
	                posicao = 1; // Muda para 1 se for 0
	            }
	
	            // Selecionar a posição correta pelo destaque_posicao
	            const livroElemento = document.getElementById(`livro-${posicao}`);
	            
	            if (livroElemento) {
	                // Limpar o conteúdo da posição atual completamente
	                livroElemento.innerHTML = ''; // Substituir o conteúdo da div existente
	
	                // Criar os elementos da resenha
	                const img = document.createElement('img');
	                img.src = resenha.url_foto; // Foto do livro
	
	                const title = document.createElement('h3');
	                title.textContent = resenha.titulo; // Título do livro
	
	                const link = document.createElement('a');
	                link.href = `resenhas/${formatarTitulo(resenha.titulo)}.html`; // Link para a resenha
	                link.textContent = 'Ver Resenha';
	
	                // Adicionar os elementos dentro da div existente
	                livroElemento.appendChild(img);
	                livroElemento.appendChild(title);
	                livroElemento.appendChild(link);    
	            } 
	            else {
	                console.error(`Elemento com id livro-${posicao} não encontrado.`);
	            }
	        });
	    })
	    .catch(error => {
	        console.error('Erro ao carregar resenhas:', error);
	    });
});