package com.limox.jesus.ejerciciocuadernoclase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    public static final String URL_API = "student";
    public static final int OK = 1;
    @BindView(R.id.idStudent)
    TextView idStudent;
    @BindView(R.id.nameStudent) EditText nameStudent;
    @BindView(R.id.surnameStudent) EditText surnameStudent;
    @BindView(R.id.addressStudent) EditText addressStudent;
    @BindView(R.id.cityStudent) EditText cityStudent;
    @BindView(R.id.postalCodeStudent) EditText postalCodeStudent;
    @BindView(R.id.phoneStudent) EditText phoneStudent;
    @BindView(R.id.emailStudent) EditText emailStudent;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.cancel) Button cancel;
    //EditText nameStudent, surnameStudent, emailStudent;
    //TextView idStudent;
    //Button accept, cancel;
    Student s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        //idStudent = (TextView) findViewById(R.id.idStudent);
        //nameStudent = (EditText) findViewById(R.id.nameStudent);
        //surnameStudent = (EditText) findViewById(R.id.surnameStudent);
        //emailStudent = (EditText) findViewById(R.id.emailStudent);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Intent i = getIntent();
        s = (Student) i.getSerializableExtra("student");
        idStudent.setText(String.valueOf(s.getId()));
        nameStudent.setText(s.getName());
        surnameStudent.setText(s.getSurname());
        addressStudent.setText(s.getAddress());
        cityStudent.setText(s.getCity());
        postalCodeStudent.setText(s.getPostalCode());
        phoneStudent.setText(s.getPhone());
        emailStudent.setText(s.getEmail());
    }
    @Override
    public void onClick(View v) {
        String i,n, l, a, c,pc,p,e;
        Student s = new Student();
        if (v == accept) {
            i = idStudent.getText().toString();
            n = nameStudent.getText().toString();
            l = surnameStudent.getText().toString();
            a = addressStudent.getText().toString();
            c = cityStudent.getText().toString();
            pc = postalCodeStudent.getText().toString();
            p = phoneStudent.getText().toString();
            e = emailStudent.getText().toString();
            if (n.isEmpty() || l.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else {
                //s = new Site(id, n, l , e);
                s.setId(Integer.parseInt(i));
                s.setName(n);
                s.setSurname(l);
                s.setAddress(a);
                s.setCity(c);
                s.setPostalCode(pc);
                s.setPhone(p);
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
        params.put("student", gson.toJson(s));
        Log.d("student",gson.toJson(s));
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
                        mBundle.putInt("id",(int)s.getId());
                        mBundle.putString("name",s.getName());
                        mBundle.putString("surname",s.getSurname());
                        mBundle.putString("address",s.getAddress());
                        mBundle.putString("city",s.getCity());
                        mBundle.putString("postalCode",s.getPostalCode());
                        mBundle.putString("phone",s.getPhone());
                        mBundle.putString("email",s.getEmail());
                        //mBundle.putSerializable("student",s);
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
                Toast.makeText(Update_Activity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progreso.dismiss();
                Toast.makeText(Update_Activity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progreso.dismiss();
            }
        });
    }
}