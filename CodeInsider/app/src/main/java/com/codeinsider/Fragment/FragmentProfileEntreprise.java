package com.codeinsider.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codeinsider.Adapter.PostAdapter;
import com.codeinsider.Interface.PostSelectListener;
import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.Entreprise;
import com.codeinsider.Model.Post;
import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentProfileEntreprise extends Fragment implements PostSelectListener{

    private TextView profileNameEntreprise;
    private TextView profileLocalizationEntreprise;
    private TextView profileEmailEntreprise;
    private TextView profilePhoneEntreprise;
    private TextView profileDescriptionEntreprise;
    private Button btnSendMail;
    private Button btnLogout;
    private ImageButton btnEdit;

    private RecyclerView recyclerView;
    private List<Post> postList;
    private PostAdapter postAdapter;

    private String id_entreprise;
    private String token;
    private String ID;
    private String TYPE_USER;
    private String cmp;
    private SharedPreferences mPrefs;

    private Entreprise entreprise;

    public FragmentProfileEntreprise(String id_entreprise) {
        this.id_entreprise = id_entreprise;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_entreprise, container, false);

        //setview
        setAllView(view);

        //get info
        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        TYPE_USER = (mPrefs.getString("TYPE_USER", ""));
        token = (mPrefs.getString("TOKEN", ""));

        //set navbar etudiant visible
        BottomNavigationView navigationEntreprise = getActivity().findViewById(R.id.navigation_entreprise);
        BottomNavigationView navigationEtudiant = getActivity().findViewById(R.id.navigation_etudiant);

        if(TYPE_USER.equals("Student")){
            navigationEntreprise.setVisibility(View.GONE);
            navigationEtudiant.setVisibility(View.VISIBLE);
            btnSendMail.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            cmp = "etudiant";
            //setInfo
            callEntreprise(id_entreprise, view, this.getContext());
        }
        else {
            navigationEntreprise.setVisibility(View.VISIBLE);
            navigationEtudiant.setVisibility(View.GONE);
            btnSendMail.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
            cmp = "entreprise";
            ID = (mPrefs.getString("ID", ""));
            //setInfo
            callEntreprise(ID, view, this.getContext());
        }

        //setButton
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send mail
                String [] mails = {entreprise.getEmail()};
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(Intent.EXTRA_EMAIL, mails );
                startActivity(Intent.createChooser(i, ""));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.clear().apply();
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEditEntreprise(entreprise, token)).addToBackStack(null).commit();
            }
        });

        return view;
    }


    private void setAllView(View view) {
        btnEdit = view.findViewById(R.id.ProfileEditEntreprise);
        profileNameEntreprise = view.findViewById(R.id.ProfileNameEntreprise);
        profileLocalizationEntreprise = view.findViewById(R.id.ProfileLocalizationEntreprise);
        profileEmailEntreprise = view.findViewById(R.id.ProfileEmailEntreprise);
        profilePhoneEntreprise = view.findViewById(R.id.ProfilePhoneEntreprise);
        profileDescriptionEntreprise = view.findViewById(R.id.ProfileDescriptionEntreprise);
        btnSendMail = view.findViewById(R.id.ProfileSendMailEntreprise);
        btnLogout = view.findViewById(R.id.ProfileLogoutEntreprise);
    }

    private void displayEntreprise(View view, Context context) {
        profileNameEntreprise.setText(entreprise.getName());
        profileLocalizationEntreprise.setText(entreprise.getLocalization());
        profileEmailEntreprise.setText(entreprise.getEmail());
        profilePhoneEntreprise.setText(entreprise.getPhone_number());
        profileDescriptionEntreprise.setText(entreprise.getDescription());

        displayRecycler(view, context);
    }

    private void callEntreprise(String id, View view, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<Entreprise> call = retrofitAPI.getCompany(id);

        call.enqueue(new Callback<Entreprise>() {
            @Override
            public void onResponse(Call<Entreprise> call, Response<Entreprise> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    entreprise = response.body();
                    displayEntreprise(view, context);
                }
            }

            @Override
            public void onFailure(Call<Entreprise> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }

    @Override
    public void onItemPostClicked(Post post) {
        getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentPost(post)).addToBackStack(null).commit();
    }


    @Override
    public void onItemDeleteClicked(Post post) {
        getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentDeletePost(post)).addToBackStack(null).commit();
    }

    @Override
    public void onItemEditClicked(Post post) {
        getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentEditPost(post)).addToBackStack(null).commit();
    }

    private void displayRecycler(View view, Context context) {
        recyclerView = view.findViewById(R.id.ProfileRecyclerViewEntreprise);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        //input of json here
        postList = new ArrayList<>();

        getPosts();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                displayFinish(context);
            }
        }, 150);
    }

    private void displayFinish(Context context) {
        postAdapter = new PostAdapter(context, postList, this, cmp);
        recyclerView.setAdapter(postAdapter);
    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Post>> call = retrofitAPI.getOneEntrepriseAllPosts(entreprise.getId());

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    postList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}