package com.codeinsider.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.codeinsider.Adapter.PostAdapter;
import com.codeinsider.Interface.PostSelectListener;
import com.codeinsider.Interface.RetrofitAPI;
import com.codeinsider.Model.Post;
import com.codeinsider.Popup;
import com.codeinsider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentFeedEtudiant extends Fragment implements PostSelectListener {

    private RecyclerView recyclerView;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private String USER_ID;
    private String token;

    private ImageButton allFilterBtn;

    private SearchView search_bar;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_etudiant, container, false);

        //get Id user
        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        USER_ID = (mPrefs.getString("ID", ""));
        token = (mPrefs.getString("TOKEN", ""));

        //set navbar etudiant visible
        BottomNavigationView navigationEntreprise = getActivity().findViewById(R.id.navigation_entreprise);
        BottomNavigationView navigationEtudiant = getActivity().findViewById(R.id.navigation_etudiant);
        navigationEntreprise.setVisibility(View.GONE);
        navigationEtudiant.setVisibility(View.VISIBLE);

        //set search bar
        search_bar = view.findViewById(R.id.SearchViewStudentFeedEtudiant);
        search_bar.setQueryHint("Rechercher ici...");

        searchAutoComplete = search_bar.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.BLACK);

        //set filter view
        allFilterBtn = view.findViewById(R.id.FeedFilterEtudiant);

        //display items
        displayItems(view, this.getContext());

        //start search view
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearchView(newText);
                return true;
            }
        });

        //setup filter button
        allFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Popup custompopup = new Popup(getActivity());

                custompopup.getCheck1().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            CheckState(custompopup);
                    }
                });

                custompopup.getCheck2().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckState(custompopup);
                    }
                });
                custompopup.build();
            }
        });

        return view;
    }

    private void CheckState(Popup custompopup){
        if(custompopup.getCheck1().isChecked() && custompopup.getCheck2().isChecked()){
            filterOption("Alternance", "Stage");
        }
        else if(!custompopup.getCheck1().isChecked() && custompopup.getCheck2().isChecked()){
            filterOption("Stage", "");
        }
        else if(custompopup.getCheck1().isChecked() && !custompopup.getCheck2().isChecked()){
            filterOption("Alternance", "");
        }
        else if(custompopup.getCheck1().isChecked()){
            filterOption("Alternance", "");
        }
        else if(custompopup.getCheck2().isChecked()){
            filterOption("Stage", "");
        }
        else{
            filterSearchView("");
        }
    }

    private void filterSearchView(String newText) {
        List<Post> filteredPostList = new ArrayList<>();
        for (Post item : postList) {
            if (item.getJob_name().toLowerCase().contains(newText.toLowerCase())){
                filteredPostList.add(item);
            }
        }
        postAdapter.filterList(filteredPostList);
    }

    private void filterOption(String option, String option2){
        String type_alternance = "Alternance";
        String type_stage = "Stage";
        String type;

        if(option.equals(type_alternance) && option2.equals(type_stage)){
            filterSearchView("");
            return;
        }
        else if(option.equals(type_alternance)) {
            type = type_alternance;
        }
        else if(option.equals(type_stage)) {
            type = type_stage;
        }
        else{
            return;
        }

        List<Post> filteredPostList = new ArrayList<>();
        for(Post item : postList){
            if(item.getContract_type().equals(type)){
                filteredPostList.add(item);
            }
        }
        postAdapter.filterList(filteredPostList);

    }

    private void displayItems(View view, Context context) {
        recyclerView = view.findViewById(R.id.RecyclerViewEtudiant);
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

    public void displayFinish(Context context){
        postAdapter = new PostAdapter(context, postList, this, "etudiant");
        recyclerView.setAdapter(postAdapter);
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

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Post>> call = retrofitAPI.getAllPosts();

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