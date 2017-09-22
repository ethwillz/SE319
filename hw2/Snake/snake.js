$(document).ready(function () {
  var c = document.getElementById("canvas");
  var ctx = c.getContext("2d");
  ctx.strokeStyle = "blue";
  ctx.lineWidth = 1;

  var xDir = 0;
  var yDir = 1;
  var xPos = 0;
  var yPos = canvas.height / 2;
  var started = 0;
  var escape = 0;

  document.addEventListener("keydown", function(key){

    if(key.code == "Space" && started == 0){
      started++;
      ctx.beginPath();
      beginGame(ctx);
    }

    if(key.code == "ArrowLeft"){
      if(xDir == 0){
        xDir = -1 * yDir;
        yDir = 0;
      }
      else{
        yDir = -1 * xDir;
        xDir = 0;
      }
    }

    if(key.code == "ArrowRight"){
      if(xDir == 0){
        xDir = yDir;
        yDir = 0;
      }
      else{
        yDir = -1 * xDir;
        xDir = 0;
      }
    }

  });

  function beginGame(ctx){
    while(true){
      setInterval(moveOne(), 500)
    }
  }

  function moveOne(){
    ctx.moveTo(xPos, yPos);
    ctx.lineTo(xPos + xDir, yPos + yDir);
    ctx.stroke();
    xPos = xPos + xDir;
    yPos = yPos + yDir;
  }
});
