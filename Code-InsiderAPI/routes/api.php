<?php

use Illuminate\Support\Facades\Route;

use App\Http\Controllers\LikeController;
use App\Http\Controllers\PostController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\ChatRoomController;
use App\Http\Controllers\ChatsStudentController;
use App\Http\Controllers\ChatsCompanyController;
use App\Http\Controllers\Auth\RegisteredUserController;
use App\Http\Controllers\Auth\AuthenticatedSessionController;
use App\Http\Controllers\EmailController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
//login route
Route::post('/loginStudent', [AuthenticatedSessionController::class, 'authStudent']);
Route::post('/loginCompany', [AuthenticatedSessionController::class, 'authCompany']);

//get students or companies
Route::get('/students', [UserController::class, 'allStudents']);
Route::get('/companies', [UserController::class, 'allCompanies']);

Route::get('/company/{id}', [UserController::class, 'showCompany']);
Route::get('/student/{id}', [UserController::class, 'showStudent']);

//register route
Route::post('/registerStudent', [RegisteredUserController::class, 'storeStudent']);
Route::post('/registerCompany', [RegisteredUserController::class, 'storeCompany']);

Route::get('/student/{id}/post/{id2}/like', [LikeController::class, 'likeornot']);
Route::get('/student/{id}/likes', [LikeController::class, 'getlike']);

Route::middleware('auth:api_Student')->group(function() {
    //update student
    Route::put('/student/{id}', [UserController::class, 'updateStudent']);
    //delete Student
    Route::delete('/student/{id}', [UserController::class, 'destroyStudent']);
    // Route like
    Route::post('/student/{id}/post/{id2}/like', [LikeController::class, 'liked']);
});

// Routes posts
Route::get('/posts', [PostController::class, 'indexStudents']);
Route::get('/company/{id}/post/{id2}', [PostController::class, 'show']);
Route::get('/company/{id}/posts', [PostController::class, 'index']);

Route::middleware('auth:api_Company')->group(function() {
    //update companies
    Route::put('/company/{id}', [UserController::class, 'updateCompany']);
    //delete companies
    Route::delete('/company/{id}', [UserController::class, 'destroyCompany']);
    // Routes posts
    Route::post('/company/{id}/post', [PostController::class, 'store']);
    Route::delete('/company/{id}/post/{id2}', [PostController::class, 'destroy']);
    Route::put('/company/{id}/post/{id2}', [PostController::class, 'update']);
    
    //Route Email
    Route::post('/company/{id}/mail/{studentid}',[EmailController::class, 'store']);
    Route::get('/company/{id}/mails',[EmailController::class, 'getemail']);
});

// Route chatroom
Route::get('/chatroom/company/{company_id}/student/{student_id}', [ChatRoomController::class, 'index']);
Route::post('/chatroom/student/{student_id}/company/{company_id}', [ChatsStudentController::class, 'store']);
Route::post('/chatroom/company/{company_id}/student/{student_id}', [ChatsCompanyController::class, 'store']);
Route::get('/chatroom/student/{student_id}',  [ChatRoomController::class, 'getAllChatsForStudents']);
Route::get('/chatroom/company/{company_id}',  [ChatRoomController::class, 'getAllChatsForCompany']);