<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use App\Http\Controllers\DB;

class UserController extends Controller
{
    public function index(Request $request)
    {
      $user = \DB::table('users')->where([
        ['username', $request->input('username')],
        ['password', $request->input('password')],
      ])->first();
      if($user != null){
        if($user->is_librarian == 'true') $content = 'is_librarian';
        else $content = 'success';
        $status = 200;
        return new Response($content, $status);
      }
      else{
        $content = $user;
        $status = 400;
        return new Response($content, $status);
      }
    }

    public function create()
    {
        //
    }

    public function store(Request $request)
    {
      $invalidFields = array();

      if(\DB::table('users')->where('username', $request->input('username'))->count()
        || \DB::table('users')->where('email', $request->input('email'))->count()){
          $content = 'Username or email already taken';
          $status = 400;
          return new Response($content, $status);
      }
      if(!preg_match("/^[0-9a-zA-Z]+$/", $request->input('username'))) array_push($invalidFields, 'Username');
      if($request->input('password') != $request->input('confirmed_password')) array_push($invalidFields, 'Password');
      if(!filter_var($request->input('email'), FILTER_VALIDATE_EMAIL)) array_push($invalidFields, 'Email');
      if(preg_match("/^[0-9]{3}-[0-9]{4}-[0-9]{4}$/", $request->input('phone'))
         && preg_match("/^[0-9]{3}-[0-9]{4}-[0-9]{4}$/", $request->input('phone'))){
           array_push($invalidFields, 'Phone');
         }

      if(count($invalidFields) > 0){
        $content = $invalidFields;
        $status = 400;
        return new Response($content, $status);
      }
      else{
        \DB::table('users')->insert(
            ['username' => $request->input('username'),
             'password' => $request->input('password'),
             'email' => $request->input('email'),
             'phone' => $request->input('phone'),
             'is_librarian' => $request->input('is_librarian')]
        );
        $content = 'success';
        $status = 200;
        return new Response($content, $status);
      }
    }

    public function show($id)
    {
        //
    }

    public function edit($id)
    {
        //
    }

    public function update(Request $request, $id)
    {
        //
    }

    public function destroy($id)
    {
        //
    }
}
