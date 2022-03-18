<?php

namespace App\Http\Controllers\Auth;

use App\Models\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Models\Company;
use App\Models\Student;
use Illuminate\Support\Facades\Hash;


class AuthenticatedSessionController extends Controller
{
    /**
     * Handle an incoming authentication request.
     *
     * @param  \App\Http\Requests\Auth\LoginRequest  $request
     * @return \Illuminate\Http\Response
     */
    public function authStudent(Request $request)
    {
        $request->validate([
            'email' => 'required',
            'password' => 'required',
        ]);
     
        $user = Student::where('email', $request->email)->first();
     
        if (! $user || ! Hash::check($request->password, $user->password)) {
            return response()->json([
                'error' => 'Wrong password or username'
            ], 400);
        }
        
        return response()->json([
            'success' => 'User connected',
            'user' => $user,
            'api_token' => $user->api_token,
        ], 200);
    }


    public function authCompany(Request $request)
    {
        $request->validate([
            'email' => 'required',
            'password' => 'required',
        ]);
        
        $user = Company::where('email', $request->email)->first();
     
        if (! $user || ! Hash::check($request->password, $user->password)) {
            return response()->json([
                'error' => 'Wrong password or username'
            ], 400);
        }
        
        return response()->json([
            'success' => 'User connected',
            'user' => $user,
            'api_token' => $user->api_token,
        ], 200);
    }

}
