<html>

  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable = yes" />
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>Sign Up</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="{{ asset('js/signup.js') }}"></script>
  </head>

  <body>
    <div id="form-container">
      <form class='card card-outline-info'>
        <label>Username</label>
        <input id='username'></input>
        <label>Password</label>
        <input id='password' type='password'></input>
        <label>Confirm Password</label>
        <input id='confirm_password' type='password'></input>
        <label>Email</label>
        <input id='email' type='email'></input>
        <label>Phone Number</label>
        <input id='phone' type='tel'</input>
        <div class='form-group'>
          <input id='is_librarian' type='checkbox'></input>
          <label>Librarian</label>
        </div>
        <button  class='btn-primary' onclick="signup()" type='button'>Sign Up</button>
      </form>
    </div>

    <style>
      #form-container{
        height: 100%;
        flex-direction: column;
        display: flex;
      }

      form{
        display: flex;
        flex-direction: column;
        margin: auto;
        width: 25%;
        padding: 2%;
      }

      input{
        margin-bottom: 5%;
      }

      button{
        width: 80%;
        margin-left: 10%;
      }
    </style>
  </body>

</html>
