package kku.wutchara.rachadacr.easykku2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

    private String nameString, phonString, userString, passString, imagrPathString, imageNameString;

    private Uri uri;
    private Boolean checkSelectImage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();

        //Controller Sign Up
        signUpController();

        //Controller Image Button
        imageController();

    }//main method

    private void imageController() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("12novV1", "Open Gallary.");

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Select App for View picture"), 0);
            }
        });
    }

    private void signUpController() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();

                if(checkSpace()){
                    //Log.d("12novV1", "name: " + nameString);
                    Log.d("12novV1", "Have Space.");

                    MyAlert myDialog = new MyAlert();
                    myDialog.myDialog(SignUpActivity.this, R.drawable.danger,
                            "Warning" + "....", "On your input have space.\nPlease fill its.");
                } else if (checkSelectImage){
                    MyAlert myDialog = new MyAlert();
                    myDialog.myDialog(SignUpActivity.this, R.drawable.danger,
                            "Warning" + "....", "Please Select Image.");
                } else {
                    Log.d("12novV1", "Not Have Space.");
                    //Toast.makeText(getApplicationContext(), "Not Have Space", Toast.LENGTH_SHORT).show();
                    uploadImageToServer();
                }
            }
        });
    }

    private void uploadImageToServer() {

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
        imageView = (ImageView) findViewById(R.id.imageView2);
        button = (Button) findViewById(R.id.button2);
        nameEditText = (EditText) findViewById(R.id.editTextName);
        phoneEditText = (EditText) findViewById(R.id.editTextPhone);
        userEditText = (EditText) findViewById(R.id.editTextUser);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)){

            checkSelectImage = false;

            Log.d("12novV1", "Result OK!!!");

            //Show Image
            uri = data.getData();
            //Log.d("12novV1", uri.toString() + "");

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
                //Log.d("12novV1", "Data : \"" + uri.toString() + "\"");

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Find Path of photo
            imagrPathString = myFinPath(uri);
            Log.d("12novV1", "PATH : \"" + imagrPathString + "\"");

            //Find name of photo
            imageNameString = imagrPathString.substring(imagrPathString.lastIndexOf("/") + 1); // + 1 because no save "
            Log.d("12novV1", "Image Name : \"" + imageNameString + "\"");

        } else {
            Log.d("12novV1", "Result Not OK!!!");
            checkSelectImage = true;
        }
    }

    private String myFinPath(Uri uri) {

        String result = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(index);

        } else {
            //if in phone have one picture
            result = uri.getPath();
        }

        return result;
    }
}//main class
