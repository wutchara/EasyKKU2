package kku.wutchara.rachadacr.easykku2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private Button signUpButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Sign Up Controller
        signUpController();

    }// Main Method

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
    }
}// Main Class
