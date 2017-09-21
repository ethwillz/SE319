function validateAndChange(){
  var canChange = 0;
  canChange += document.getElementById('firstName').value != "" ? validate('firstName') : invalidate('firstName');
  canChange += document.getElementById('lastName').value != "" ? validate('lastName') : invalidate('lastName');
  canChange += document.getElementById('gender').value != "" ? validate('gender') : invalidate('gender');
  canChange += document.getElementById('state').value != "" ? validate('state') : invalidate('state');
  if(canChange === 0){
    window.location.href = '../validation2/validation2.html';
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
