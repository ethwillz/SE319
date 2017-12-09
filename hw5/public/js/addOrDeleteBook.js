function deleteBook(){
  let d = document;
  $.ajax({
    url: '/books/d',
    method: 'DELETE',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    data: new BookInfo(d.getElementById('bookID').value, null, null, null),
    success: function(data){
      alert('Book successfully deleted');
    },
    error: function(data){
      alert('An error occured');
    }
  });
}

function addBook(){
  let d = document;
  $.ajax({
    url: '/books',
    method: 'POST',
    headers: {
      'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    },
    data: new BookInfo(null, d.getElementById('bookName').value,
                       d.getElementById('author').value, d.getElementById('shelf').value),
    success: function(data){
      alert('Book successfully added');
    },
    error: function(data){
      alert('An error occured');
    }
  });
}

function BookInfo(id, name, author, shelf){
  this.id = id;
  this.name = name;
  this.author = author;
  this.shelf = shelf;
}
