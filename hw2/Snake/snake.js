$(document).ready(function () {
  var c = document.getElementById("canvas");
  var ctx = c.getContext("2d");
  ctx.strokeStyle = "blue";
  ctx.lineWidth = 1;

  var xDir = 1;
  var yDir = 0;
  var xPos = 0;
  var yPos = canvas.height / 2;
  var started = 0;
  var game;

  document.addEventListener("keydown", function(key){

    if(key.code == "Space" && started == 0){
      started++;
      ctx.beginPath();
      beginGame(ctx);
    }

    //TODO revise these to be correct
    if(key.code == "ArrowLeft"){
      if(xDir == 0){
        xDir = yDir;
        yDir = 0;
      }
      else{
        yDir = -1 * xDir;
        xDir = 0;
      }
    }

    //TODO revise these to be correct
    if(key.code == "ArrowRight"){
      if(xDir == 0){
        xDir = -1 * yDir;
        yDir = 0;
      }
      else{
        yDir = xDir;
        xDir = 0;
      }
    }

    if(key.code == "Escape"){
      clearInterval(game);
    }

  });

  function beginGame(ctx){
      game = setInterval(moveOne, 15);
  }

  function moveOne(){
    if(xPos + xDir < 0 || yPos + yDir < 0){
      clearInterval(game);
      alert("You hit the egde fam");
    }
    ctx.moveTo(xPos, yPos);
    ctx.lineTo(xPos + xDir, yPos + yDir);
    ctx.stroke();
    xPos = xPos + xDir;
    yPos = yPos + yDir;
  }
});
