$(document).ready(function(){
  //renders view based on user type in session storage
  let view;
  let options = [];
  let optionsLinks = [];
  let container = document.getElementById("container");
  let wel = document.createElement('h4');
  wel.innerHTML = "Hello " + sessionStorage.username;
  container.appendChild(wel);
  if(sessionStorage.userType === "librarian"){
    view = document.createElement('h4');
    view.innerHTML = "Librarian View";
    container.appendChild(view);
    options = ['Add/Delete books', 'View borrow history', 'View all shelves'];
    optionsLinks = ['addOrDelete', 'borrowHistory', 'allShelves'];
  }
  else{
    view = document.createElement('h4');
    view.innerHTML = "Student View";
    container.appendChild(view);
    options = ['View all shelves'];
    optionsLinks = ['allShelves'];
  }

  for(var i = 0; i < options.length; i++){
    let el = document.createElement('a');
    el.href = optionsLinks[i];
    el.innerHTML = options[i];
    container.appendChild(el);
  }
});
