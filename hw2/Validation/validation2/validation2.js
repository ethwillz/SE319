function validateAndChange(){
  var dotCount = 0;
  var atCount = 0;

  var email = document.getElementById('email').value;
  validate('email');
  email.foreach(function(element){
    if(element == '@' && atCount == 0){
      atCount++;
    }
    else if(element == '@' && atCount > 0){
      invalidate('email');
      break;
    }
    else if(element == '.' && dotCount == 0){
      dotCount++;
    }
    else if(element == '.' && dotCount > 0){
      invalidate('email');
      break;
    }
    else if(/[^a-zA-Z0-9]/.test(element) == false){
      invalidate('email');
      break;
    }
  });

  //TODO validate phone and address as per rules
  canChange += document.getElementById('phone').value != "" ? validate('lastName') : invalidate('lastName');
  canChange += document.getElementById('address').value != "" ? validate('gender') : invalidate('gender');

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
