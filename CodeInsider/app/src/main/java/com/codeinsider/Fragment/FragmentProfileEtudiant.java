package com.codeinsider.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.Etudiant;
import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentProfileEtudiant extends Fragment {

    private TextView profileNameEtudiant;
    private TextView profileFirstnameEtudiant;
    private TextView profileBirthdayEtudiant;
    private TextView profileCursusEtudiant;
    private TextView profileLocalizationEtudiant;
    private TextView profileSkillsEtudiant;
    private TextView profileContractTypeEtudiant;
    private TextView profileContractTimeEtudiant;
    private TextView profileRemunerationEtudiant;
    private TextView profileEmailEtudiant;
    private TextView profilePhoneEtudiant;
    private Button btnSendMail;
    private Button btnLogout;
    private ImageButton btnEdit;

    private String id_student;
    private String ID;
    private String TYPE_USER;
    private String token;
    private SharedPreferences mPrefs;

    private Etudiant etudiant;

    public FragmentProfileEtudiant(String id_student) {
        this.id_student = id_student;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_etudiant, container, false);

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
            btnSendMail.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);

            ID = (mPrefs.getString("ID", ""));
            //setInfo
            callStudent(ID);
        }
        else {
            navigationEntreprise.setVisibility(View.VISIBLE);
            navigationEtudiant.setVisibility(View.GONE);
            btnSendMail.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            //setInfo
            callStudent(id_student);
        }

        //setButton
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send mail
                String [] mails = {etudiant.getEmail()};
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
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEditEtudiant(etudiant, token)).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void setAllView(View view) {
        btnEdit = view.findViewById(R.id.ProfileEditEtudiant);
        btnLogout = view.findViewById(R.id.ProfileLogoutEtudiant);
        btnSendMail = view.findViewById(R.id.ProfileSendMailEtudiant);
        profileNameEtudiant = view.findViewById(R.id.ProfileNameEtudiant);
        profileFirstnameEtudiant = view.findViewById(R.id.ProfileFirstnameEtudiant);
        profileBirthdayEtudiant = view.findViewById(R.id.ProfileBirthdayEtudiant);
        profileCursusEtudiant = view.findViewById(R.id.ProfileCursusEtudiant);
        profileLocalizationEtudiant = view.findViewById(R.id.ProfileLocalizationEtudiant);
        profileSkillsEtudiant = view.findViewById(R.id.ProfileSkillsEtudiant);
        profileContractTypeEtudiant = view.findViewById(R.id.ProfileContractTypeEtudiant);
        profileContractTimeEtudiant = view.findViewById(R.id.ProfileContractTimeEtudiant);
        profileRemunerationEtudiant = view.findViewById(R.id.ProfileRemunerationEtudiant);
        profileEmailEtudiant = view.findViewById(R.id.ProfileEmailEtudiant);
        profilePhoneEtudiant = view.findViewById(R.id.ProfilePhoneEntudiant);
    }

    private void displayEtudiant() {
        profileNameEtudiant.setText(etudiant.getName());
        profileFirstnameEtudiant.setText(etudiant.getFirstname());
        profileBirthdayEtudiant.setText(etudiant.getBirthday());
        profileCursusEtudiant.setText(etudiant.getCurriculum_year());
        profileLocalizationEtudiant.setText(etudiant.getLocalization());
        profileSkillsEtudiant.setText(etudiant.getSkills());
        profileContractTypeEtudiant.setText(etudiant.getContract_type());
        profileContractTimeEtudiant.setText(etudiant.getContract_time());
        profileRemunerationEtudiant.setText(etudiant.getRequested_remuneration());
        profileEmailEtudiant.setText(etudiant.getEmail());
        profilePhoneEtudiant.setText(etudiant.getPhone_number());
    }

    private void callStudent(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<Etudiant> call = retrofitAPI.getStudent(id);

        call.enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    etudiant = response.body();
                    displayEtudiant();
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}