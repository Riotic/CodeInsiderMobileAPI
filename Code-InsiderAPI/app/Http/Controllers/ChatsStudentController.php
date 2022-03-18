<?php

namespace App\Http\Controllers;
use App\Models\Chatroom;
use App\Models\ChatsStudent;
use Illuminate\Http\Request;

class ChatsStudentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, $student_id, $company_id)
    {

        $data = Chatroom::select('*')
        ->where('company_id', '=', $company_id)
        ->where('student_id', '=', $student_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $chatroom = $data[0];

        if(!isset($chatroom)){
            $array = ([
                'company_id' => $company_id,
                'student_id' => $student_id,
            ]);
            $chatroom = Chatroom::create($array);

            $message_student = ChatsStudent::create([
                'chatroom_id' => $chatroom['id'],
                'message_student' => $request->message_student,
                'student_id' => $student_id,
            ]);
            $message_student->save();
            return response()->json($message_student,201);
            
        }

        $message_student = ChatsStudent::create([
            'chatroom_id' => $chatroom['id'],
            'message_student' => $request->message_student,
            'student_id' => $student_id,
        ]);
        $message_student->save();
        return response()->json($message_student,201);
    }
    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
