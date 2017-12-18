function validateAndChange(){
  var dotCount = 0;
  var atCount = 0;
  var dashCount = 0;
  var errorsExist = 0;

  var email = document.getElementById('email').value;
  validate('email');
  if(email.length == 0){
    invalidate('email');
    errorsExist++;
  }
  for(var i = 0; i < email.length; i++){
    if(email[i] == '@'){
      if(!/^[a-z0-9]+$/i.test(email[i+1])){
        atCount++;
      }
      atCount++;
    }
    else if(email[i] == '.'){
      if(!/^[a-z0-9]+$/i.test(email[i+1])){
        dotCount++;
      }
      dotCount++;
    }
    else{
      if(!/^[a-z0-9]+$/i.test(email[i])){
        invalidate('email');
        errorsExist++;
      }
    }
  }
  if(dotCount != 1 || atCount != 1){
    invalidate('email');
    errorsExist++;
  }

  var phone = document.getElementById('phone').value;
  validate('phone');
  if(phone.length == 0){
    invalidate('phone');
    errorsExist++;
  }
  if(phone.includes("-")){
    if(phone.length != 12){
      invalidate('phone');
      errorsExist++;
    }
    for(var i = 0; i < phone.length; i++){
      if(i == 3 || i == 7){
        if(phone[i] != '-'){
          invalidate('phone');
          errorsExist++;
        }
      }
      else{
        if(!/^\d+$/.test(phone[i])){
          invalidate('phone');
          errorsExist++;
        }
      }
    }
  }
  else{
    if(phone.length != 10){
      invalidate('phone');
      errorsExist++;
    }
    if(!/^\d+$/.test(phone)){
      invalidate('phone');
      errorsExist++;
    }
  }

  var address = document.getElementById('address').value;
  var broken = address.split(",");
  validate('address');
  if(broken.length != 2){
    invalidate("address");
    errorsExist++;
  }

  if(errorsExist == 0){
    localStorage.setItem('address', address);
    window.location.href = '../map/map.html';
    return false;
  }

  return false;
}

function validate(element){
  var el = document.getElementById(element);

  var group = el.parentElement.parentElement;

  group.classList.remove("has-danger");
  group.classList.add("has-success");

  el.classList.remove("form-control-danger");
  el.classList.add("form-control-success");

  return 0;
}

function invalidate(element){
  var el = document.getElementById(element);

  var group = el.parentElement.parentElement;

  group.classList.remove("has-success");
  group.classList.add("has-danger");

  el.classList.remove("form-control-success");
  el.classList.add("form-control-danger");
  return 1;
}
