$(document).ready(function(){
  document.addEventListener("keydown", function(key){
    if(key.code === 'Enter'){
      authenticate();
    }
  });
});

function authenticate(){
  var d = document;
  $.ajax({
    url: '/users',
    method: 'GET',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    data: new UserInfo(d.getElementById('username').value, d.getElementById('password').value),
    success: function(data){
      if(data == 'is_librarian') sessionStorage.userType = 'librarian';
      sessionStorage.username = d.getElementById('username').value;
      window.location.href = '/welcome';
    },
    error: function(data){
      alert('The username or password is incorrect');
    }
  });
}

function UserInfo(username, password){
  this.username = username;
  this.password = password;
}
