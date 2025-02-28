<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../imagens/icone/book.png" type="image/x-icon">
    <link rel="stylesheet" href="../../css/stylesEditarResenha.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400..700&display=swap" rel="stylesheet">
    <title>Editar e Excluir Resenha</title>
</head>
<body>
    <div id="page-container" class="page-content">
        <header>
            <h1>Resenhas de Livros</h1>
            <nav>
                <ul>
                    <li><a href="../index.html">Início</a></li>
                    <li><a href="indexAdm.html">Início Adm</a></li>
                    <li><a href="../resenhas.html">Resenhas</a></li>
                </ul>
                <form id="searchForm">
                    <input type="text" id="searchInput" name="searchInput" placeholder="Buscar resenhas...">
                    <button type="submit" id="submit">Pesquisar</button>
                    <div id="resultado"></div>
                </form>
            </nav>
        </header>
        <section class="informacoes">
            <h1>Resenha</h1>
            <form id="form-edit-resenha" action="/back-end/excluirEditar" method="post" enctype="multipart/form-data">
                <input type="hidden" id="resenhaId" name="resenhaId">
                
                <label for="imagem">Imagem da Capa:</label>
				<input type="file" id="url_foto" name="url_foto" accept="image/*">
				<img id="imagem_capa" src="<%= request.getContextPath() %>/<%= request.getAttribute("url_foto") %>"/>

             
                <label for="titulo">Título:</label>
                <input type="text" id="titulo" name="titulo" value="<%= request.getAttribute("titulo") %>">

                <label for="idade">Idade de leitura:</label>
                <input type="text" id="idade_leitura" name="idade_leitura" value="<%= request.getAttribute("idade_leitura") %>">

                <label for="livro">Livro:</label>
                <input type="text" id="livro" name="livro" value="<%= request.getAttribute("livro") %>">

                <label for="autor">Autor:</label>
                <input type="text" id="autor" name="autor" value="<%= request.getAttribute("autor") %>">

                <label for="tradutor">Tradutor:</label>
                <input type="text" id="tradutor" name="tradutor" value="<%= request.getAttribute("tradutor") %>">

                <label for="generos">Gêneros:</label>
                <input type="text" id="generos" name="generos" value="<%= request.getAttribute("generos") %>">

                <label for="idioma">Idioma:</label>
                <input type="text" id="idioma" name="idioma" value="<%= request.getAttribute("idioma") %>">

                <label for="editora">Editora:</label>
                <input type="text" id="editora" name="editora" value="<%= request.getAttribute("editora") %>">

                <label for="numero_paginas">Número de páginas:</label>
                <input type="number" id="numero_paginas" name="numero_paginas" min="1" value="<%= request.getAttribute("numero_paginas") %>">
   		  </form>
	</section>
        
        <section class="resenha">
            <h1>Frase de Destaque</h1>
            <form id="resenha_frase" action="/back-end/excluirEditar" method="post" enctype="multipart/form-data">
            	 <input type="hidden" id="resenhaId" name="resenhaId">
            	 
                <input type="text" id="frase_destaque" name="frase_destaque" value="<%= request.getAttribute("frase_destaque") %>">

                <h2>Texto da Resenha</h2>
                <textarea id="conteudo_resenha" name="conteudo_resenha" rows="10"><%= request.getAttribute("conteudo_resenha") %></textarea>
            </form>
        </section>
        
		<div class="submit-container">
	            <button type="button" id="bot-editar-resenha">Editar Resenha</button>
	            <button type="button" id="bot-excluir-resenha">Excluir Resenha</button>
		</div>
    </div>
    <script src="../../javaScript/scriptsInicial.js" defer></script>
    <script src="../../javaScript/editarExcluirResenha.js" defer></script>
</body>
</html>