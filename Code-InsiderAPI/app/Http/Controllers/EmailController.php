<?php

namespace App\Http\Controllers;

use App\Models\Email;
use Illuminate\Http\Request;

class EmailController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function store($company_id, $student_id)
    {
        $data = Email::select('*')
        ->where('student_id', '=', $student_id)
        ->where('company_id', '=', $company_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $liked = $data[0];

        if(!isset($liked)){
            $array = ([
                'student_id' => $student_id,
                'company_id' => $company_id,
                'like' => 1,
            ]);
            Email::create($array);
            return response()->json([
                'success' => 'Email send'
            ], 200);
        }

        return response()->json([
            'success' => 'Email Alredy Send'
        ], 200);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function getemail($company_id)
    {
        return Email::where('company_id', $company_id)->get();
    }

}
