<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Book extends Model
{
  protected $fillable -> [
    'shelf_id', 'book_name', 'author', 'availability'
  ];
}
