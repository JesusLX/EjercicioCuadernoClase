package com.limox.jesus.ejerciciocuadernoclase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Incidence;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;
import com.limox.jesus.ejerciciocuadernoclase.Utils.RestClient;
import com.limox.jesus.ejerciciocuadernoclase.Utils.Result;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class AddIncidence_Activity extends AppCompatActivity implements View.OnClickListener {

    public static final String URL = "incidence";
    ArrayList<Student> students;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String[] faltasVal = new String[]{"No", "Injustificada", "Justificada" ,"Retraso"};
    String[] actitudVal =new String[]{"Bueno", "Regular" , "Malo"};
    String[] trabajoVal =new String[]{"Postitva","Negativa"};
    String[] studentsVal;
    @BindView(R.id.spnStudents)
    Spinner spnStudents;
    @BindView(R.id.spnFalta)
    Spinner spnFalta;
    @BindView(R.id.spnTrabajo)
    Spinner spnTrabajo;
    @BindView(R.id.spnActitud)
    Spinner spnActitud;
    @BindView(R.id.edtObservacioes)
    EditText edtObservaciones;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.accept)
    Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incidence);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapterFaltas = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item,faltasVal);
        spnFalta.setAdapter(adapterFaltas);
        ArrayAdapter<CharSequence> adapterActitud = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item,actitudVal);
        spnActitud.setAdapter(adapterActitud);
        ArrayAdapter<CharSequence> adapterTrabajo = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item,trabajoVal);
        spnTrabajo.setAdapter(adapterTrabajo);

        students =getIntent().getParcelableArrayListExtra("students");
        studentsVal = new String[students.size()];
        for (int i = 0; i < studentsVal.length; i++) {
            studentsVal[i] = students.get(i).toString();
        }
        ArrayAdapter<CharSequence> adapterStudents = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item,studentsVal);
        spnStudents.setAdapter(adapterStudents);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String actitud, trabajo, falta, observaciones;
        Student s;
        Incidence i;
        if (v == accept) {
            actitud =  spnActitud.getSelectedItem().toString();
            trabajo = spnTrabajo.getSelectedItem().toString();
            falta = spnFalta.getSelectedItem().toString();
            s = students.get(spnStudents.getSelectedItemPosition());
            observaciones = edtObservaciones.getText().toString();

            if (actitud.isEmpty() || trabajo.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else {
                Date d = new Date();

                i = new Incidence(falta, trabajo, actitud, observaciones,format.format(d),s.getId());
                i.setName(spnStudents.getSelectedItem().toString());
                connection(i);
            }
        }
        if (v == cancel)
            finish();
    }
    private void connection(final Incidence incidence) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RequestParams params = new RequestParams();
        //params.put("site", gson.toJson(s));
        params.put("id_student", incidence.getId_user());
        params.put("faltas", incidence.getFaltas());
        params.put("trabajo", incidence.getTrabajo());
        params.put("actitud", incidence.getActitud());
        params.put("observaciones", incidence.getObservaciones());
        params.put("date", incidence.getDate());
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
                        mBundle.putSerializable("incidence",incidence);
                        i.putExtras(mBundle);
                        setResult(RESULT_OK, i);
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
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //onFailure
        });
    }
}
