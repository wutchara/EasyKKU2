package kku.wutchara.rachadacr.easykku2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private Button signUpButton, signInButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    private MyConstant myConstant;

    private String[] nameString, phoneString, imageString;
    private String truePassword;
    private Boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myConstant = new MyConstant();

        //Bind Widget
        bindWidget();

        //Sign Up Controller
        signUpController();

        //sign In Controller
        signINController();

    }// Main Method

    private class SynUser extends AsyncTask<String, Void, String> {

        private Context context;
        public SynUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("13novV2", "e doIn : " + e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("13novV2", s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                nameString = new String[jsonArray.length()];
                phoneString = new String[jsonArray.length()];
                imageString = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    nameString[i] = jsonObject.getString("Name");
                    phoneString[i] = jsonObject.getString("Phone");
                    imageString[i] = jsonObject.getString("Image");

                    Log.d("13novV3", i + ". Name : " + nameString[i]);

                    //Check User
                    if (userString.equals(jsonObject.getString("User"))) {
                        aBoolean = false;
                        truePassword = jsonObject.getString("Password");
                    }
                }//for

                if (aBoolean) {
                    //User Fail
                    MyAlert  myAlert = new MyAlert();
                    myAlert.myDialog(MainActivity.this, R.drawable.danger,
                            getResources().getString(R.string.title_user_fail),
                            getResources().getString(R.string.measage_user_fail));

                } else if(passwordString.equals(truePassword)) {
                    //Password True
                    Toast.makeText(MainActivity.this, "Login Success!!", Toast.LENGTH_SHORT).show();
                } else {
                    //Password fail
                    MyAlert  myAlert = new MyAlert();
                    myAlert.myDialog(MainActivity.this, R.drawable.danger,
                            getResources().getString(R.string.title_passwod_fail),
                            getResources().getString(R.string.message_password_fail));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void signINController() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get values
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                Log.d("13novV2", "Want to Login\nu : \"" + userString + "\"\nP : \"" + passwordString + "\"");

                //check space
                if (userString.equals("") || passwordString.equals("")) {
                    //Toast.makeText(MainActivity.this, "Have space", Toast.LENGTH_SHORT).show();
                    Log.d("13novV2", "Have space");
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(MainActivity.this, R.drawable.doremon48, getResources().getString(R.string.title_have_space), getResources().getString(R.string.messageSpace));
                } else {
                    //No Space
                    SynUser synUser = new SynUser(MainActivity.this);
                    synUser.execute(myConstant.getUrlGetJSON());
                }
            }
        });
    }


    private void signUpController() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }

    private void bindWidget() {
        signInButton = (Button) findViewById(R.id.signInBtn);
        signUpButton = (Button) findViewById(R.id.signUpBtn);

        userEditText = (EditText) findViewById(R.id.editTextUser);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
    }
}// Main Class
