const form = document.getElementById('loginForm');

form.addEventListener('submit', (e) => {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const resultado = document.getElementById('resultado');

	fetch('/back-end/login', { 
	  method: 'POST',
	  headers: {
	    'Content-Type': 'application/x-www-form-urlencoded'
	  },
	  body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}` 
	})
	.then((response) => {
	  console.log('Response:', response);
	  console.log('Status:', response.status);
	  if (response.ok) {
	    window.location.href = 'indexAdm.html';
	  } else if (response.status === 401) {
	    if (resultado) {
		  errorMessage = 'Usuário ou senha incorretos.';
	      resultado.innerHTML = errorMessage;
	      resultado.style.display = 'block'; 
	      setTimeout(() => {
	        resultado.style.display = 'none'; 
	      }, 2000); 
	    }
	  } else {
	    throw new Error('Erro desconhecido');
	  }
	})
	.catch((error) => {
	  console.error('Erro ao fazer a requisição:', error);
	});

  form.reset();
});