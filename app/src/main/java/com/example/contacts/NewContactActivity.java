package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewContactActivity extends AppCompatActivity {
    private EditText editname, editphn, editemail, place;
   private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact);
      save = findViewById(R.id.btn23);

        editname = findViewById(R.id.createname);
        editphn = findViewById(R.id.phoneno);
        editemail = findViewById(R.id.createmail);
        place = findViewById(R.id.createplace);

        save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String name = editname.getText().toString();
               String phone = editphn.getText().toString();
               String email = editemail.getText().toString();
               String place1 = place.getText().toString();
               if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(email) && TextUtils.isEmpty(place1)) {
                   Toast.makeText(NewContactActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
               } else {

                   addcontact(name, email, phone, place1);
               }

           }
       });

    }

    private void addcontact(String name, String email, String phone, String place1) {
        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        contactIntent
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                .putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                .putExtra(ContactsContract.Intents.Insert.DATA, place1);
       startActivityForResult(contactIntent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK)
            {

                Toast.makeText(this, "Contact has been added.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewContactActivity.this, MainActivity.class);
                startActivity(i);
            }

            if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Cancelled Added Contact",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

