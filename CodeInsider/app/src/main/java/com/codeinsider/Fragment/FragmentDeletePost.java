package com.codeinsider.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.LoginResponseEntreprise;
import com.codeinsider.Model.Post;
import com.codeinsider.Model.ResponsePost;
import com.codeinsider.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentDeletePost extends Fragment{

    private Post post;
    private SharedPreferences mPrefs;
    private String token;

    public FragmentDeletePost(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_post, container, false);

        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        token = (mPrefs.getString("TOKEN", ""));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<ResponsePost> call = retrofitAPI.deletePost("Bearer " + token, post.getCompany_id(), post.getId());

        call.enqueue(new Callback<ResponsePost>() {
            @Override
            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    ResponsePost entrepriseReturn = response.body();
                    Toast.makeText(getContext(), "Post correctement supprimée", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEntreprise(post.getCompany_id())).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<ResponsePost> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });

        return view;
    }

}