<?php
  $method = $_SERVER['REQUEST_METHOD'];

  if($method == 'GET'){
    echo json_encode(file_get_contents('books.txt', true));
  }
  else if($method == 'POST'){
    $input = json_decode(file_get_contents('php://input'), true);
  }
  else{
    http_response_code(500);
  }
?>
