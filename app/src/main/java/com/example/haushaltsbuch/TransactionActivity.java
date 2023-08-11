package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionActivity extends AppCompatActivity {

    EditText resonTxtTran, amountTxtTran;
    DatePicker dateDatTran;
    Switch incomeBolTran;
    Button submitBtnTran, TableBtnTran, dAuftragBtnTran;
    ImageButton backBtn;
    static DBHelper DB;
    public static TableLayout pTableTblTran;
    TableLayout.LayoutParams lp;
    public static int incomInt;
    public static double newBalance;
    public static String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        backBtn = findViewById(R.id.backBtn1);
        dAuftragBtnTran = findViewById(R.id.dAuftragBtnTran);
        dateDatTran = findViewById(R.id.dateDatTran);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dAuftragBtnTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this,BankersOrderActivity.class));
                MainActivity.SelectFromBankOrders();
            }
        });

        pTableTblTran = (TableLayout) findViewById(R.id.TableTblTran);
        if (pTableTblTran.getChildCount() == 0){
            edditTable.InitializTable(MainActivity.pi, pTableTblTran, this);
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

                resonTxtTran.getText().clear();
                amountTxtTran.getText().clear();

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
                        edditTable.addToTable(pTableTblTran, MainActivity.pi + 1, year + "-" + month + "-" + day, amountDbl, resonTxt, incomInt, TransactionActivity.this);
                        Toast.makeText(TransactionActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                        calculateBalence();
                    } else {
                        Toast.makeText(TransactionActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public static void calculateBalence(){
        MainActivity.SelectFromBankBalence();
        if (MainActivity.piBalance - 1 > -1) {
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1) {
                newBalance = (MainActivity.pBalance[MainActivity.piBalance - 1] + MainActivity.pBetrag[MainActivity.pi - 1]);
            } else {
                newBalance = (MainActivity.pBalance[MainActivity.piBalance - 1] - MainActivity.pBetrag[MainActivity.pi - 1]);
            }
        } else {
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1) {
                newBalance = (MainActivity.pBetrag[MainActivity.pi - 1]);
            } else {
                newBalance = - (MainActivity.pBetrag[MainActivity.pi - 1]);
            }
        }

        DB.insertBankBelance(today, newBalance);
        System.out.println("--- [Incerted Bankbalance] Date: " + today + " Amount: " + newBalance + "€");
        MainActivity.bankBalance.setText(newBalance + "€");
    }
}