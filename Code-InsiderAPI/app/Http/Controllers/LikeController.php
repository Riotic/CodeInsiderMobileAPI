<?php

namespace App\Http\Controllers;

use App\Models\Like;
use App\Models\Post;
use Illuminate\Http\Request;


class LikeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function likeornot($student_id, $post_id)
    {
        $data = Like::select('*')
        ->where('student_id', '=', $student_id)
        ->where('post_id', '=', $post_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $liked = $data[0];

        if(isset($liked)){
            return response()->json([
                'success' => $liked->liked
            ], 200);
        }
        return response()->json([
            'success' => 0
        ], 200);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function liked(Request $request, $student_id, $post_id)
    {
        $data = Like::select('*')
        ->where('student_id', '=', $student_id)
        ->where('post_id', '=', $post_id)
        ->limit(1)
        ->get();

        if(count($data) > 0) $liked = $data[0];

        if(!isset($liked)){
            $array = ([
                'student_id' => $student_id,
                'post_id' => $post_id,
                'liked' => 1,
            ]);
            Like::create($array);
            return response()->json([
                'success' => 'Post liked'
            ], 200);
        }

        $liked['student_id'] = $student_id;
        if($liked['liked'] == 0) {
            $liked['liked'] = 1;
            $liked->update($request->all());
            return response()->json([
                'success' => 'Post liked'
              ], 200);
        }
        $liked['liked'] = 0;
        $liked->update($request->all());
        return response()->json([
            'success' => 'Post unliked'
          ], 200);
    }


        /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function getlike($student_id)
    {
        $data = Like::select('*')
        ->where('student_id', '=', $student_id)
        ->get();

        
        $i = count($data) -1;
        $finaldata = [];
        while($i >= 0)
        {
            if($data[$i]->liked == 1){
                array_push($finaldata, Post::findOrFail($data[$i]->post_id));
            }
            $i--;
        };

        return response()->json([
            'success' => $finaldata
        ], 200);
        
    }

}
