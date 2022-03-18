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
import android.widget.TextView;
import android.widget.Toast;

import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.EditPostEntreprise;
import com.codeinsider.Model.Entreprise;
import com.codeinsider.Model.LoginResponseEntreprise;
import com.codeinsider.Model.PostEntreprise;
import com.codeinsider.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentProfileEditEntreprise extends Fragment {

    private Entreprise entreprise;
    private String token;

    private TextView editNameEntreprise;
    private TextView editAdressEntreprise;
    private TextView editDescriptionEntreprise;
    private TextView editPhoneNumberEntreprise;
    private TextView editEmailEntreprise;

    private Button btnEdit;
    private TextView btnSupress;
    private SharedPreferences mPrefs;

    public FragmentProfileEditEntreprise(Entreprise entreprise, String token) {
        this.entreprise = entreprise;
        this.token = token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit_entreprise, container, false);

        //set all view
        setAllView(view);

        //display info
        displayInfo();

        //set button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDescriptionEntreprise.getText().toString().isEmpty() ||  editNameEntreprise.getText().toString().isEmpty() || editAdressEntreprise.getText().toString().isEmpty() || editPhoneNumberEntreprise.getText().toString().isEmpty() || editEmailEntreprise.getText().toString().isEmpty()) {
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
        editNameEntreprise = view.findViewById(R.id.EditNameEntreprise);
        editAdressEntreprise = view.findViewById(R.id.EditAdressEntreprise);
        editDescriptionEntreprise = view.findViewById(R.id.EditDescriptionEntreprise);
        editPhoneNumberEntreprise = view.findViewById(R.id.EditPhoneNumberEntreprise);
        editEmailEntreprise = view.findViewById(R.id.EditEmailEntreprise);
        btnEdit = view.findViewById(R.id.EditButtonEntreprise);
        btnSupress = view.findViewById(R.id.SuppressButtonEntreprise);
    }

    private void  displayInfo(){
        editNameEntreprise.setText(entreprise.getName());
        editEmailEntreprise.setText(entreprise.getEmail());
        editAdressEntreprise.setText(entreprise.getLocalization());
        editPhoneNumberEntreprise.setText(entreprise.getPhone_number());
        editDescriptionEntreprise.setText(entreprise.getDescription());
    }

    private void startRetrofit(String mode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        if(mode.equals("edit")){
            editEntreprise(retrofitAPI, editNameEntreprise.getText().toString(), editDescriptionEntreprise.getText().toString(), editAdressEntreprise.getText().toString(), editEmailEntreprise.getText().toString(), editPhoneNumberEntreprise.getText().toString());
        }
        else{
            supressEntreprise(retrofitAPI);
        }
    }

    private void editEntreprise(RetrofitAPI retrofitAPI, String name, String description, String adress, String email, String phone) {

        EditPostEntreprise modal = new EditPostEntreprise(name, adress, description, phone, email);

        Call<LoginResponseEntreprise> call = retrofitAPI.editCompany("Bearer " + token, modal, entreprise.getId());

        call.enqueue(new Callback<LoginResponseEntreprise>() {
            @Override
            public void onResponse(Call<LoginResponseEntreprise> call, Response<LoginResponseEntreprise> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    String responseString = "Response Code : " + response.code();
                    LoginResponseEntreprise responseFromAPI = response.body();
                    Toast.makeText(getContext(), "Entreprise correctement actualisée !", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEntreprise(entreprise.getId())).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseEntreprise> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Log.d("ERROR", "Error found is : " + t.getMessage());
            }
        });
    }

    private void supressEntreprise(RetrofitAPI retrofitAPI) {
        Call<LoginResponseEntreprise> call = retrofitAPI.deleteCompany("Bearer " + token, entreprise.getId());

        call.enqueue(new Callback<LoginResponseEntreprise>() {
            @Override
            public void onResponse(Call<LoginResponseEntreprise> call, Response<LoginResponseEntreprise> response) {

                try {
                    String errorCatch = "Error code " + response.code() + " " + response.errorBody().string();
                    String responseString = "Response Code : " + errorCatch;
                    Log.d("ERROR CALL", responseString);

                } catch (Exception e) {
                    LoginResponseEntreprise entrepriseReturn = response.body();
                    Toast.makeText(getContext(), "Entreprise correctement supprimée", Toast.LENGTH_SHORT).show();
                    mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.clear().apply();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseEntreprise> call, Throwable t) {
                Log.d("ERROR CALL", "Callback failure");
            }
        });
    }
}