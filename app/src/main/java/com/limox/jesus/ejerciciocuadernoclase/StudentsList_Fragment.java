package com.limox.jesus.ejerciciocuadernoclase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;


public class StudentsList_Fragment extends Fragment {

    private Toolbar mToolbar;
    private OnStudentListFragmentListener mListener;

    public StudentsList_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_students_list, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.inflateMenu(R.menu.menu_incidences);
        mToolbar.setTitle("Alumnos");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStudentListFragmentListener) {
            mListener = (OnStudentListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStudentListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnStudentListFragmentListener {
        void startStudentManager(Bundle student);
    }
}
