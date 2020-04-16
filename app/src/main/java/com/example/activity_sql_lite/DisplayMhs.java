package com.example.activity_sql_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMhs extends AppCompatActivity {

    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    EditText nomhs ;
    EditText phone;
    EditText nama;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_mhs);
        nomhs = (EditText) findViewById(R.id.editTextNim);
        nama = (EditText) findViewById(R.id.editTextName);
        phone = (EditText) findViewById(R.id.editTextPhone);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String no = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_NIM));
                String nam = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_NAMA));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_PHONE));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                nomhs.setText((CharSequence) no);
                nomhs.setFocusable(false);
                nomhs.setClickable(false);

                nama.setText((CharSequence) nam);
                nama.setFocusable(false);
                nama.setClickable(false);

                phone.setText((CharSequence) phon);
                phone.setFocusable(false);
                phone.setClickable(false);

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.menu_display,menu);
            } else{
                getMenuInflater().inflate(R.menu.menu_main,menu);
            }
        }
        return true;
    }

    public void run (View view){
        if (nomhs.getText().toString().equals("")||
            nama.getText().toString().equals("")||
            phone.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),
                    "Data Harus Diisi Semua !.",Toast.LENGTH_SHORT).show();
        } else {
            mydb.insertContact(nomhs.getText().toString(),
                    nama.getText().toString(),
                    phone.getText().toString());
            Toast.makeText(getApplicationContext(),
                    "Insert Data Sukses.",Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
