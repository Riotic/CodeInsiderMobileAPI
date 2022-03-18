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
import android.widget.TextView;
import android.widget.Toast;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.EditPostEntreprise;
import com.codeinsider.Model.EditPostEtudiant;
import com.codeinsider.Model.Etudiant;
import com.codeinsider.Model.LoginResponseEntreprise;
import com.codeinsider.Model.LoginResponseEtudiant;
import com.codeinsider.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentProfileEditEtudiant extends Fragment {

    private Etudiant etudiant;

    private EditText editFirstnameEtudiant;
    private EditText editNameEtudiant;
    private EditText editBirthdayEtudiant;
    private EditText editAdressEtudiant;
    private EditText editPhoneNumberEtudiant;
    private EditText editCuriculumYearEtudiant;
    private EditText editRequestedRemunerationEtudiant;
    private EditText editContractTypeEtudiant;
    private EditText editContractTimeEtudiant;
    private EditText editEmailEtudiant;
    private EditText editSkillsEtudiant;

    private Button btnEdit;
    private TextView btnSupress;
    private String token;
    private SharedPreferences mPrefs;


    public FragmentProfileEditEtudiant(Etudiant etudiant, String token) {
        this.etudiant = etudiant;
        this.token = token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit_etudiant, container, false);

        //set all view
        setAllView(view);

        //display info
        displayInfo();

        //set button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSkillsEtudiant.getText().toString().isEmpty() || editContractTimeEtudiant.getText().toString().isEmpty() || editContractTypeEtudiant.getText().toString().isEmpty() || editRequestedRemunerationEtudiant.getText().toString().isEmpty() || editCuriculumYearEtudiant.getText().toString().isEmpty() || editFirstnameEtudiant.getText().toString().isEmpty() || editNameEtudiant.getText().toString().isEmpty() || editBirthdayEtudiant.getText().toString().isEmpty() || editAdressEtudiant.getText().toString().isEmpty() || editPhoneNumberEtudiant.getText().toString().isEmpty() || editEmailEtudiant.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vous devez compléter tout les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                startRetrofit("edit");
            }
        });

        btnSupress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRetrofit("delete");
            }
        });

        return view;
    }

    private void setAllView(View view) {
        btnEdit = view.findViewById(R.id.EditButtonEtudiant);
        btnSupress = view.findViewById(R.id.SuppressButtonEtudiant);

        editFirstnameEtudiant = view.findViewById(R.id.EditFirstnameEtudiant);
        editNameEtudiant = view.findViewById(R.id.EditNameEtudiant);
        editBirthdayEtudiant = view.findViewById(R.id.EditBirthdayEtudiant);
        editAdressEtudiant = view.findViewById(R.id.EditAdressEtudiant);
        editPhoneNumberEtudiant = view.findViewById(R.id.EditPhoneNumberEtudiant);
        editCuriculumYearEtudiant = view.findViewById(R.id.EditCuriculumYearEtudiant);
        editRequestedRemunerationEtudiant = view.findViewById(R.id.EditRequestedRemunerationEtudiant);
        editContractTypeEtudiant = view.findViewById(R.id.EditContractTypeEtudiant);
        editContractTimeEtudiant = view.findViewById(R.id.EditContractTimeEtudiant);
        editEmailEtudiant = view.findViewById(R.id.EditEmailEtudiant);
        editSkillsEtudiant = view.findViewById(R.id.EditSkillsEtudiant);

    }

    private void displayInfo() {
        editFirstnameEtudiant.setText(etudiant.getFirstname());
        editNameEtudiant.setText(etudiant.getName());
        editBirthdayEtudiant.setText(etudiant.getBirthday());
        editAdressEtudiant.setText(etudiant.getLocalization());
        editPhoneNumberEtudiant.setText(etudiant.getPhone_number());
        editCuriculumYearEtudiant.setText(etudiant.getCurriculum_year());
        editRequestedRemunerationEtudiant.setText(etudiant.getRequested_remuneration());
        editContractTypeEtudiant.setText(etudiant.getContract_type());
        editContractTimeEtudiant.setText(etudiant.getContract_time());
        editEmailEtudiant.setText(etudiant.getEmail());
        editSkillsEtudiant.setText(etudiant.getSkills());
    }

    private void startRetrofit(String mode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        if(mode.equals("edit")){
            editEntreprise(retrofitAPI, editSkillsEtudiant.getText().toString(), editContractTimeEtudiant.getText().toString(), editContractTypeEtudiant.getText().toString(), editRequestedRemunerationEtudiant.getText().toString(), editCuriculumYearEtudiant.getText().toString(), editFirstnameEtudiant.getText().toString(), editNameEtudiant.getText().toString(), editBirthdayEtudiant.getText().toString(), editAdressEtudiant.getText().toString(), editPhoneNumberEtudiant.getText().toString(), editEmailEtudiant.getText().toString());
        }
        else{
            supressEtudiant(retrofitAPI);
        }
    }

    private void editEntreprise(RetrofitAPI retrofitAPI, String skills, String contract_time, String contract_type, String remuneration, String curiculum, String firstname, String name, String birthday, String adress, String phone, String email) {
        EditPostEtudiant modal = new EditPostEtudiant(email, firstname, name, birthday, adress, curiculum, remuneration, skills, contract_type, contract_time, phone);

        Call<LoginResponseEtudiant> call = retrofitAPI.editStudent("Bearer " + token, modal, etudiant.getId());

        call.enqueue(new Callback<LoginResponseEtudiant>() {
            @Override
            public void onResponse(Call<LoginResponseEtudiant> call, Response<LoginResponseEtudiant> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    LoginResponseEtudiant responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Etudiant correctement actualisée !", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEtudiant(etudiant.getId())).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseEtudiant> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });
    }

    private void supressEtudiant(RetrofitAPI retrofitAPI) {
        Call<LoginResponseEtudiant> call = retrofitAPI.deleteStudent("Bearer " + token, etudiant.getId());

        call.enqueue(new Callback<LoginResponseEtudiant>() {
            @Override
            public void onResponse(Call<LoginResponseEtudiant> call, Response<LoginResponseEtudiant> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    LoginResponseEtudiant etudiantReturn = response.body();
                    Toast.makeText(getContext(), "Etudiant correctement supprimée", Toast.LENGTH_SHORT).show();
                    mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.clear().apply();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseEtudiant> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });

    }
}