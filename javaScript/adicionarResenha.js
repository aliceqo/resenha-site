const formAddResenha = document.getElementById('form-add-resenha');
const formResenhaFrase = document.getElementById('resenha_frase');
const botAddResenha = document.getElementById('bot-add-resenha');
const botRasResenha = document.getElementById('bot-ras-resenha');

botAddResenha.addEventListener('click', (e) => {
  e.preventDefault();
  sendRequest();
});

botRasResenha.addEventListener('click', (e) => {
  e.preventDefault();
  sendRequest();
});

function sendRequest() {
  const urlFoto = document.getElementById('url_foto').files[0];
  const titulo = document.getElementById('titulo').value;
  const idadeLeitura = document.getElementById('idade_leitura').value;
  const livro = document.getElementById('livro').value;
  const autor = document.getElementById('autor').value;
  const tradutor = document.getElementById('tradutor').value;
  const generos = document.getElementById('generos').value;
  const idioma = document.getElementById('idioma').value;
  const editora = document.getElementById('editora').value;
  const numeroPaginas = document.getElementById('numero_paginas').value;
  const fraseDestaque = document.getElementById('frase_destaque').value;
  const conteudoResenha = document.getElementById('resenha').value;

  const formData = new FormData();
  formData.append('url_foto', urlFoto);
  formData.append('titulo', titulo);
  formData.append('idade_leitura', idadeLeitura);
  formData.append('livro', livro);
  formData.append('autor', autor);
  formData.append('tradutor', tradutor);
  formData.append('generos', generos);
  formData.append('idioma', idioma);
  formData.append('editora', editora);
  formData.append('numero_paginas', numeroPaginas);
  formData.append('frase_destaque', fraseDestaque);
  formData.append('conteudo_resenha', conteudoResenha);

  fetch('/back-end/adicionar', {
    method: 'POST',
    body: formData
  })
  .then((response) => {
    console.log('Response:', response);
    console.log('Status:', response.status);
    if (response.ok) {
      alert('Resenha adicionada com sucesso!');
    } else {
      alert('Erro ao adicionar resenha!');
    }
  })
  .catch((error) => {
    console.error('Erro ao fazer a requisição:', error);
  });
}