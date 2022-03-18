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
import com.codeinsider.Model.Etudiant;
import com.codeinsider.Model.PostEtudiant;
import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentRegisterEtudiant extends Fragment {

    private Button registerButtonEtudiant;
    private TextView registerTextEtudiant;
    private EditText registerFirstNameEtudiant;
    private EditText registerNameEtudiant;
    private EditText registerBirthdayEtudiant;
    private EditText registerLocalizationEtudiant;
    private EditText registerSkillsEtudiant;
    private EditText registerPhoneNumberEtudiant;
    private EditText registerCuriculumYearEtudiant;
    private EditText registerRequestedRemunerationEtudiant;
    private EditText registerContractTypeEtudiant;
    private EditText registerContractTimeEtudiant;
    private EditText registerEmailEtudiant;
    private EditText registerPasswordEtudiant;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_etudiant, container, false);

        //set view
        setAllView(view);

        //set button
        registerButtonEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validating if the text field is empty or not.
                if (registerSkillsEtudiant.getText().toString().isEmpty() || registerContractTimeEtudiant.getText().toString().isEmpty() || registerContractTypeEtudiant.getText().toString().isEmpty() || registerRequestedRemunerationEtudiant.getText().toString().isEmpty() || registerCuriculumYearEtudiant.getText().toString().isEmpty() || registerFirstNameEtudiant.getText().toString().isEmpty() || registerNameEtudiant.getText().toString().isEmpty() || registerBirthdayEtudiant.getText().toString().isEmpty() || registerLocalizationEtudiant.getText().toString().isEmpty() || registerPhoneNumberEtudiant.getText().toString().isEmpty() || registerEmailEtudiant.getText().toString().isEmpty() || registerPasswordEtudiant.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vous devez compléter tout les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                //send data to create student
                postData(registerContractTimeEtudiant.getText().toString(), registerContractTypeEtudiant.getText().toString(), registerRequestedRemunerationEtudiant.getText().toString(), registerCuriculumYearEtudiant.getText().toString(), registerFirstNameEtudiant.getText().toString(), registerNameEtudiant.getText().toString(), registerBirthdayEtudiant.getText().toString(), registerLocalizationEtudiant.getText().toString(), registerSkillsEtudiant.getText().toString(), registerPhoneNumberEtudiant.getText().toString(), registerEmailEtudiant.getText().toString(), registerPasswordEtudiant.getText().toString());
            }
        });

        registerTextEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void setAllView(View view) {
        registerButtonEtudiant = view.findViewById(R.id.RegisterButtonEtudiant);
        registerTextEtudiant = view.findViewById(R.id.RegisterTextEtudiant);
        registerFirstNameEtudiant = view.findViewById(R.id.RegisterFirstNameEtudiant);
        registerNameEtudiant = view.findViewById(R.id.RegisterNameEtudiant);
        registerBirthdayEtudiant = view.findViewById(R.id.RegisterBirthdayEtudiant);
        registerLocalizationEtudiant = view.findViewById(R.id.RegisterLocalizationEtudiant);
        registerPhoneNumberEtudiant = view.findViewById(R.id.RegisterPhoneNumberEtudiant);
        registerCuriculumYearEtudiant = view.findViewById(R.id.RegisterCuriculumYearEtudiant);
        registerRequestedRemunerationEtudiant = view.findViewById(R.id.RegisterRequestedRemunerationEtudiant);
        registerContractTypeEtudiant = view.findViewById(R.id.RegisterContractTypeEtudiant);
        registerContractTimeEtudiant = view.findViewById(R.id.RegisterContractTimeEtudiant);
        registerSkillsEtudiant = view.findViewById(R.id.RegisterSkillsEtudiant);
        registerEmailEtudiant = view.findViewById(R.id.RegisterEmailEtudiant);
        registerPasswordEtudiant = view.findViewById(R.id.RegisterPasswordEtudiant);
    }

    private void postData(String contract_time, String contract_type ,String remuneration, String curiculum, String firstname, String name, String birthday, String localization, String skills, String phone_number, String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        PostEtudiant modal = new PostEtudiant(email, firstname, name, birthday, localization, curiculum, remuneration, skills, contract_type, contract_time, phone_number, password);

        Call<Etudiant> call = retrofitAPI.createStudent(modal);

        call.enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {

                registerFirstNameEtudiant.setText("");
                registerNameEtudiant.setText("");
                registerBirthdayEtudiant.setText("");
                registerLocalizationEtudiant.setText("");
                registerPhoneNumberEtudiant.setText("");
                registerEmailEtudiant.setText("");
                registerPasswordEtudiant.setText("");
                registerCuriculumYearEtudiant.setText("");
                registerRequestedRemunerationEtudiant.setText("");
                registerContractTypeEtudiant.setText("");
                registerContractTimeEtudiant.setText("");
                registerSkillsEtudiant.setText("");

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("SUUU", responseString);
                    Toast.makeText(getContext(), "Email déjà pris", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    Etudiant responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Le compte à bien été crée !", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();

                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });
    }
}