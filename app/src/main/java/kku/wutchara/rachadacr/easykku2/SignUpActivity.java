package kku.wutchara.rachadacr.easykku2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private ImageView imageView;
    private Button button;
    private EditText nameEditText, phoneEditText, userEditText, passwordEditText;

    private String nameString, phonString, userString, passString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();

        //Controller
        signUpController();
    }//main method

    private void signUpController() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();

                if(checkSpace()){
                    //Log.d("12novV1", "name: " + nameString);
                    Log.d("12novV1", "Have Space");

                    //MyAlert myDialog = new MyAlert();
                    //myDialog.myDialog(MainActivity.class.this, "Warning....", "On your input have space.\nPlease fill its.");
                } else {
                    Toast.makeText(getApplicationContext(), "Not Have Space", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData() {
        nameString = nameEditText.getText().toString().trim();
        phonString = phoneEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passString = passwordEditText.getText().toString().trim();
    }

    private boolean checkSpace() {
        return nameString.equals("") ||
                phonString.equals("") ||
                userString.equals("") ||
                passString.equals("");
    }

    private void bindWidget() {
        button = (Button) findViewById(R.id.button2);
        nameEditText = (EditText) findViewById(R.id.editTextName);
        phoneEditText = (EditText) findViewById(R.id.editTextPhone);
        userEditText = (EditText) findViewById(R.id.editTextUser);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
    }

}//main class
