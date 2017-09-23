function validateAndChange(){
  var canChange = 0;
  var storedState = 0;
  var firstName = document.getElementById('firstName').value;
  if(firstName != "" && /^([0-9]|[a-z])+([0-9a-z]+)$/i.test(firstName)){
    validate('firstName');
  }
  else{
    canChange += invalidate('firstName');
  }

  var lastName = document.getElementById('lastName').value;
  if(lastName != "" && /^([0-9]|[a-z])+([0-9a-z]+)$/i.test(lastName)){
    validate('lastName');
  }
  else{
    canChange += invalidate('lastName');
  }

  canChange += document.getElementById('gender').value != "" ? validate('gender') : invalidate('gender');
  canChange += document.getElementById('state').value != "" ? validate('state') : invalidate('state');

  if(canChange === 0){
    localStorage.setItem("state", document.getElementById('state').value);
    window.location.href = '../validation2/validation2.html';
    return false;
  }
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
