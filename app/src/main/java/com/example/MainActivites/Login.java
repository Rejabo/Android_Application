package com.example.MainActivites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.MainActivites.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;
import static maes.tech.intentanim.CustomIntent.customType;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String user, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkConnection();
        //onPause();
        onResume();


        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        binding.register.setOnClickListener(v ->
                startActivity(new Intent(Login.this, Register.class)));
        customType(Login.this,"bottom-to-up");

        binding.loginButton.setOnClickListener(v -> {
            user = binding.username.getText().toString();
            pass = binding.password.getText().toString();

            if (user.equals("")) {
                binding.username.setError("can't be blank");
            } else if (pass.equals("")) {
                binding.password.setError("can't be blank");
            } else {
                String url = "https://androidchattapp-8f815-default-rtdb.firebaseio.com/users.json";
                final ProgressDialog pd = new ProgressDialog(Login.this);
                pd.setMessage("Loading...");
                pd.show();

                StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                    if (response.equals("null")) {
                        Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.has(user)) {
                                Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                                resetDataAndFocus();
                            } else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                                UserDetails.username = user;
                                UserDetails.password = pass;
                                startActivity(new Intent(Login.this, UserList.class));

                            } else {
                                Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }, volleyError -> {
                    System.out.println("" + volleyError);
                    pd.dismiss();
                });

                RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                rQueue.add(request);
            }

        });
        checkConnection();
    }


    chargeBroadcastReceiver chargebroadcastreceiver;


    protected void onPause() {
    super.onPause();
    unregisterReceiver(chargebroadcastreceiver);
    }


    protected void onResume() {
        super.onResume();

        chargebroadcastreceiver = new chargeBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(chargebroadcastreceiver, intentFilter);
    }

    public void checkConnection() {

        ConnectivityManager manger = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manger.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_LONG).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                Toast.makeText(this, "Data Network Enabled", Toast.LENGTH_LONG).show();
            }
        }
        else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }

    }



    /*** Reset Data and Focus **/
    private void resetDataAndFocus() {
        binding.username.setText("");
        binding.password.setText("");
        binding.password.clearFocus();
        binding.password.clearFocus();
    }

    @Override
    public void onBackPressed() {
        return;
        }
}
