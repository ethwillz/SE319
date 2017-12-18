<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('signup');
});

Route::get('/login', function () {
    return view('login');
});

Route::get('/welcome', function () {
    return view('welcomePage');
});

Route::get('/allShelves', function () {
    return view('shelves');
});

Route::get('/shelf', function () {
    return view('shelf');
});

Route::get('/addOrDelete', function () {
    return view('addOrDeleteBook');
});

Route::get('/borrowHistory', function () {
    return view('viewBorrowHistory');
});

Route::resource('users', 'UserController');

Route::resource('books', 'BookController');

Route::resource('loan', 'LoanController');
