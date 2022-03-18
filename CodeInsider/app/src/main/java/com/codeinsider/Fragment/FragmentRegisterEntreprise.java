package com.codeinsider.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.Entreprise;
import com.codeinsider.Model.PostEntreprise;
import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentRegisterEntreprise extends Fragment {

    private Button registerButtonEntreprise;
    private TextView registerTextEntreprise;
    private EditText registerNameEntreprise;
    private EditText registerLocalizationEntreprise;
    private EditText registerDescriptionEntreprise;
    private EditText registerPhoneNumberEntreprise;
    private EditText registerEmailEntreprise;
    private EditText registerPasswordEntreprise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_entreprise, container, false);

        //set view
        setAllView(view);

        //set button
        registerButtonEntreprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validating if the text field is empty or not.
                if (registerDescriptionEntreprise.getText().toString().isEmpty() ||  registerNameEntreprise.getText().toString().isEmpty() || registerLocalizationEntreprise.getText().toString().isEmpty() || registerPhoneNumberEntreprise.getText().toString().isEmpty() || registerEmailEntreprise.getText().toString().isEmpty() || registerPasswordEntreprise.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vous devez compléter tout les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                //send request to create company
                postData(registerNameEntreprise.getText().toString(), registerLocalizationEntreprise.getText().toString(), registerDescriptionEntreprise.getText().toString(), registerPhoneNumberEntreprise.getText().toString(), registerEmailEntreprise.getText().toString(), registerPasswordEntreprise.getText().toString());
            }
        });

        registerTextEntreprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void setAllView(View view) {
        registerButtonEntreprise = view.findViewById(R.id.RegisterButtonEntreprise);
        registerTextEntreprise = view.findViewById(R.id.RegisterTextEnteprise);
        registerNameEntreprise = view.findViewById(R.id.RegisterNameEntreprise);
        registerLocalizationEntreprise = view.findViewById(R.id.RegisterLocalizationEntreprise);
        registerDescriptionEntreprise = view.findViewById(R.id.RegisterDescriptionEntreprise);
        registerPhoneNumberEntreprise = view.findViewById(R.id.RegisterPhoneNumberEntreprise);
        registerEmailEntreprise = view.findViewById(R.id.RegisterEmailEntreprise);
        registerPasswordEntreprise = view.findViewById(R.id.RegisterPasswordEntreprise);
    }

    private void postData(String name, String localization, String description, String phoneNumber, String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostEntreprise modal = new PostEntreprise(name, localization, description, phoneNumber, email, password);

        Call<Entreprise> call = retrofitAPI.createCompany(modal);

        call.enqueue(new Callback<Entreprise>() {
            @Override
            public void onResponse(Call<Entreprise> call, Response<Entreprise> response) {

                registerNameEntreprise.setText("");
                registerLocalizationEntreprise.setText("");
                registerPhoneNumberEntreprise.setText("");
                registerEmailEntreprise.setText("");
                registerPasswordEntreprise.setText("");
                registerDescriptionEntreprise.setText("");

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    Entreprise responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Le compte à bien été crée !", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<Entreprise> call, Throwable t) {
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });
    }

}