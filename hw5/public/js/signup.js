$(document).ready(function(){
  document.addEventListener("keydown", function(key){
    if(key.code === 'Enter'){
      signup();
    }
  });
});

function signup(){
  let d = document;
  $.ajax({
    url: '/users',
    method: 'POST',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    data: new UserInfo(d.getElementById('username').value, d.getElementById('password').value,
                       d.getElementById('confirm_password').value, d.getElementById('email').value,
                       d.getElementById('phone').value, d.getElementById('is_librarian').checked),
    success: function(data){
      window.location.href = '/login';
    },
    error: function(data){
      if(data == 'Username or email already taken') alert(data);
      else{
        alert('Fields are invalid');
      }
    }
  });
}

function UserInfo(username, password, confirmed_password, email, phone, is_librarian){
  this.username = username;
  this.password = password;
  this.confirmed_password = confirmed_password;
  this.email = email;
  this.phone = phone;
  this.is_librarian = is_librarian;
}
