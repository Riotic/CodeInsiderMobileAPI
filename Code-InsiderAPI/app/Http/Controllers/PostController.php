<?php

namespace App\Http\Controllers;

use App\Models\Like;
use App\Models\Post;
use App\Models\Company;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\ModelNotFoundException;

class PostController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($id)
    {
        return response()->json(Post::where('company_id','=', $id)->get());
    }

    public function indexStudents()
    {
        return response()->json(Post::all());
    }
    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @param  int  $id2
     * @return \Illuminate\Http\Response
     */
    public function show($company_id, $post_id)
    {
        $verify = Post::findOrFail($post_id);
        if ($verify['company_id'] != $company_id) { response()->json(['errors' => 'bad request'],400); }
        return response()->json(Post::findOrFail($company_id));
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
    public function store(Request $request, $id)
    {
        $rules = array(
            'job_name' => 'required|string|max:255',
            'contract_type' => 'required',
            'description' => 'required|string|max:255',
        );
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'errors' => $validator->errors(),
              ], 400);
        }
        $validated = $validator->validated();
        $validated['company_id'] = $id;
        Post::create($validated);
        return response()->json([
            'success' => 'Post crée'
          ], 201);
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
    public function update(Request $request, $company_id, $post_id)
    {

        $data = Post::select('*')
        ->where('company_id', '=', $company_id)
        ->where('id', '=', $post_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $post = $data[0];

        if(!isset($post)) {return response()->json(['error' => 'Poste inexistant'],400);}

        $rules = array(
            'job_name' => 'required|string|max:255',
            'contract_type' => 'required|string',
            'description' => 'required|string|max:255',
        );
        $validator = Validator::make($request->all(), $rules);
          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
          }
          $post->update($request->all());
          return response()->json([
            'success' => 'Poste modifié'
          ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($company_id, $post_id)
    {

        $data = Post::select('*')
        ->where('company_id', '=', $company_id)
        ->where('id', '=', $post_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $post = $data[0];

        if(!isset($post)) {return response()->json(['error' => 'Poste inexistant'],400);}

        Like::where('post_id', $post_id)->delete();

        if ($post->delete()){
            return response()->json([
                'success' => 'Poste supprimé'
            ], 200);
        }
    }
}
