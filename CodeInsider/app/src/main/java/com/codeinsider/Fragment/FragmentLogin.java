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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.LoginRequest;
import com.codeinsider.Model.LoginResponseEntreprise;
import com.codeinsider.Model.LoginResponseEtudiant;
import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentLogin extends Fragment {

    private TextView loginTextRegister;
    private RadioButton loginButtonEtudiant, loginButtonEntreprise;
    private Button loginButton;
    private EditText loginEmail, loginPassword;
    private LoginRequest loginRequest;
    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;
    private BottomNavigationView navigationEntreprise, navigationEtudiant;
    String loginChoice = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //set navbar invisible
        navigationEntreprise = getActivity().findViewById(R.id.navigation_entreprise);
        navigationEtudiant = getActivity().findViewById(R.id.navigation_etudiant);
        navigationEntreprise.setVisibility(View.GONE);
        navigationEtudiant.setVisibility(View.GONE);

        //set view
        setAllView(view);

        //setup button
        loginButtonEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginChoice = "Etudiant";
            }
        });

        loginButtonEntreprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginChoice = "Entreprise";
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifLogin();
            }
        });

        loginTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentRegisterChoice()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void setAllView(View view) {
        loginButtonEtudiant = view.findViewById(R.id.LoginRadioOne);
        loginButtonEntreprise = view.findViewById(R.id.LoginRadioTwo);
        loginTextRegister = view.findViewById(R.id.LoginTextRegister);
        loginButton = view.findViewById(R.id.LoginButton);
        loginEmail = view.findViewById(R.id.LoginEmail);
        loginPassword = view.findViewById(R.id.LoginPassword);
    }

    private void verifLogin() {
        if(loginEmail.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Vous devez compléter tout les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        if(loginChoice == ""){
            Toast.makeText(getContext(), "Vous devez séléctioner un profile", Toast.LENGTH_SHORT).show();
            return;
        }

        //setup login request and start login
        loginRequest = new LoginRequest();
        loginRequest.setEmail(loginEmail.getText().toString());
        loginRequest.setPassword(loginPassword.getText().toString());

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);

        if(loginChoice == "Etudiant"){
            startLoginEtudiant();
        }
        else if(loginChoice == "Entreprise"){
            startLoginEntreprise();
        }
    }

    private void startLoginEtudiant() {
        Call<LoginResponseEtudiant> call = retrofitAPI.authStudent(loginRequest);

        call.enqueue(new Callback<LoginResponseEtudiant>() {
            @Override
            public void onResponse(Call<LoginResponseEtudiant> call, Response<LoginResponseEtudiant> response) {
                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Toast.makeText(getContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    String UserId = response.body().user.getId();
                    String UserTOKEN = response.body().getApi_token();
                    //load info
                    SharedPreferences mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("ID", UserId).apply();
                    mEditor.putString("TOKEN", UserTOKEN).apply();
                    mEditor.putString("TYPE_USER", "Student").apply();
                    //set visible navbar
                    navigationEntreprise.setVisibility(View.GONE);
                    navigationEtudiant.setVisibility(View.VISIBLE);
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentFeedEtudiant()).addToBackStack(null).commit();
                }
            }
            @Override
            public void onFailure(Call<LoginResponseEtudiant> call, Throwable t) {
                Log.d("ERROR", "CALL FAILURE");
            }
        });
    }

    private void startLoginEntreprise() {
        Call<LoginResponseEntreprise> call = retrofitAPI.authCompany(loginRequest);

        call.enqueue(new Callback<LoginResponseEntreprise>() {
            @Override
            public void onResponse(Call<LoginResponseEntreprise> call, Response<LoginResponseEntreprise> response) {
                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Toast.makeText(getContext(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    String UserId = response.body().user.getId();
                    String UserTOKEN = response.body().getApi_token();
                    //load info
                    SharedPreferences mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("ID", UserId).apply();
                    mEditor.putString("TOKEN", UserTOKEN).apply();
                    mEditor.putString("TYPE_USER", "Company").apply();
                    //set navbar visible
                    navigationEntreprise.setVisibility(View.VISIBLE);
                    navigationEtudiant.setVisibility(View.GONE);
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentFeedEntreprise()).commit();
                }
            }
            @Override
            public void onFailure(Call<LoginResponseEntreprise> call, Throwable t) {
                Log.d("ERROR", "CALL FAILURE");
            }
        });
    }
}