$(document).ready(function(){
  generateFrontEnd();

  addKeyListeners();
});

var expression = "";

function handleClick(el){
  if(el.target.innerHTML == '='){
    document.getElementsByTagName('input')[0].value = eval(expression);
  }
  else{
    addToExpression(el.target.innerHTML);
    return false;
  }
}

function generateFrontEnd(){
  var keys = [ "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0",
               ".", "=", "/", "C", "MR", "M-", "M+", "MC", "", "", "" ];

  var form = document.getElementsByTagName('form')[0];
  var curRow;
  var curButton;
  for(var i = 0; i < keys.length; i++){
    if(i % 4 == 0){
      curRow = form.appendChild(document.createElement('div'));
      curRow.classList.add('form-group');
      curRow.classList.add('button-group');
    }
    curButton = curRow.appendChild(document.createElement('button'));
    curButton.onclick = handleClick;
    curButton.type = "button";
    curButton.innerHTML = keys[i];
    if(keys[i] == ""){
      curButton.style.visibility = "hidden";
    }
  }
}

function addKeyListeners(){
  document.addEventListener("keydown", function(key){

    if(key.code == "+"){
      addToExpression(key.code);
    }
    else if(key.code == "Minus"){
      addToExpression("-");
    }
    else if(key.code == "*"){
      addToExpression(key.code);
    }
    else if(key.code == "Slash"){
      addToExpression("/");
    }
    else if(key.code == "="){
      addToExpression(key.code);
    }
    else if(key.code == "."){
      addToExpression(key.code);
    }
    else{
      var code = key.code;
      code = code.substring(code.indexOf("t")+1, code.length);
      if(/^[0-9]*$/.test(code)){
        addToExpression(code);
      }
    }
  })
}

function addToExpression(value){
  expression += value;
  document.getElementsByTagName('input')[0].value += value;
}
