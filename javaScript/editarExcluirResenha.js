document.addEventListener('DOMContentLoaded', function () {
    console.log('JavaScript carregado e executando');
    const resenhaId = obterIdResenha();
    if (resenhaId) {
        console.log('ID da resenha:', resenhaId);
        carregarDadosResenha(resenhaId);
    } else {
        console.error('ID da resenha não encontrado');
    }
});

function carregarDadosResenha(id) {
    fetch(`/back-end/excluirEditar?id=${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro ao carregar os dados da resenha: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Dados da resenha:', data);

            atualizarElemento('imagem_capa', data.url_foto, 'src');
            atualizarElemento('titulo', data.titulo);
            atualizarElemento('idade_leitura', data.idade_leitura);
            atualizarElemento('livro', data.livro);
            atualizarElemento('autor', data.autor);
            atualizarElemento('tradutor', data.tradutor);
            atualizarElemento('generos', data.generos);
            atualizarElemento('idioma', data.idioma);
            atualizarElemento('editora', data.editora);
            
            atualizarElemento('numero_paginas', data.numero_paginas, 'value', true);
            
            atualizarElemento('frase_destaque', data.frase_destaque);
            atualizarElemento('conteudo_resenha', data.conteudo_resenha, 'innerText');
        })
        .catch(error => {
            console.error('Erro ao carregar os dados da resenha:', error);
        });
}

function atualizarElemento(id, valor, atributo, forNumber) {
    const elemento = document.getElementById(id);
    if (elemento) {
        if (atributo === 'src' && elemento.tagName === 'IMG') {
            elemento.src = valor || 'Não disponível';
        } else if (atributo === 'value' || !atributo) {
            if (forNumber) {
                elemento.valueAsNumber = valor; 
            } else {
                elemento.value = valor || '';
            }
        } else if (atributo === 'innerText') {
            elemento.innerText = valor || 'Não disponível';
        }
    } else {
        console.error(`Elemento com ID ${id} não encontrado`);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const resenhaId = obterIdResenha();
    if (resenhaId) {
        console.log('ID da resenha:', resenhaId);
        carregarDadosResenha(resenhaId);

        document.querySelector("#bot-editar-resenha").addEventListener("click", function (event) {
            event.preventDefault();  

            const titulo = document.querySelector("#titulo").value;
            const idade_leitura = document.querySelector("#idade_leitura").value;
            const livro = document.querySelector("#livro").value;
            const autor = document.querySelector("#autor").value;
            const tradutor = document.querySelector("#tradutor").value;
            const generos = document.querySelector("#generos").value;
            const idioma = document.querySelector("#idioma").value;
            const editora = document.querySelector("#editora").value;
            const numero_paginas = document.querySelector("#numero_paginas").value;
            const frase_destaque = document.querySelector("#frase_destaque").value;
            const conteudo_resenha = document.querySelector("#conteudo_resenha").value;
            const url_foto = document.querySelector("#url_foto").files[0]; 
            
            const formData = new FormData();
            formData.append("resenhaId", resenhaId); 
            formData.append("titulo", titulo);
            formData.append("idade_leitura", idade_leitura);
            formData.append("livro", livro);
            formData.append("autor", autor);
            formData.append("tradutor", tradutor);
            formData.append("generos", generos);
            formData.append("idioma", idioma);
            formData.append("editora", editora);
            formData.append("numero_paginas", numero_paginas);
            formData.append("frase_destaque", frase_destaque);
            formData.append("conteudo_resenha", conteudo_resenha);
            if (url_foto) {
                formData.append("url_foto", url_foto); 
            }

            fetch("/back-end/excluirEditar", {
                method: "POST",  
                body: formData
            }).then(response => {
                if (response.ok) {
                    alert("Resenha editada com sucesso!");
                    window.location.reload();  
                } else {
                    alert("Erro ao editar a resenha.");
                }
            }).catch(error => {
                console.error("Erro na requisição:", error);
            });
        });
    } else {
        console.error('ID da resenha não encontrado');
    }
});

function excluirResenha(id) {
    fetch(`/back-end/excluirEditar?id=${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(function(response) {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text);
            });
        }
        return response.text();
    })
    .then(text => {
        alert(text);
        window.location.href = 'indexAdm.html'; 
    })
    .catch(error => {
        console.error('Erro ao excluir a resenha:', error);
        alert('Ocorreu um erro ao excluir a resenha: ' + error.message);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const botaoExcluir = document.getElementById('bot-excluir-resenha');
    if (botaoExcluir) {
        botaoExcluir.addEventListener('click', function () {
            const resenhaId = obterIdResenha(); 
            if (resenhaId) {
                if (confirm("Tem certeza que deseja excluir esta resenha?")) {
                    excluirResenha(resenhaId);
                }
            } else {
                console.error('ID da resenha não encontrado na URL.');
            }
        });
    } else {
        console.error('Botão de exclusão não encontrado.');
    }
});

function obterIdResenha() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}