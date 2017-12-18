<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable = yes" />
    <title>Login</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="{{ asset('js/login.js') }}"></script>
  </head>

  <body>
    <div id="form-container">
      <form class='card card-outline-info'>
        <h3>Shelves</h3>
        <a href="/shelf#art">Art</a>
        <a href="/shelf#science">Science</a>
        <a href="/shelf#sport">Sport</a>
        <a href="/shelf#literature">Literature</a>
      </form>
    </div>

    <style>
      #form-container{
        height: 100%;
        flex-direction: column;
        display: flex;
      }

      h3, a{
        text-align: center;
      }

      form{
        display: flex;
        flex-direction: column;
        margin: auto;
        width: 25%;
        padding: 2%;
      }

    </style>
  </body>

</html>
