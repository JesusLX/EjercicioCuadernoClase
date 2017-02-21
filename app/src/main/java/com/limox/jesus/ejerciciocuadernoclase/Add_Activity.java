package com.limox.jesus.ejerciciocuadernoclase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;
import com.limox.jesus.ejerciciocuadernoclase.Utils.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class Add_Activity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL = "site";
    public static final int OK = 1;
    @BindView(R.id.nameStudent) EditText nameStudent;
    @BindView(R.id.surnameStudent) EditText surnameStudent;
    @BindView(R.id.addressStudent) EditText addressStudent;
    @BindView(R.id.cityStudent) EditText cityStudent;
    @BindView(R.id.postalCodeStudent) EditText postalCodeStudent;
    @BindView(R.id.phoneStudent) EditText phoneStudent;
    @BindView(R.id.emailStudent) EditText emailSite;
    @BindView(R.id.accept) Button accept;
    @BindView(R.id.cancel) Button cancel;
    //EditText nameSurname, surnameStudent, emailSite;
    //Button accept, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        //nameSurname = (EditText) findViewById(R.id.nameSurname);
        //surnameStudent = (EditText) findViewById(R.id.surnameStudent);
        //emailSite = (EditText) findViewById(R.id.emailSite);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String n, l, a, c,pc,p,e;
        Student s;
        if (v == accept) {
            n = nameStudent.getText().toString();
            l = surnameStudent.getText().toString();
            a = addressStudent.getText().toString();
            c = cityStudent.getText().toString();
            pc = postalCodeStudent.getText().toString();
            p = phoneStudent.getText().toString();
            e = emailSite.getText().toString();
            if (n.isEmpty() || l.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else {
                s = new Student(n, l, a, c,pc,p , e);
                connection(s);
            }
        }
        if (v == cancel)
            finish();
    }
    private void connection(final Student s) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RequestParams params = new RequestParams();
        //params.put("site", gson.toJson(s));
        params.put("name", s.getName());
        params.put("link", s.getLink());
        params.put("email", s.getEmail());
        RestClient.post(URL, params, new JsonHttpResponseHandler() {
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
                //Site site;
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null)
                    if (result.getCode()) {
                        message = "Added site ok";
                        //site = gson.fromJson(String.valueOf(result.getSites()), Site.class);
                        Intent i = new Intent();
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("id", result.getLast());
                        mBundle.putString("name", s.getName());
                        mBundle.putString("link", s.getLink());
                        mBundle.putString("email", s.getEmail());
                        i.putExtras(mBundle);
                        setResult(OK, i);
                        finish();
                    } else
                        message = "Error adding the site:\n" + result.getMessage();
                else
                    message = "Null data";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
            //onFailure
        });
    }
}