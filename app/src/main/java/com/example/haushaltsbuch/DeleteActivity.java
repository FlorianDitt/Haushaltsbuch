package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText IDTxtDlt;
    Spinner TableSpnDlt;
    Button submitBtnDlt;
    DBHelper DB;
    ImageButton backBtn3;
    public static String TableToDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        backBtn3 = findViewById(R.id.backBtn3);

        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        submitBtnDlt = findViewById(R.id.submitBtnDlt);
        TableSpnDlt = findViewById(R.id.TableSpnDlt);
        IDTxtDlt = findViewById(R.id.IDTxtDlt);
        
        DB = new DBHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DBTables, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TableSpnDlt.setAdapter(adapter);
        TableSpnDlt.setOnItemSelectedListener(this);

        submitBtnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IdTxt = IDTxtDlt.getText().toString();
                if (IdTxt.equals("")) {
                    Toast.makeText(DeleteActivity.this, "No Data to Delete", Toast.LENGTH_SHORT).show();
                } else {
                    IDTxtDlt.setText("");
                    switch (TableToDel) {
                        case DBHelper.TABLE1_NAME: {

                            int IdInt = Integer.parseInt(IdTxt);
                            Boolean checkDeletedata = DB.deleteTransaktion(IdInt, DBHelper.TABLE1_NAME);
                            if (checkDeletedata) {
                                Toast.makeText(DeleteActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                                System.out.println("--- [Deletend] In Table: " + DBHelper.TABLE1_NAME + " Row of ID: " + IdInt);
                            } else {
                                Toast.makeText(DeleteActivity.this, "Entry not Deleted", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                        case DBHelper.TABLE2_NAME: {

                            int IdInt = Integer.parseInt(IdTxt);
                            Boolean checkDeletedata = DB.deleteTransaktion(IdInt, DBHelper.TABLE2_NAME);
                            if (checkDeletedata) {
                                Toast.makeText(DeleteActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                                System.out.println("--- [Deletend] In Table: " + DBHelper.TABLE2_NAME + " Row of ID: " + IdInt);
                            } else {
                                Toast.makeText(DeleteActivity.this, "Entry not Deleted", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                        case DBHelper.TABLE4_NAME: {

                            int IdInt = Integer.parseInt(IdTxt);
                            Boolean checkDeletedata = DB.deleteTransaktion(IdInt, DBHelper.TABLE4_NAME);
                            if (checkDeletedata) {
                                Toast.makeText(DeleteActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                                System.out.println("--- [Deletend] In Table: " + DBHelper.TABLE4_NAME + " Row of ID: " + IdInt);
                            } else {
                                Toast.makeText(DeleteActivity.this, "Entry not Deleted", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TableToDel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}