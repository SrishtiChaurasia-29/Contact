package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<contacts> list = new ArrayList<>();
    private RecyclerView rcv;
    private MyAdapter contactMyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.myview);
        FloatingActionButton floatbtn = findViewById(R.id.floatbtn1);
        preparecontact();
        requestContactPermission();

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, NewContactActivity.class);
                startActivity(in);
            }
        });

    }

    private void preparecontact() {
        rcv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(list, this);

        rcv.setAdapter(adapter);
    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            getContactList();
        }

    }

    private void getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "abc";
        Cursor cursor = getContentResolver().query(uri, null, null, null, sort);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String i = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String n = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriphn = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
                Cursor Phonecursor = getContentResolver().query(uriphn, null, selection, new String[]{i}, null);
                if (Phonecursor.moveToNext()) {

                    String number = Phonecursor.getString(Phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts c = new contacts(n, number);
                    c.setName(n);
                    c.setNumber(number);
                    list.add(c);
                    Phonecursor.close();
                }
            }
        }
        cursor.close();

    }




        public void onRequestPermissionResult ( int requestcode, @NonNull String[] permissions,
        int[] grantResults){
            super.onRequestPermissionsResult(requestcode, permissions, grantResults);
            if (requestcode == 100 && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContactList();
            } else {
                Toast.makeText(MainActivity.this, "The permission is denied", Toast.LENGTH_SHORT).show();
                requestContactPermission();
            }


        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.search_bar, menu);
            MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText.toLowerCase());
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);
        }

        private void filter (String text){
            ArrayList<contacts> filterlist = new ArrayList<>();
            for (contacts item : list) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filterlist.add(item);
                }
            }
            if (filterlist.isEmpty()) {
                Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
            } else {
                MyAdapter.filterList(filterlist);


            }
        }

    }