package com.codeinsider.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentRegisterChoice extends Fragment {

    private Button choiceRegisterEtudiant, choiceRegisterEntreprise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_choice, container, false);

        //set view
        choiceRegisterEtudiant = view.findViewById(R.id.ChoiceRegisterEtudiant);
        choiceRegisterEntreprise = view.findViewById(R.id.ChoiceRegisterEntreprise);

        //set button
        choiceRegisterEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentRegisterEtudiant()).addToBackStack(null).commit();
            }
        });

        choiceRegisterEntreprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentRegisterEntreprise()).addToBackStack(null).commit();
            }
        });

        return view;
    }
}