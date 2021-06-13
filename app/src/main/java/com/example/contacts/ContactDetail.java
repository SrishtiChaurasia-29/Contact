package com.example.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ContactDetail extends AppCompatActivity {
    private String contactName, contactNumber;
    private TextView nameView, numberView;
    private ImageView image2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactdetail);
        contactName = getIntent().getStringExtra("name");
        contactNumber = getIntent().getStringExtra("number");
        nameView = findViewById(R.id.idTVName);
        numberView = findViewById(R.id.calltext);
        image2 = findViewById(R.id.calling);
        nameView.setText(contactName);
        numberView.setText(contactNumber);


        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contactNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }


            }
        });
    }
}


