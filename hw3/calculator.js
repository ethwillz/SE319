$(document).ready(function(){
  generateDecimalFrontEnd();
});

var expression = "";
var lastOperation = "";
var overwrite = 0;
localStorage.memoryVal = "0";

function toggle(type){
  clearShit();

  if(type == 'decimal'){
    generateDecimalFrontEnd();
    document.getElementById('binary').classList.remove('btn-primary');
    document.getElementById('decimal').classList.remove('btn-secondary');
    document.getElementById('decimal').classList.add('btn-primary');
    document.getElementById('binary').classList.add('btn-secondary');
  }
  else{
    generateBinaryFrontEnd();
    document.getElementById('decimal').classList.remove('btn-primary');
    document.getElementById('binary').classList.remove('btn-secondary');
    document.getElementById('binary').classList.add('btn-primary');
    document.getElementById('decimal').classList.add('btn-secondary');
  }

}

function clearShit(){

  document.getElementsByTagName('form')[0].innerHTML = "";
  expression = "";
  lastOperation = "";
  overwrite = 0;
  localStorage.memoryVal = "0";

}

function handleDecimalClick(el){

  if(overwrite == 1){
    document.getElementsByTagName('input')[0].value = "";
    overwrite = 0;
  }
  if(el.target.innerHTML == '='){
    var val = eval(expression);
    if(/^[0-9]*$/.test(expression)){
      val = eval(expression + lastOperation);
    }
    document.getElementsByTagName('input')[0].value = val;
    expression = val;
  }
  else if(el.target.innerHTML == 'C'){
    document.getElementsByTagName('input')[0].value = "";
    expression = "";
    lastOperation = "";
  }
  else if(el.target.innerHTML == 'MR'){
    document.getElementsByTagName('input')[0].value = localStorage.memoryVal;
    overwrite = 1;
  }
  else if(el.target.innerHTML == 'M-'){
    localStorage.memoryVal = parseInt(localStorage.memoryVal)
      - document.getElementsByTagName('input')[0].value
      + "";
  }
  else if(el.target.innerHTML == 'M+'){
    localStorage.memoryVal = eval(localStorage.memoryVal
      + "+" + document.getElementsByTagName('input')[0].value)
      + "";
  }
  else if(el.target.innerHTML == 'MC'){
    localStorage.memoryVal = "0";
  }
  else{
    addToDecimalExpression(el.target.innerHTML);
  }

}

function generateDecimalFrontEnd(){

  var keys = [ "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0",
               ".", "=", "/", "C", "MR", "M-", "M+", "MC", "", "", "" ];

  var form = document.getElementsByTagName('form')[0];
  var input = form.appendChild(document.createElement('div'));
  input.classList.add('form-group');
  input.appendChild(document.createElement('input'));

  var curRow;
  var curButton;
  for(var i = 0; i < keys.length; i++){

    if(i % 4 == 0){
      curRow = form.appendChild(document.createElement('div'));
      curRow.classList.add('form-group');
      curRow.classList.add('button-group');
    }

    curButton = curRow.appendChild(document.createElement('button'));
    curButton.onclick = handleDecimalClick;
    curButton.type = "button";
    curButton.innerHTML = keys[i];
    curButton.classList.add('btn');
    curButton.classList.add('btn-secondary');

    if(keys[i] == ""){
      curButton.style.visibility = "hidden";
    }

  }

}

function addToDecimalExpression(value){

  var operators = [ "/", "*", "+", "-"];

  if(operators.includes(value)){
    lastOperation = value;
  }
  else{
    lastOperation += value;
  }

  expression += value;
  document.getElementsByTagName('input')[0].value += value;
}
