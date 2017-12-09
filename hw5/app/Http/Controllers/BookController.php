<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use App\Http\Controllers\DB;

class BookController extends Controller
{
    public function index(Request $request)
    {
      $content = \DB::table('books')
                  ->join('shelves', 'shelves.id', '=', 'books.shelf_id')
                  ->where('shelf_name', $request->input('val'))
                  ->select('books.*')
                  ->get();
      $status = 200;
      return new Response($content, $status);
    }

    public function create()
    {
        //
    }

    public function store(Request $request)
    {
      $shelf_id = \DB::table('shelves')->where('shelf_name', $request->input('shelf'))->first()->id;
      if($shelf_id == null){
        $content = 'error';
        $status = 400;
        return new Response($content, $status);
      }

      \DB::table('books')->insert(
        ['shelf_id' => $shelf_id,
         'book_name' => $request->input('name'),
         'author' => $request->input('author')]
      );

      $content = 'success';
      $status = 200;
      return new Response($content, $status);
    }

    public function show($id)
    {
        //
    }

    public function edit($id)
    {
        //
    }

    public function update(Request $request)
    {
      $date = strtotime("+7 day");
      $user_id = \DB::table('users')->where('username', $request->input('username'))->first()->id;
      \DB::table('books')->where('id', $request->input('book_id'))->update(['availability' => 'false']);
      \DB::table('loan')->insert([
        'user_id' => $user_id,
        'book_id' => $request->input('book_id'),
        'due_date' => $date]
      );
    }

    public function destroy($id)
    {
      if(\DB::table('books')->where('id', $request->input('id'))->first() == null){
        $content = 'error';
        $status = 400;
        return new Response($content, $status);
      }
      else{
        \DB::table('books')->where('id', $request->input('id'))->delete();
        $content = 'success';
        $status = 200;
        return new Response($content, $status);
      }
    }
}
