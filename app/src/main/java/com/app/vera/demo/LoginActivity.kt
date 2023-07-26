package com.app.vera.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.vera.R;
import com.resonai.VeraConfiguration;
import com.resonai.common.helpers.Languages;


public class LoginActivity extends AppCompatActivity {


    private static final int MY_REQUEST_CODE = 100;
    String[] permissions =new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnStart).setOnClickListener(view -> checkPermissions());
    }


    private void checkPermissions(){
        boolean allPermissions=true;
        for (String per :permissions) {
            allPermissions = allPermissions && (checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED);
        }
        if (!allPermissions) {
            requestPermissions(permissions, MY_REQUEST_CODE);
        }else {
            openVeraScreen();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openVeraScreen();
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openVeraScreen() {

        VeraConfiguration.Builder()
                .setClientAppID("vera_client_app")
                .setLanguage(Languages.EN)
                    .startWithoutLogin(this);


    }

}