$(document).ready(function(){
  var hash = window.location.hash;

  $.ajax({
    url: '/books',
    method: 'GET',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    data: new GenericObj(hash.substring(1, hash.length)),
    success: function(data){
      let table = document.getElementsByTagName('table')[0];

      var tr = document.createElement('tr');
      table.appendChild(tr);

      var id = document.createElement('td');
      id.innerHTML = "Book ID";
      id.style.backgroundColor = "#d3d3d3";
      tr.appendChild(id);

      var name = document.createElement('td');
      name.innerHTML = "Book Name";
      name.style.backgroundColor = "#d3d3d3";
      tr.appendChild(name);

      var author = document.createElement('td');
      author.innerHTML = "Author";
      author.style.backgroundColor = "#d3d3d3";
      tr.appendChild(author);

      var availability = document.createElement('td');
      availability.innerHTML = "Available";
      availability.style.backgroundColor = "#d3d3d3";
      tr.appendChild(availability);

      var borrow = document.createElement('td');
      borrow.innerHTML = "Borrow?";
      borrow.style.backgroundColor = "#d3d3d3";
      tr.appendChild(borrow);

      for(var i = 0; i < data.length; i++){
        var tr = document.createElement('tr');
        table.appendChild(tr);
        var id = document.createElement('td');
        id.innerHTML = data[i].id;
        tr.appendChild(id);

        var name = document.createElement('td');
        name.innerHTML = data[i].book_name;
        tr.appendChild(name);

        var author = document.createElement('td');
        author.innerHTML = data[i].author;
        tr.appendChild(author);

        var availability = document.createElement('td');
        availability.innerHTML = data[i].availability == 'true' ? 'Yes' : 'No';
        availability.id = data[i].id + "-avail";
        tr.appendChild(availability);

        var borrowLink = document.createElement('td');
        var a = document.createElement('a');
        a.innerHTML = 'Borrow';
        a.href = "shelf" + hash;
        a.id = data[i].id;
        a.setAttribute('onclick','borrow(' + data[i].id + '); return false;')
        if(data[i].availability == 'true') borrowLink.appendChild(a);
        tr.appendChild(borrowLink);
      }
    },
    error: function(data){
      alert('An error occured');
    }
  });
});

function borrow(id){
  $.ajax({
    url: '/books/p',
    method: 'PUT',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    data: new LoanObj(sessionStorage.username, id),
    success: function(data){
      document.getElementById(id).innerHTML = "";
      document.getElementById(id + "-avail").innerHTML = "No";
    },
    error: function(data){
      alert('An error has occured');
    }
  });
  return false;
}

function GenericObj(val){
  this.val = val;
}

function LoanObj(username, book_id){
  this.username = username;
  this.book_id = book_id;
}
