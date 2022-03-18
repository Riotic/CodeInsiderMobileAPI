<?php

namespace App\Http\Controllers;

use App\Models\Chatroom;
use App\Models\ChatsCompany;
use App\Models\ChatsStudent;
use App\Models\Like;
use App\Models\Post;
use App\Models\Company;
use App\Models\Student;
use Illuminate\Http\Request;

use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\ModelNotFoundException;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function allStudents()
    {
        return Student::all();
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function allCompanies()
    {
        return Company::all();
    }

    /**
     * Display one resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function showCompany($id)
    {
        try
        {
            Company::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return response()->json([
                'errors' => 'Company not found',
            ], 400);
        }
        return Company::findOrFail($id);
    }

    
    /**
     * Display one resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function showStudent($id)
    {
        try
        {
            Student::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return response()->json([
                'errors' => 'Student not found',
            ], 400);
        }
        return Student::findOrFail($id);
    }



    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function updateStudent(Request $request, $id)
    {
        try
        {
            Student::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        $student = Student::findOrFail($id);

        $rules = array(
            'email' => 'required|string|max:255',
            'firstname' => 'required|string|max:255',
            'name' => 'required|string|max:255',
            'birthday' => 'required|string|max:255',
            'localization' => 'required|string|max:255',
            'phone_number' => 'required|string|max:255',
            'curriculum_year' => 'required|string|max:255',
            'requested_remuneration' => 'required|string|max:255',
            'skills' => 'required|string|max:255',
            'contract_type' => 'required|string|max:255',
            'contract_time' => 'required|string|max:255',
        );

        $validator = Validator::make($request->all(), $rules);

          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
          }

          $student->update($request->all());

          return response()->json([
            'success' => 'Student updated'
          ], 200);
    }


        /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function updateCompany(Request $request, $id)
    {
        try
        {
            Company::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        $student = Company::findOrFail($id);

        $rules = array(
            'name' => 'required|string|max:255',
            'localization' => 'required|string',
            'description' => 'required|string',
            'phone_number' => 'required|string|max:32',
            'email' => 'required|string|max:255',
        );

        $validator = Validator::make($request->all(), $rules);

          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
          }

          $student->update($request->all());

          return response()->json([
            'success' => 'Company updated'
          ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroyStudent($id)
    {
        try
        {
            Student::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }

        $student = Student::findOrFail($id);

        $data = Chatroom::select('*')
        ->where('student_id', '=', $id)
        ->get();

        
        if(count($data) > 0) $chatroom  = $data[0];

        if(isset($chatroom)){
            ChatsStudent::where('chatroom_id', $chatroom['id'])->delete();
            ChatsCompany::where('chatroom_id', $chatroom['id'])->delete();
            Chatroom::where('id', $chatroom['id'])->delete();
        };
        
        Like::where('student_id', $id)->delete();

        if ($student->delete()){
            return response()->json([
                'success' => 'Student suprimed'
            ], 200);
        }
    }

    
    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroyCompany($id)
    {
        try
        {
            Company::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }

        $company = Company::findOrFail($id);

        $data = Chatroom::select('*')
        ->where('company_id', '=', $id)
        ->get();

        
        if(count($data) > 0) $chatroom  = $data[0];

        if(isset($chatroom)){
            ChatsStudent::where('chatroom_id', $chatroom['id'])->delete();
            ChatsCompany::where('chatroom_id', $chatroom['id'])->delete();
            Chatroom::where('id', $chatroom['id'])->delete();
        };

        Post::where('company_id', $id)->delete();
        
        if ($company->delete()){
            return response()->json([
                'success' => 'Company suprimed'
            ], 200);
        }
    }


}
