document.addEventListener('DOMContentLoaded', () => {
  const form1 = document.getElementById('esqueciSenha');
  const form2 = document.getElementById('redefinirSenha');
  const resultado = document.getElementById('resultado'); 
  
  if (form1) {
    form1.addEventListener('submit', (e) => {
      e.preventDefault();
      
      const username = document.getElementById('digUsername').value.trim();
      
      fetch('/back-end/esqueci', { 
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `digUsername=${encodeURIComponent(username)}` 
      })
      .then((response) => {
        console.log('Response:', response);
        console.log('Status:', response.status);
        if (response.ok) {
          window.location.href = 'redefinirSenha.html';
        } else if (response.status === 401) {
          const errorMessage = 'Usuário incorreto.';
          resultado.innerHTML = errorMessage;
          resultado.style.display = 'block'; 
          setTimeout(() => {
            resultado.style.display = 'none'; 
          }, 2000); 
        } else {
          throw new Error('Erro desconhecido');
        }
      })
      .catch((error) => {
        console.error('Erro ao fazer a requisição:', error);
      });

      form1.reset();
    });
  }

  if (form2) {
    form2.addEventListener('submit', handleSubmit);
  }
  
  function handleSubmit(event) {
    event.preventDefault();
    
    const newPassword = document.getElementById('newPassword').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();
    
    if (newPassword !== confirmPassword) {
      const errorMessage = 'Senhas não coincidem.';
      resultado.innerHTML = errorMessage;
      resultado.style.display = 'block'; 
      setTimeout(() => {
        resultado.style.display = 'none'; 
      }, 2000); 
      return;
    }
    
    if (newPassword.length < 6) {
      const errorMessage = 'Senha deve ter pelo menos 6 caracteres.';
      resultado.innerHTML = errorMessage;
      resultado.style.display = 'block'; 
      setTimeout(() => {
        resultado.style.display = 'none'; 
      }, 2000); 
      return;
    }
    
    const urlParams = new URLSearchParams(window.location.search);
    const username = urlParams.get('username');
    
    fetch('/back-end/redefinir', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: `username=${encodeURIComponent(username)}&newPassword=${encodeURIComponent(newPassword)}&confirmPassword=${encodeURIComponent(confirmPassword)}`
    })
    .then(response => response.text().then(text => ({
      status: response.status,
      body: text
    })))
    .then(result => {
      if (result.status === 200) {
        const errorMessage = 'Senha redefinida com sucesso..';
        resultado.innerHTML = errorMessage;
        resultado.style.display = 'block'; 
        setTimeout(() => {
          resultado.style.display = 'none'; 
        }, 2000); 
        setTimeout(() => {
          window.location.href = 'login.html';
        }, 2000);
      } else {
        displayMessage(result.body);
      }
    })
    .catch(error => console.error('Erro ao fazer a requisição:', error));
  }
  
  function displayMessage(message) {
    if (resultado) {
      resultado.innerHTML = message;
      resultado.style.display = 'block';
      setTimeout(() => {
        resultado.style.display = 'none';
      }, 2000);
    }
  }
});