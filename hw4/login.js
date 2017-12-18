$(document).ready(function(){
  sessionStorage.loginType = 'invalid';

  document.addEventListener("keydown", function(key){
    if(key.code === 'Enter'){
      authenticate();
    }
  });
});

function authenticate(){
  let username = document.getElementById('username').value;
  let password = document.getElementById('password').value;

  if(username === 'admin' && password === 'admin'){
    sessionStorage.loginType = 'admin';
    window.location.href = 'booksLibrary.html';
  }
  else if(username.substring(0, 1).toLowerCase() === 'u'){
    sessionStorage.loginType = 'undergrad';
    window.location.href = 'booksLibrary.html';
  }
  else{
    alert('Unsuccessful Login');
  }
}
