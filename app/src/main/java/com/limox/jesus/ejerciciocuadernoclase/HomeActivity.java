package com.limox.jesus.ejerciciocuadernoclase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limox.jesus.ejerciciocuadernoclase.Adapters.MyRecyclerAdapter;
import com.limox.jesus.ejerciciocuadernoclase.Interfaces.ClickListener;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;
import com.limox.jesus.ejerciciocuadernoclase.Utils.RecyclerTouchListener;
import com.limox.jesus.ejerciciocuadernoclase.Utils.RestClient;
import com.limox.jesus.ejerciciocuadernoclase.Utils.Result;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String URL_API = "student";
    public static final String MAIL = "mail";
    public static final int ADD_CODE = 100;
    public static final int UPDATE_CODE = 200;
    public static final int OK = 1;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton fab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MyRecyclerAdapter mAdapter;
    int positionClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);
        //Initialize RecyclerView
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new MyRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent emailIntent = new Intent(getApplicationContext(), Email_Activity.class);
                emailIntent.putExtra(MAIL, mAdapter.getAt(position).getEmail());
                startActivity(emailIntent);
            }

            @Override
            public void onLongClick(View view, int position) {
                showPopup(view, position);
            }
        }));
        downloadSites();
    }
    private void downloadSites() {
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
                        mAdapter.set(result.getStudents());
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

    @Override
    public void onClick(View view) {
        if (view == fab) {
            Intent i = new Intent(this, Add_Activity.class);
            startActivityForResult(i, ADD_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Student student = new Student();
        if (requestCode == ADD_CODE)
            if (resultCode == OK) {
                student.setId(data.getIntExtra("id", 1));
                student.setName(data.getStringExtra("name"));
                student.setSurname(data.getStringExtra("surname"));
                student.setAddress(data.getStringExtra("address"));
                student.setCity(data.getStringExtra("city"));
                student.setPostalCode(data.getStringExtra("postalCode"));
                student.setPhone(data.getStringExtra("phone"));
                student.setEmail(data.getStringExtra("email"));
                //add the student to the adapter
                mAdapter.add(student);
            }
        if (requestCode == UPDATE_CODE)
            if (resultCode == OK) {
                student.setId(data.getIntExtra("id", 1));
                student.setName(data.getStringExtra("name"));
                student.setSurname(data.getStringExtra("surname"));
                student.setAddress(data.getStringExtra("address"));
                student.setCity(data.getStringExtra("city"));
                student.setPostalCode(data.getStringExtra("postalCode"));
                student.setPhone(data.getStringExtra("phone"));
                student.setEmail(data.getStringExtra("email"));
                // modify the student in the adapter
                mAdapter.modifyAt(student,positionClicked);
            }
    }

    private void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_change, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modify_site:
                        modify(mAdapter.getAt(position));
                        positionClicked = position;
                        return true;
                    case R.id.delete_site:
                        confirm((int) mAdapter.getAt(position).getId(), mAdapter.getAt(position).getName(), position);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Show the menu
        popup.show();
    }
    private void modify(Student s) {
        Intent i = new Intent(this, Update_Activity.class);
        i.putExtra("student", s);
        startActivityForResult(i, UPDATE_CODE);
    }
    private void confirm(final int idSite, String name, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(name + "\nDo you want to delete?")
                .setTitle("Delete")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        connection(idSite, position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    private void connection(int id, final int position) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.delete(URL_API + "/" + id, new JsonHttpResponseHandler() {
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
                        message = "Site deleted OK";
                        // delete the site in the adapter
                        mAdapter.removeAt(position);
                    } else
                        message = "Error deleting the site:\nEstado: " + result.getStatus() + "\n" +
                                result.getMessage();
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
