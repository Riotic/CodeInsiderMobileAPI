package com.codeinsider.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codeinsider.Adapter.EtudiantAdapter;
import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.Entreprise;
import com.codeinsider.Model.EntrepriseT;
import com.codeinsider.Model.Etudiant;
import com.codeinsider.Model.Post;
import com.codeinsider.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentPost extends Fragment {

    private TextView postJobName;
    private TextView postJobType;
    private TextView postCompanyName;
    private TextView postCompanyAdress;
    private TextView postCompanyPhone;
    private TextView postDescription;
    private ImageButton postLikebtn;
    private ImageButton postEditbtn;
    private ImageButton postDeletebtn;

    private String token;
    private String ID;
    private String TYPE_USER;
    private SharedPreferences mPrefs;

    private Post post;
    private EntrepriseT entreprise;

    public FragmentPost(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //get info
        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        TYPE_USER = (mPrefs.getString("TYPE_USER", ""));
        Log.d("ok", ""+TYPE_USER);
        token = (mPrefs.getString("TOKEN", ""));
        ID = (mPrefs.getString("ID", ""));

        //set all view
        setAllView(view);

        //get entreprise
        getEntreprise();

        postCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEntreprise(entreprise.getId())).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void displayView(EntrepriseT entreprise){
        postJobName.setText(post.getJob_name());
        postJobType.setText(post.getContract_type());
        postCompanyName.setText(entreprise.getName());
        postCompanyAdress.setText(entreprise.getLocalization());
        postCompanyPhone.setText(entreprise.getPhone_number());
        postDescription.setText(post.getDescription());

        if(TYPE_USER.equals("Student")){
            postLikebtn.setVisibility(View.VISIBLE);
            postEditbtn.setVisibility(View.GONE);
            postDeletebtn.setVisibility(View.GONE);
        }
        else{
            postLikebtn.setVisibility(View.GONE);
            postEditbtn.setVisibility(View.VISIBLE);
            postDeletebtn.setVisibility(View.VISIBLE);
        }
    }

    private void setAllView(View view) {
        postJobName = view.findViewById(R.id.PostJobName);
        postJobType = view.findViewById(R.id.PostJobType);
        postCompanyName = view.findViewById(R.id.PostCompanyName);
        postCompanyAdress = view.findViewById(R.id.PostCompanyAdress);
        postCompanyPhone = view.findViewById(R.id.PostCompanyPhone);
        postDescription = view.findViewById(R.id.PostDescription);
        postLikebtn = view.findViewById(R.id.PostHeartbtn);
        postEditbtn = view.findViewById(R.id.PostEditBtn);
        postDeletebtn = view.findViewById(R.id.PostDeleteBtn);
    }

    private void getEntreprise() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<EntrepriseT> call = retrofitAPI.getCompanyT(post.getCompany_id());

        call.enqueue(new Callback<EntrepriseT>() {
            @Override
            public void onResponse(Call<EntrepriseT> call, Response<EntrepriseT> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    entreprise = response.body();
                    //display View
                    displayView(entreprise);
                }
            }

            @Override
            public void onFailure(Call<EntrepriseT> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}