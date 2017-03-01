package com.limox.jesus.ejerciciocuadernoclase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.limox.jesus.ejerciciocuadernoclase.Adapters.IncidencesRecyclerAdapter;
import com.limox.jesus.ejerciciocuadernoclase.Adapters.StudentsRecyclerAdapter;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Incidence;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;
import com.limox.jesus.ejerciciocuadernoclase.Utils.RestClient;
import com.limox.jesus.ejerciciocuadernoclase.Utils.Result;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class IncidencesList_Activity extends AppCompatActivity {

    public static final String URL_API = "incidence";
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvIncidences)
    RecyclerView mRvIncidences;

    @BindView(R.id.fabAdd)
    FloatingActionButton mFabAdd;

    IncidencesRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidences_list);
        ButterKnife.bind(this);
        mToolbar.setTitle("Incidencias "+format.format(new Date()));
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mAdapter = new IncidencesRecyclerAdapter(IncidencesList_Activity.this);
        mRvIncidences.setLayoutManager(new LinearLayoutManager(this));
        mRvIncidences.setAdapter(mAdapter);
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IncidencesList_Activity.this,AddIncidence_Activity.class);
                i.putExtras(getIntent().getExtras());
                startActivityForResult(i,1);
            }
        });
        downloadIncidences();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Incidence incidence;
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                incidence = (Incidence) data.getSerializableExtra("incidence");
                //add the student to the adapter
                mAdapter.add(incidence);
            }
    }

    private void downloadIncidences() {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(URL_API, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Connecting . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Handle resulting parsed JSON response here
                progreso.dismiss();
                Result result;
                Gson gson = new Gson();
                String message;
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null)
                    if (result.getCode()) {
                        //put the sites in the adapter
                        message = "Connection OK";
                        mAdapter.set(result.getIncidences());
                    } else
                        message = result.getMessage();
                else
                    message = "Null data";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
