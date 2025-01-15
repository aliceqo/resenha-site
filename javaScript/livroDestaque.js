document.addEventListener('DOMContentLoaded', () => {
   	function formatarTitulo(titulo) {
        return titulo
            .normalize('NFD') 
            .replace(/[\u0300-\u036f]/g, '') 
            .replace(/ /g, '-') 
            .toLowerCase(); 
    }
	fetch('/back-end/pegarResenhas')
	    .then(response => response.json())
	    .then(resenhas => {
	        resenhas.forEach(resenha => {
	            let posicao = resenha.destaque_posicao;
	            if (posicao === 0) {
	                posicao = 1; 
	            }
	
	            const livroElemento = document.getElementById(`livro-${posicao}`);
	            
	            if (livroElemento) {
	                livroElemento.innerHTML = ''; 
	
	                const img = document.createElement('img');
	                img.src = resenha.url_foto; 
	
	                const title = document.createElement('h3');
	                title.textContent = resenha.titulo; 
	
	                const link = document.createElement('a');
	                link.href = `resenhas/${formatarTitulo(resenha.titulo)}.html`; 
	                link.textContent = 'Ver Resenha';
	
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