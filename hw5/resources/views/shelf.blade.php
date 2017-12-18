<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable = yes" />
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>Shelf</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="{{ asset('js/shelf.js') }}"></script>
  </head>

  <body>
    <div class='card card-outline-info' id="table-container">
      <table></table>

    <style>
      body{
        display: flex;
      }

      #table-container{
        margin: auto;
      }

      table{
        display: flex;
        flex-direction: column;
        margin: auto;
        padding: 2%;
      }

      input, select{
        margin-right: 3%;
        height: 4%;
      }

      option, button, select{
        cursor: pointer;
      }

      button{
        height: 4%;
        width: 35%;
      }

      td{
        border: 1px solid black;
        height: 50px;
        width: 150px;
        margin: 0;
        text-align: center;
      }
    </style>
  </body>

  </html>
