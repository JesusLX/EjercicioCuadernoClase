package com.limox.jesus.ejerciciocuadernoclase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;
import com.limox.jesus.ejerciciocuadernoclase.Utils.RestClient;
import com.limox.jesus.ejerciciocuadernoclase.Utils.Result;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class Update_Activity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_API = "site";
    public static final int OK = 1;
    @BindView(R.id.idSite)
    TextView idSite;
    @BindView(R.id.nameStudent)
    EditText nameSurname;
    @BindView(R.id.surnameStudent) EditText surnameStudent;
    @BindView(R.id.emailStudent) EditText emailSite;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.cancel) Button cancel;
    //EditText nameSurname, surnameStudent, emailSite;
    //TextView idSite;
    //Button accept, cancel;
    Student s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        //idSite = (TextView) findViewById(R.id.idSite);
        //nameSurname = (EditText) findViewById(R.id.nameSurname);
        //surnameStudent = (EditText) findViewById(R.id.surnameStudent);
        //emailSite = (EditText) findViewById(R.id.emailSite);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Intent i = getIntent();
        s = (Student) i.getSerializableExtra("student");
        idSite.setText(String.valueOf(s.getId()));
        nameSurname.setText(s.getName());
        surnameStudent.setText(s.getSurname());
        emailSite.setText(s.getEmail());
    }
    @Override
    public void onClick(View v) {
        String n, l, e;
        if (v == accept) {
            n = nameSurname.getText().toString();
            l = surnameStudent.getText().toString();
            e = emailSite.getText().toString();
            if (n.isEmpty() || l.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else {
                //s = new Site(id, n, l , e);
                s.setName(n);
                s.setSurname(l);
                s.setEmail(e);
                connection(s);
            }
        }
        if (v == cancel)
            finish();
    }
    private void connection(final Student s) {
        final ProgressDialog progreso = new ProgressDialog(this);
        Gson gson = new Gson();
        RequestParams params = new RequestParams();
        params.put("site", gson.toJson(s));
        //params.put("name", s.getName());
        //params.put("link", s.getLink());
        //params.put("email", s.getEmail());
        RestClient.put(URL_API + "/" + s.getId(), params, new JsonHttpResponseHandler() {
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
                        message = "Modified site ok";
                        //site = gson.fromJson(String.valueOf(result.getSites()), Site.class);
                        Intent i = new Intent();
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("student",s);
                        i.putExtras(mBundle);
                        setResult(OK, i);
                        finish();
                    } else
                        message = "Error modifying the site:\n" + result.getMessage();
                else
                    message = "Null data";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            // onFailure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progreso.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progreso.dismiss();
            }
        });
    }
}