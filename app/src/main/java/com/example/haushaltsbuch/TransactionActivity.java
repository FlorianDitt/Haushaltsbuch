package com.example.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class TransactionActivity extends AppCompatActivity {
    Intent intent;

    String Store = "";
    String Sum = "";
    EditText resonTxtTran, amountTxtTran;
    DatePicker dateDatTran;
    Switch incomeBolTran;
    Button submitBtnTran, TableBtnTran, scanBtnTran;
    ImageButton backBtn;
    static DBHelper DB;
    public static TableLayout pTableTblTran;
    TableLayout.LayoutParams lp;
    public static int incomInt;
    Activity a = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Initialize();

        intent = getIntent();
        if (!Objects.equals(intent.getStringExtra("Store"), "")){
            Store = intent.getStringExtra("Store");
        }
        if (!Objects.equals(intent.getStringExtra("Sum"), "")){
            Sum = intent.getStringExtra("Sum");
        }
        checkIntent();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        scanBtnTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransactionActivity.this,ScanBillActivity.class));
            }
        });

        if (pTableTblTran.getChildCount() == 0){
            edditTable.InitializTable(MainActivity.pi, lp, pTableTblTran, this);
        }
        TableBtnTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,TransaktionTableActivity.class));
            }
        });
        submitBtnTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resonTxt = resonTxtTran.getText().toString();
                String amountTxt = amountTxtTran.getText().toString();

                int dayInt = dateDatTran.getDayOfMonth();
                String day;
                if (String.valueOf(dayInt).length() == 1){
                    day = "0" + dayInt;
                }else{
                    day = Integer.toString(dayInt);
                }
                int monthInt = dateDatTran.getMonth() + 1;
                String month;
                if (String.valueOf(monthInt).length() == 1){
                    month = "0" + monthInt;
                }else{
                    month = Integer.toString(monthInt);
                }
                int year = dateDatTran.getYear();
                if (resonTxt.equals("") || amountTxt.equals("")) {
                    Toast.makeText(TransactionActivity.this, "Bitte gib einen Betrag und einen Grund an", Toast.LENGTH_SHORT).show();
                } else {
                    double amountDbl = Double.parseDouble(amountTxt);
                    if (incomeBolTran.isChecked()){
                        incomInt = 1;
                    } else {
                        incomInt = 0;
                    }
                    boolean checkinsertdata = DB.insertTransaktion(year + "-" + month + "-" + day, amountDbl, incomInt, resonTxt);
                    if (checkinsertdata) {
                        MainActivity.SelectFromTransaktion();
                        edditTable.addToTable(MainActivity.pi, lp, pTableTblTran, a);
                        MainActivity.calculateBalence();
                    } else {
                        Toast.makeText(TransactionActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                    }
                    resonTxtTran.getText().clear();
                    amountTxtTran.getText().clear();
                }
            }
        });
    }
    private void Initialize(){
        DB = new DBHelper(this);
        backBtn = findViewById(R.id.backBtn1);
        dateDatTran = findViewById(R.id.dateDatTran);
        scanBtnTran = findViewById(R.id.scanBtnTran);
        resonTxtTran = findViewById(R.id.resonTxtTran);
        incomeBolTran = findViewById(R.id.incomeBolTran);
        amountTxtTran = findViewById(R.id.amountTxtTran);
        TableBtnTran = findViewById(R.id.TableBtnTran);
        submitBtnTran = findViewById(R.id.submitBtnTran);
        pTableTblTran = (TableLayout) findViewById(R.id.TableTblTran);

        lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);
    }
    private void checkIntent(){
        if ((Sum != null && Store != null)&&(!Sum.equals("")||!Store.equals(""))){
            resonTxtTran.setText("Einkauf bei: " + Store);
            amountTxtTran.setText(Sum.replaceAll("[^0-9,.]","").replaceAll(",","."));
        }
    }
}