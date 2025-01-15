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
			const container = document.getElementById('resenhas-container');
			container.innerHTML = '';

			resenhas.forEach(resenha => {
				const resenhaDiv = document.createElement('div');
				resenhaDiv.className = 'resenha';

				const img = document.createElement('img');
				img.src = `${resenha.url_foto}`;


				const title = document.createElement('h3');
				title.textContent = resenha.titulo;

				const link = document.createElement('a');
				link.href = `resenhas/${formatarTitulo(resenha.titulo)}.html`;
				link.textContent = 'Ver Resenha';

				resenhaDiv.appendChild(img);
				resenhaDiv.appendChild(title);
				resenhaDiv.appendChild(link);
				container.appendChild(resenhaDiv);
			});
		})
		.catch(error => {
			console.error('Erro ao carregar resenhas:', error);
		});
});

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
            const container = document.getElementById('resenhas-container-adm');
            container.innerHTML = '';

            const adicionarDiv = document.createElement('div');
            adicionarDiv.className = 'adicionaResenha';

            const imgAdicionar = document.createElement('img');
            imgAdicionar.src = '../../imagens/livros/adicionar-resenha.png';
            imgAdicionar.alt = 'Adicionar nova resenha';

            const h3Adicionar = document.createElement('h3');
            h3Adicionar.textContent = 'Adicionar nova resenha';

            const linkAdicionar = document.createElement('a');
            linkAdicionar.href = 'adicionarResenha.html';
            linkAdicionar.textContent = 'Adicionar';

            adicionarDiv.appendChild(imgAdicionar);
            adicionarDiv.appendChild(h3Adicionar);
            adicionarDiv.appendChild(linkAdicionar);

            container.appendChild(adicionarDiv);

            resenhas.forEach(resenha => {
                const resenhaDiv = document.createElement('div');
                resenhaDiv.className = 'resenha';

                const img = document.createElement('img');
                img.src = `${resenha.url_foto}`;

                const title = document.createElement('h3');
                title.textContent = resenha.titulo;

                const link = document.createElement('a');
                link.href = `editarResenha.jsp?id=${resenha.id}`; 
                link.textContent = 'Editar';

                const destaque = document.createElement('h4');
                destaque.textContent = 'Livros em destaque';

                const livrosDestaque = document.createElement('div');
                livrosDestaque.className = 'livrosDestaque';

                const buttonsContainer = document.createElement('div');
                buttonsContainer.className = 'livrosDestaque';

                for (let i = 1; i <= 4; i++) {
                    const button = document.createElement('button');
                    button.className = 'atualizarDestaque';
                    button.textContent = i;
                    button.setAttribute('data-livro-id', resenha.id); 
                    button.setAttribute('data-posicao', i); 
                    buttonsContainer.appendChild(button);
                }

                livrosDestaque.appendChild(buttonsContainer);

                resenhaDiv.appendChild(img);
                resenhaDiv.appendChild(title);
                resenhaDiv.appendChild(link);
                resenhaDiv.appendChild(destaque);
                resenhaDiv.appendChild(livrosDestaque);
                container.appendChild(resenhaDiv);
            });

            document.querySelectorAll(".atualizarDestaque").forEach(button => {
		    button.addEventListener("click", () => {
		        const livroId = button.getAttribute("data-livro-id");
		        const posicao = button.getAttribute("data-posicao");
		
		        fetch("/back-end/atualizarDestaque", {
		            method: "POST",
		            headers: {
		                "Content-Type": "application/x-www-form-urlencoded"
		            },
		            body: `livroId=${livroId}&posicao=${posicao}`
		        })
		        .then(response => {
		            if (response.ok) {
		                return response.text();
		            } else {
		                throw new Error("Erro ao atualizar destaque.");
		            }
		        })
		        .then(message => {
		            alert(message);
		        })
		        .catch(error => {
		            alert(error.message);
		        });
		    });
		});

        })
        .catch(error => {
            console.error('Erro ao carregar resenhas:', error);
        });
});