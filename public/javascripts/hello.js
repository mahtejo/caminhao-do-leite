if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

document.dificuldade.onclick = function(){
    var radVal = document.dificuldade.rads.value;
    result.innerHTML = +radVal;
}