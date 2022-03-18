package com.codeinsider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.codeinsider.Fragment.FragmentCreatePost;
import com.codeinsider.Fragment.FragmentFeedEntreprise;
import com.codeinsider.Fragment.FragmentFeedEtudiant;
import com.codeinsider.Fragment.FragmentHomePage;
import com.codeinsider.Fragment.FragmentProfileEntreprise;
import com.codeinsider.Fragment.FragmentProfileEtudiant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    public String TOKEN;
    public String ID;
    public String TYPE_USER;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set navigation bar
        toolbar = getSupportActionBar();

        BottomNavigationView navigationEntreprise = findViewById(R.id.navigation_entreprise);
        BottomNavigationView navigationEtudiant = findViewById(R.id.navigation_etudiant);

        //setup click nav bar
        navigationEntreprise.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return navigationSelectEntreprise(item);
            }
        });

        navigationEtudiant.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return navigationSelectEtudiant(item);
            }
        });

        //go to the home page or if already log got to feed
        mPrefs = getPreferences(MODE_PRIVATE);
        TOKEN = (mPrefs.getString("TOKEN", ""));
        ID = (mPrefs.getString("ID", ""));
        TYPE_USER = (mPrefs.getString("TYPE_USER", ""));
        if(TOKEN.equals("") || ID.equals("") || TYPE_USER.equals("")) {
            navigationEntreprise.setVisibility(View.GONE);
            navigationEtudiant.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, new FragmentHomePage()).commit();
        }
        else{
            if(TYPE_USER.equals("Student")){
                navigationEntreprise.setVisibility(View.GONE);
                navigationEtudiant.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.main_container, new FragmentFeedEtudiant()).commit();
            }
            else{
                navigationEntreprise.setVisibility(View.VISIBLE);
                navigationEtudiant.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.main_container, new FragmentFeedEntreprise()).commit();
            }
        }
    }



    private boolean navigationSelectEntreprise(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEntreprise("")).commit();
                return true;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentFeedEntreprise()).commit();
                return true;
            case R.id.nav_chat_en:
                return true;
            case R.id.nav_edit:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreatePost()).commit();
                return true;
        }
        return false;
    }

    private boolean navigationSelectEtudiant(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentProfileEtudiant("")).commit();
                return true;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentFeedEtudiant()).commit();
                return true;
            case R.id.nav_chat_et:
                return true;
        }
        return false;
    }
}