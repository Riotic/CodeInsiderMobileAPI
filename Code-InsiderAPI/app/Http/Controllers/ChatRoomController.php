<?php

namespace App\Http\Controllers;

use App\Models\Chatroom;
use App\Models\ChatsCompany;
use App\Models\ChatsStudent;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class ChatRoomController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index( $company_id, $student_id)
    {
        
        $data = Chatroom::select('id')
        ->where('company_id', '=', $company_id)
        ->where('student_id', '=', $student_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $chatroom = $data[0];

        if(!isset($chatroom)){ return response()->json(['error' => 'no chatroom actually created']);}

        $first = DB::table('chats_students')
                ->select('message_student', 'created_at')
                ->where('chatroom_id', '=', $chatroom['id'])
                ->where('student_id', '=', $student_id);

        $second = DB::table('chats_companies')
                ->select('message_company as message-chatroom', 'created_at')
                ->where('chatroom_id','=',$chatroom['id'])
                ->where('company_id', '=',$company_id )
                ->unionAll($first)
                ->orderBy('created_at', 'asc')
                ->get();
        
        return response()->json([
            'chatroom' => $second
          ], 200);
    }

    public function getAllChatsForStudents( $student_id)
    {
        
        $data = Chatroom::select('*')
        ->where('student_id', '=', $student_id)
        ->get();

        if(count($data) > 0) $chatroom = $data;

        if(!isset($chatroom)){ return response()->json(['error' => 'no chatroom actually created']);}

        return response()->json([
            'chatrooms' => $chatroom
          ], 200);
    }

    public function getAllChatsForCompany( $company_id)
    {
        
        $data = Chatroom::select('*')
        ->where('company_id', '=', $company_id)
        ->get();

        if(count($data) > 0) $chatroom = $data;

        if(!isset($chatroom)){ return response()->json(['error' => 'no chatroom actually created']);}

        return response()->json([
            'chatrooms' => $chatroom
          ], 200);
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
    public function store(Request $request)
    {
        //
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
