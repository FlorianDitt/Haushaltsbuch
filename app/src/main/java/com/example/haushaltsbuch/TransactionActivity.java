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
                        Toast.makeText(TransactionActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                        MainActivity.SelectFromTransaktion();
                        addNewToTable();
                        calculateBalence();
                    } else {
                        Toast.makeText(TransactionActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public void addNewToTable(){
        if (MainActivity.pi -1 > -1) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pID[MainActivity.pi - 1]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(MainActivity.pDatum[MainActivity.pi - 1]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(this);
            Tv3.setText(Double.toString(MainActivity.pBetrag[MainActivity.pi - 1]) + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(this);
            Tv5.setText(MainActivity.pGrund[MainActivity.pi - 1]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Incerted Transaktion] ID: " + Tv1.getText() + " Date: " + Tv2.getText() + " Amount: " + Tv3.getText() + " Reson: " + Tv5.getText());

            pTableTblTran.addView(TblRow);
        }else{
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pID[0]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(MainActivity.pDatum[0]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(this);
            Tv3.setText(MainActivity.pBetrag[0] + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[0] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(this);
            Tv5.setText(MainActivity.pGrund[0]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Incerted Transaktion] ID: " + Tv1.getText() + " Date: " + Tv2.getText() + " Amount: " +Tv3.getText() + " Reson: " + Tv5.getText());

            pTableTblTran.addView(TblRow);
        }
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