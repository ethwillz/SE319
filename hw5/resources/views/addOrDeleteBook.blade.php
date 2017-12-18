<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable = yes" />
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>Add or Delete Book</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="{{ asset('js/addOrDeleteBook.js') }}"></script>
  </head>

  <body>
    <div id="form-container">
      <form id="top" class='card card-outline-info'>
        <label>Book Name</label>
        <input id='bookName'></input>
        <label>Author</label>
        <input id='author'></input>
        <label>Shelf</label>
        <select id='shelf'>
          <option value='art'>art</option>
          <option value='science'>science</option>
          <option value='sport'>sport</option>
          <option value='literature'>literature</option>
        </select>
        <button class='btn-primary' onclick="addBook()" type='button'>Add Book</button>
      </form>

      <form id= "bottom" class='card card-outline-info'>
        <label>Book ID</label>
        <input id='bookID'></input>
        <button class='btn-primary' onclick="deleteBook()" type='button'>Delete</button>
      </form>
    </div>

    <style>
      #form-container{
        height: 100%;
        flex-direction: column;
        display: flex;
      }

      #buttons{
        margin: 0;
        flex-direction: row;
        display: flex;
      }

      form{
        display: flex;
        flex-direction: column;
        margin: auto;
        width: 25%;
        padding: 2%;
      }

      #top{
        margin-bottom: 3%;
      }

      #bottom{
        margin-top: 1%;
      }

      input, select{
        margin-bottom: 5%;
      }

      button{
        width: 80%;
        margin-left: 10%;
      }
    </style>

  </body>

</html>
