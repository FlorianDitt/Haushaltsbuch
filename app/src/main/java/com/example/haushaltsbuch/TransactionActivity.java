package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionActivity extends AppCompatActivity {

    EditText resonTxtTran, amountTxtTran;
    DatePicker dateDatTran;
    Switch incomeBolTran;
    Button submitBtnTran, TableBtnTran;
    ImageButton backBtn;
    static DBHelper DB;
    public static TableLayout pTableTblTran;
    TableLayout.LayoutParams lp;
    public static int incomInt;
    public static BigDecimal newBalance;
    public static String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Activity a = this;
        backBtn = findViewById(R.id.backBtn1);
        dateDatTran = findViewById(R.id.dateDatTran);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);

        pTableTblTran = (TableLayout) findViewById(R.id.TableTblTran);
        if (pTableTblTran.getChildCount() == 0){
            edditTable.InitializTable(MainActivity.pi, lp, pTableTblTran, this);
        }



        resonTxtTran = findViewById(R.id.resonTxtTran);
        incomeBolTran = findViewById(R.id.incomeBolTran);
        amountTxtTran = findViewById(R.id.amountTxtTran);
        TableBtnTran = findViewById(R.id.TableBtnTran);

        submitBtnTran = findViewById(R.id.submitBtnTran);

        DB = new DBHelper(this);

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
                    Toast.makeText(TransactionActivity.this, "No Data to Insert", Toast.LENGTH_SHORT).show();
                } else {
                    double amountDbl = Double.parseDouble(amountTxt);
                    if (incomeBolTran.isChecked()){
                        incomInt = 1;
                    } else {
                        incomInt = 0;
                    }
                    boolean checkinsertdata = DB.insertTransaktion(year + "-" + month + "-" + day, amountDbl, incomInt, resonTxt);
                    if (checkinsertdata) {
                        Toast.makeText(TransactionActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
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
}