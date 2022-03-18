package com.codeinsider.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FragmentHomePage extends Fragment {

    private Button homeRegister;
    private TextView homeLogin;
    private ImageView lamp_bule;
    private Animation mAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        //set by view id
        homeRegister = view.findViewById(R.id.HomeRegisterButton);
        homeLogin = view.findViewById(R.id.HomeLoginText);
        lamp_bule = view.findViewById(R.id.lampe_bule);


        //animation de la lampe
        startLampeAnimation();

        //setup button
        setButtonAction();

        return view;
    }

    private void startLampeAnimation() {
        mAnimation = new TranslateAnimation(0, 0, 0, 30);
        mAnimation.setDuration(1250);
        mAnimation.setFillAfter(true);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        lamp_bule.setAnimation(mAnimation);
        lamp_bule.setVisibility(View.VISIBLE);
    }

    private void setButtonAction() {

        homeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentRegisterChoice()).addToBackStack(null).commit();
            }
        });

        homeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentLogin()).addToBackStack(null).commit();
            }
        });

    }
}