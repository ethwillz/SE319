$(document).ready(function(){
  var hash = window.location.hash;

  $.ajax({
    url: '/loan',
    method: 'GET',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    success: function(data){
      let table = document.getElementsByTagName('table')[0];

      var tr = document.createElement('tr');
      table.appendChild(tr);

      var user = document.createElement('td');
      user.innerHTML = "User";
      user.style.backgroundColor = "#d3d3d3";
      tr.appendChild(user);

      var book = document.createElement('td');
      book.innerHTML = "Book";
      book.style.backgroundColor = "#d3d3d3";
      tr.appendChild(book);

      var dueDate = document.createElement('td');
      dueDate.innerHTML = "Due Date";
      dueDate.style.backgroundColor = "#d3d3d3";
      tr.appendChild(dueDate);

      var returnedDate = document.createElement('td');
      returnedDate.innerHTML = "Returned Date";
      returnedDate.style.backgroundColor = "#d3d3d3";
      tr.appendChild(returnedDate);

      for(var i = 0; i < data.length; i++){
        var tr = document.createElement('tr');
        table.appendChild(tr);

        var user = document.createElement('td');
        user.innerHTML = data[i].username;
        tr.appendChild(user);

        var book = document.createElement('td');
        book.innerHTML = data[i].book_name;
        tr.appendChild(book);

        var dueDate = document.createElement('td');
        dueDate.innerHTML = (new Date(data[i].due_date * 1000)).toDateString();
        tr.appendChild(dueDate);

        var returnedDate = document.createElement('td');
        if(data[i].returned_date == null) returnedDate.innerHTML = "Not yet returned";
        else returnedDate.innerHTML = new Date(data[i].returned_date * 1000).toDateString();;
        tr.appendChild(returnedDate);
      }
    },
    error: function(data){
      alert('An error occured');
    }
  });
});
