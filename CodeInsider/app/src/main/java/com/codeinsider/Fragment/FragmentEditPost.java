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
import com.codeinsider.Model.Post;
import com.codeinsider.Model.ResponsePost;
import com.codeinsider.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentEditPost extends Fragment {

    private Post post;

    private EditText editPostName;
    private EditText editPostContract;
    private EditText editPostDesc;
    private Button btnEdit;

    private String token;
    private String ID;
    private SharedPreferences mPrefs;

    public FragmentEditPost(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        //get info
        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        ID = (mPrefs.getString("ID", ""));
        token = (mPrefs.getString("TOKEN", ""));

        //set view
        setView(view);

        //set button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPostName.getText().toString().isEmpty() || editPostDesc.getText().toString().isEmpty() || editPostContract.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vous devez compléter tout les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                //call to create post
                postData(editPostName.getText().toString(), editPostContract.getText().toString(), editPostDesc.getText().toString());
            }
        });

        return view;
    }

    private void setView(View view){
        editPostName = view.findViewById(R.id.EditPostName);
        editPostDesc = view.findViewById(R.id.EditPostDescription);
        editPostContract = view.findViewById(R.id.EditContractType);
        btnEdit = view.findViewById(R.id.ButtonEditPost);

        editPostName.setText(post.getJob_name());
        editPostDesc.setText(post.getDescription());
        editPostContract.setText(post.getContract_type());
    }

    private void postData(String name, String contract, String description){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        EditPost modal = new EditPost(name, contract, description);

        Call<ResponsePost> call = retrofitAPI.editPost("Bearer " + token, modal, post.getCompany_id(), post.getId());

        call.enqueue(new Callback<ResponsePost>() {
            @Override
            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    ResponsePost responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Le post à bien été modifié !", Toast.LENGTH_SHORT).show();
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