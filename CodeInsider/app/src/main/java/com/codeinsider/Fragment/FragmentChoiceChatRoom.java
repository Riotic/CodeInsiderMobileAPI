package com.codeinsider.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeinsider.Adapter.EtudiantAdapter;
import com.codeinsider.Model.Etudiant;
import com.codeinsider.R;

import java.util.List;

public class FragmentChoiceChatRoom extends Fragment {

    private RecyclerView recyclerView;
    private List<Etudiant> roomList;
    private EtudiantAdapter roomAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_chat_room, container, false);



        return view;
    }
}