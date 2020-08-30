package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button showButton, RegButton;
    private EditText username, password, email;
    RequestQueue requestQueue;
    private ProgressBar loading;
    private static String insertUrl = "http://52.156.45.138/~db2019/i18102/test.php";
    private static String showUrl = "http://52.156.45.138/~db2019/i18102/Show.php";
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.loading);
        username =  findViewById(R.id.UserText);
        password =  findViewById(R.id.passText);
        email =  findViewById(R.id.emailText);
        RegButton =  findViewById(R.id.RegisButton);
        showButton =  findViewById(R.id.showButton);
        result =  findViewById(R.id.Text);

        //requestQueue = Volley.newRequestQueue(getApplicationContext());

        //登録したデータをアプリに表示するは以下のコードである

        showButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println("ww");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            JSONArray students = response.getJSONArray("students");
                            for (int i = 0; i < students.length(); i++) {
                                JSONObject student = students.getJSONObject(i);

                                String username = student.getString("username");
                                String password = student.getString("password");
                                String email = student.getString("email");

                                result.append(username + " " + password + " " + email + " \n");
                            }
                            result.append("===\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue.add(jsonObjectRequest);

            }
        });


        //MYSQL にデータを登録するコードは以下のように

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration();
            }

        });


    }

    private void Registration() {
        loading.setVisibility(View.VISIBLE);
        RegButton.setVisibility(View.GONE);

        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(MainActivity.this,"Register Success!", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Register Error!" + e.toString(),  Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    RegButton.setVisibility(View.VISIBLE);


                }

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Register Error!" + error.toString(),  Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                RegButton.setVisibility(View.VISIBLE);

            }
        })

        {
            @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();
                        parameters.put("username",username);
                        parameters.put("password",password);
                        parameters.put("email",email);

                        return parameters;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }