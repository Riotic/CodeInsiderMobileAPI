package com.codeinsider.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.EditPost;
import com.codeinsider.Model.Entreprise;
import com.codeinsider.Model.Post;
import com.codeinsider.Model.PostEntreprise;
import com.codeinsider.Model.ResponsePost;
import com.codeinsider.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCreatePost extends Fragment {

    private EditText createPostName;
    private EditText createPostContract;
    private EditText createPostDesc;
    private Button btnCreate;

    private String token;
    private String ID;
    private SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        //get info
        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        ID = (mPrefs.getString("ID", ""));
        token = (mPrefs.getString("TOKEN", ""));

        //set view
        setView(view);

        //set button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createPostName.getText().toString().isEmpty() || createPostDesc.getText().toString().isEmpty() || createPostContract.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vous devez compléter tout les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                //call to create post
                postData(createPostName.getText().toString(), createPostContract.getText().toString(), createPostDesc.getText().toString());
            }
        });

        return view;
    }

    private void setView(View view){
        createPostName = view.findViewById(R.id.CreatePostName);
        createPostDesc = view.findViewById(R.id.CreatePostDescription);
        createPostContract = view.findViewById(R.id.CreateContractType);
        btnCreate = view.findViewById(R.id.ButtonCreatePost);
    }

    private void postData(String name, String contract, String description){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        EditPost modal = new EditPost(name, contract, description);

        Call<ResponsePost> call = retrofitAPI.createPost("Bearer " + token, modal, ID);

        call.enqueue(new Callback<ResponsePost>() {
            @Override
            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                createPostName.setText("");
                createPostContract.setText("");
                createPostDesc.setText("");

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    ResponsePost responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Le post à bien été crée !", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEntreprise(ID)).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<ResponsePost> call, Throwable t) {
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });
    }
}