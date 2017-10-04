function handleBinaryClick(el){

  if(el.target.innerHTML == '='){
    var expression = stateVariables('expression', '');
    if(/^[0-9]*$/.test(expression)){
      val = eval(expression + stateVariables('lastOperation', ''));
      document.getElementsByTagName('input')[0].value = val.toString(2);
      stateVariables('setExpression', val);
    }
    else if(expression.indexOf("~") > -1){
      evaluate("~", expression);
    }
    else if(expression.indexOf("<<") > -1){
      evaluate("<<", expression);
    }
    else if(expression.indexOf(">>") > -1){
      evaluate(">>", expression);
    }
    else if(expression.indexOf("&") > -1){
      evaluate("&", expression);
    }
    else if(expression.indexOf("+") > -1){
      evaluate("+", expression);
    }
    else{
      evaluate("", expression);
    }

  }
  else if(el.target.innerHTML == 'C'){
    document.getElementsByTagName('input')[0].value = "";
    stateVariables('setExpression', '');
    stateVariables('setLastOperation', '');
  }
  else{
    addToBinaryExpression(el.target.innerHTML);
  }

}

function evaluate(operator, expression){

  var val = "";
  var oneHit = false;
  opIndex = expression.indexOf(operator);
  if(operator == ""){
    val = eval(expression);
  }
  else if(operator == "~"){
    for(var i = 0; i < expression.length - 1; i++){
      tempVal = Math.abs(eval(expression[i] + "-1"));
      if(tempVal == "0" && oneHit == true){
        val += tempVal
      }
      if(tempVal == "1"  && oneHit == false){
        val += tempVal;
        oneHit = true;
      }
    }
  }
  else{
    var first = parseInt(expression.substring(0, opIndex), 2);
    var second = parseInt(expression.substring(opIndex + operator.length, expression.length), 2)
    var val = eval(first + operator + second);
  }

  if(val.length == 0){
    val = '0';
  }
  document.getElementsByTagName('input')[0].value = val.toString(2);
  stateVariables('setExpression', val);
}

function generateBinaryFrontEnd(){

  var keys = [ "0", "1", "", "C", "<<", ">>", "&", "~", "+", "/", "%", "="];

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

  if(value == "&lt;&lt;"){
    value = "<<"
  }
  if(value == "&gt;&gt;"){
    value = ">>";
  }
  if(value == "&amp;"){
    value = "&";
  }

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
