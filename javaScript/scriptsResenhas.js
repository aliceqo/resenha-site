document.addEventListener('DOMContentLoaded', () => {
  console.log('JavaScript carregado e executando');

	const pageContainer = document.getElementById('page-container');
  const loadPage = (url) => {
    fetch(url)
      .then(response => response.text())
      .then(html => {
        pageContainer.innerHTML = html;
      })
      .catch(error => console.error('Erro ao carregar a página:', error));
  };

	const searchInput = document.getElementById('searchInput');
	const submitButton = document.querySelector('form#searchForm button');
	const resultadoDiv = document.getElementById('resultado');
	let timeout;
	
	const search = () => {
	  const searchTerm = searchInput.value.trim();
	  if (searchTerm !== '') {
	    fetch('/back-end/buscar2?searchInput=' + encodeURIComponent(searchTerm))
	      .then(response => response.text())
	      .then(data => {
	        resultadoDiv.innerHTML = data;
	        resultadoDiv.addEventListener('click', () => {
	          resultadoDiv.innerHTML = '';
	          searchInput.value = '';
	        });
	      })
	      .catch(error => console.error('Erro ao realizar a busca:', error));
	  } else {
	    resultadoDiv.innerHTML = ''; 
	  }
	};
	
	searchInput.addEventListener('input', (e) => {
	  clearTimeout(timeout); 
	  timeout = setTimeout(() => {
	    search(); 
	  }, 300); 
	});
	
	submitButton.addEventListener('click', (e) => {
	  e.preventDefault(); 
	  search();
	});
	
	document.getElementById('searchForm').addEventListener('submit', (e) => {
	  e.preventDefault();
	});
	
	resultadoDiv.addEventListener('click', () => {
		resultadoDiv.innerHTML = '';
	    searchInput.value = '';  
	});
});