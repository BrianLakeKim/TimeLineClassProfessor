package com.example.timelineclassprofessor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class lecture_list extends Fragment {

    private View view;
    LinearLayout lectureButton1;
    LinearLayout lectureButton2;
    LinearLayout lectureButton3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_lecture_list, container, false);

        lectureButton1 = (LinearLayout) rootView.findViewById(R.id.lectureButton1);
        lectureButton2 = (LinearLayout) rootView.findViewById(R.id.lectureButton2);
        lectureButton3 = (LinearLayout) rootView.findViewById(R.id.lectureButton3);

        lectureButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PlayerActivity.class);
                startActivity(intent);
            }
        });

        lectureButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PlayerActivity.class);
                startActivity(intent);
            }
        });

        lectureButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PlayerActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}