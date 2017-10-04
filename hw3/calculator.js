$(document).ready(function(){
  generateDecimalFrontEnd();
});

(function(){
  var expression = "";
  var lastOperation = "";
  var overwrite = 0;
  var memoryVal = 0;

  function stateVariables(exp, val){

    if(exp == 'expression'){
      return expression;
    }
    if(exp == 'lastOperation'){
      return lastOperation;
    }
    if(exp == 'overwrite'){
      return overwrite;
    }
    if(exp == 'memoryVal'){
      return memoryVal;
    }

    if(exp == 'clear'){
      document.getElementsByTagName('form')[0].innerHTML = "";
      expression = "";
      lastOperation = "";
      overwrite = 0;
      memoryVal = "0";
    }

    if(exp == 'setExpression'){
      expression = val;
    }
    if(exp == 'setLastOperation'){
      lastOperation = val;
    }
    if(exp == 'setOverwrite'){
      overwrite = val;
    }
    if(exp == 'setMemoryVal'){
      memoryVal = val;
    }
  }

  window.stateVariables = stateVariables;
})();

function toggle(type){
  stateVariables('clear', '');

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

function handleDecimalClick(el){
  var temp;

  if(stateVariables('overwrite', '') == 1){
    document.getElementsByTagName('input')[0].value = "";
    stateVariables('setOverwrite', 0);
  }
  if(el.target.innerHTML == '='){
    var val = eval(stateVariables('expression', ''));
    if(/^[0-9]*$/.test(stateVariables('expression', ''))){
      val = eval(stateVariables('expression', '') + stateVariables('lastOperation', ''));
    }
    document.getElementsByTagName('input')[0].value = val;
    stateVariables('setExpression', val);
  }
  else if(el.target.innerHTML == 'C'){
    document.getElementsByTagName('input')[0].value = "";
    stateVariables('setExpression', '');
    stateVariables('setLastOperation', '');
  }
  else if(el.target.innerHTML == 'MR'){
    document.getElementsByTagName('input')[0].value = stateVariables('memoryVal', '');
    stateVariables('setOverwrite', 1);
  }
  else if(el.target.innerHTML == 'M-'){
    temp = parseInt(stateVariables('memoryVal', ''))
      - document.getElementsByTagName('input')[0].value
      + "";
      stateVariables('setMemoryVal', temp);
  }
  else if(el.target.innerHTML == 'M+'){
    temp = eval(stateVariables('memoryVal', '')
      + "+" + document.getElementsByTagName('input')[0].value)
      + "";
      stateVariables('setMemoryVal', temp);
  }
  else if(el.target.innerHTML == 'MC'){
    stateVariables('setMemoryVal', 0)
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

  var operators = [ "/", "*", "+", "-", "<<", ">>"];
  var temp;

  if(operators.includes(value)){
    stateVariables('setLastOperation', value);
  }
  else{
    temp = stateVariables('lastOperation', '');
    temp += value;
    stateVariables('setLastOperation', temp);
  }

  temp = stateVariables('expression', '');
  temp += value;
  stateVariables('setExpression', temp);
  document.getElementsByTagName('input')[0].value += value;
}
