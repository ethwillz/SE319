function handleBinaryClick(el){

  if(el.target.innerHTML == '='){

    var val;
    if(expression.indexOf("~") > -1){
      op = expression.indexOf("~");
      var val = eval(expression.substring(0, op));
    }
    else if(expression.indexOf("<<") > -1){
      evaluate("<<");
    }
    else if(expression.indexOf(">>") > -1){
      evaluate(">>");
    }
    else if(expression.indexOf("&") > -1){
      evaluate(">>");
    }
    else if(expression.indexOf("+") > -1){
      evaluate("+");
    }
    else{
      evaluate("");
    }

  }
  else{
    addToBinaryExpression(el.target.innerHTML);
  }

}

function evaluate(operator){

  opIndex = expression.indexOf(operator);
  if(operator == ""){
    val = eval(expression);
  }
  else{
    if(operator == "+"){
      operator = "-";
    }
    var first = parseInt(expression.substring(0, opIndex), 2);
    var second = parseInt(expression.substring(opIndex + 1, expression.length), 2)
    var val = eval(first + operator + second);
  }

  document.getElementsByTagName('input')[0].value = val.toString(2);
  expression = val;
}

function generateBinaryFrontEnd(){

  var keys = [ "0", "1", "", "", "<<", ">>", "&", "~", "+", "/", "%", "="];

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
    curButton.onclick = handleBinaryClick;
    curButton.type = "button";
    curButton.innerHTML = keys[i];
    curButton.classList.add('btn');
    curButton.classList.add('btn-secondary');

    if(keys[i] == ""){
      curButton.style.visibility = "hidden";
    }

  }

}

function addToBinaryExpression(value){

  var decVal;
  var operators = [ "<<", ">>", "&", "~", "+", "/", "%"];

  if(operators.includes(value)){
    lastOperation = value;
  }
  else{
    lastOperation += value;
  }
  
  expression += value;
  document.getElementsByTagName('input')[0].value += value;
}
