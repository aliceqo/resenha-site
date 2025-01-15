package model;

public class JavaBeans {
	private String id;
	private String url_foto; 
	private String titulo; 
	private String idade_leitura; 
	private String livro; 
	private String autor; 
	private String tradutor; 
	private String generos;
	private String idioma; 
	private String editora; 
	private String numero_paginas; 
	private String frase_destaque; 
	private String conteudo_resenha;
	
	public JavaBeans() {
		super();
	}
	
	public JavaBeans(String id, String url_foto, String titulo, String idade_leitura, String livro, String autor,
			String tradutor, String generos, String idioma, String editora, String numero_paginas,
			String frase_destaque, String conteudo_resenha) {
		super();
		this.id = id;
		this.url_foto = url_foto;
		this.titulo = titulo;
		this.idade_leitura = idade_leitura;
		this.livro = livro;
		this.autor = autor;
		this.tradutor = tradutor;
		this.generos = generos;
		this.idioma = idioma;
		this.editora = editora;
		this.numero_paginas = numero_paginas;
		this.frase_destaque = frase_destaque;
		this.conteudo_resenha = conteudo_resenha;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUrl_foto() {
		return url_foto;
	}
	public void setUrl_foto(String url_foto) {
		this.url_foto = url_foto;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getIdade_leitura() {
		return idade_leitura;
	}
	public void setIdade_leitura(String idade_leitura) {
		this.idade_leitura = idade_leitura;
	}
	
	public String getLivro() {
		return livro;
	}
	public void setLivro(String livro) {
		this.livro = livro;
	}
	
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getTradutor() {
		return tradutor;
	}
	public void setTradutor(String tradutor) {
		this.tradutor = tradutor;
	}
	
	public String getGeneros() {
		return generos;
	}
	public void setGeneros(String generos) {
		this.generos = generos;
	}
	
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	public String getNumero_paginas() {
		return numero_paginas;
	}
	public void setNumero_paginas(String numero_paginas) {
		this.numero_paginas = numero_paginas;
	} 
	
	public String getFrase_destaque() {
		return frase_destaque;
	}
	public void setFrase_destaque(String frase_destaque) {
		this.frase_destaque = frase_destaque;
	} 
	
	public String getConteudo_resenha() {
		return conteudo_resenha;
	}
	public void setConteudo_resenha(String conteudo_resenha) {
		this.conteudo_resenha = conteudo_resenha;
	}
}
