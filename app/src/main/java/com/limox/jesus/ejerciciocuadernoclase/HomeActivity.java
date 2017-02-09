package com.limox.jesus.ejerciciocuadernoclase;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity implements StudentsList_Fragment.OnStudentListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState != null)
            startStudentsListFragment();

    }

    void startStudentsListFragment(){
        startFragment(new StudentsList_Fragment());
    }

    void startFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home,fragment).commit();
    }

    @Override
    public void startStudentManager(Bundle student) {

    }
}
