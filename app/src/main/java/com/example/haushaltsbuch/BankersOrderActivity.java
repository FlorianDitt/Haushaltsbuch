package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class BankersOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText amountTxtBko;
    Switch incomeBolBko;
    TableLayout TableTblBko;
    Button submitBtnBko, tableBtnBko;
    ImageButton backBtn6;
    DatePicker DateDatBko;
    Spinner RepSpnBko, WeekDaySpnBko;
    TableLayout.LayoutParams lp;
    public static String RytmLengthTxt;
    public static String WeekDayTxt;
    DBHelper DB;
    public static int incimeInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankers_order);

        backBtn6 = findViewById(R.id.backBtn6);
        RepSpnBko = findViewById(R.id.RepSpnBko);
        tableBtnBko = findViewById(R.id.tableBtnBko);
        WeekDaySpnBko = findViewById(R.id.WeekDaySpnBko);
        incomeBolBko = findViewById(R.id.incomeBolBko);
        amountTxtBko = findViewById(R.id.amountTxtBko);
        submitBtnBko = findViewById(R.id.submitBtnBko);
        TableTblBko = findViewById(R.id.TableTblBko);
        DateDatBko = findViewById(R.id.DateDatBko);
        lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);

        ArrayAdapter<CharSequence> Rytmadapter = ArrayAdapter.createFromResource(this, R.array.RytmLength, R.layout.spinner_item);
        Rytmadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> WeekDayadapter = ArrayAdapter.createFromResource(this, R.array.WeekDay, R.layout.spinner_item);
        WeekDayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        RepSpnBko.setAdapter(Rytmadapter);
        RepSpnBko.setOnItemSelectedListener(this);
        RepSpnBko.setOnItemSelectedListener(new RythmSpinner());
        WeekDaySpnBko.setAdapter(WeekDayadapter);
        WeekDaySpnBko.setOnItemSelectedListener(this);
        WeekDaySpnBko.setOnItemSelectedListener(new WeekDaySpinner());

        DB = new DBHelper(this);

        addToTable();


        backBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tableBtnBko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankersOrderActivity.this,BankOrderTableActivity.class));
            }
        });

        submitBtnBko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountTxt = amountTxtBko.getText().toString();

                amountTxtBko.getText().clear();

                int dayInt = DateDatBko.getDayOfMonth();
                String day;
                if (String.valueOf(dayInt).length() == 1){
                    day = "0" + dayInt;
                }else{
                    day = Integer.toString(dayInt);
                }
                int monthInt = DateDatBko.getMonth() + 1;
                String month;
                if (String.valueOf(monthInt).length() == 1){
                    month = "0" + monthInt;
                }else{
                    month = Integer.toString(monthInt);
                }
                int year = DateDatBko.getYear();

                if (amountTxt.equals("")) {
                    Toast.makeText(BankersOrderActivity.this, "No Data to Insert", Toast.LENGTH_SHORT).show();
                } else {
                    double amountDbl = Double.parseDouble(amountTxt);
                    if (incomeBolBko.isChecked()){
                        incimeInt = 1;
                    } else {
                        incimeInt = 0;
                    }
                    if (RytmLengthTxt.equals("Wöchentlich")){
                        boolean checkinsertdata = DB.insertBankerOrder(amountDbl, incimeInt, RytmLengthTxt, WeekDayTxt);
                        if (checkinsertdata) {
                            Toast.makeText(BankersOrderActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                            MainActivity.SelectFromBankOrders();
                            addNewToTable();
                        } else {
                            Toast.makeText(BankersOrderActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        boolean checkinsertdata = DB.insertBankerOrder(amountDbl, incimeInt, RytmLengthTxt, year + "-" + month + "-" + day);
                        if (checkinsertdata) {
                            Toast.makeText(BankersOrderActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                            MainActivity.SelectFromBankOrders();
                            addNewToTable();
                        } else {
                            Toast.makeText(BankersOrderActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class RythmSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            RytmLengthTxt = parent.getItemAtPosition(position).toString();
            if (RytmLengthTxt.equals("Wöchentlich")) {
                DateDatBko.setVisibility(DatePicker.GONE);
                WeekDaySpnBko.setVisibility(Spinner.VISIBLE);
            } else{
                DateDatBko.setVisibility(DatePicker.VISIBLE);
                WeekDaySpnBko.setVisibility(Spinner.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class WeekDaySpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            WeekDayTxt = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void addToTable() {
        for (int i = 0; i < MainActivity.piBankorder; i++) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setId(i);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBankorder[i]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBankorder[i])  + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            if (MainActivity.pEinnahenBankorder[i] == 1){
                TblRow.setBackgroundColor(Color.rgb(0,39,0));
            }else{
                TblRow.setBackgroundColor(Color.rgb(59,0,0));
            }
            TextView Tv3 = new TextView(this);
            Tv3.setText(MainActivity.pWiederholung[i]);
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            TextView Tv4 = new TextView(this);
            Tv4.setText(MainActivity.pDateBankorder[i]);
            Tv4.setGravity(Gravity.CENTER);
            Tv4.setTextColor(Color.WHITE);
            TblRow.addView(Tv4);

            System.out.println("--- [Bank Order Table] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Rythmus: " +Tv3.getText() + " Date: " + Tv3.getText());

            TableTblBko.addView(TblRow);
        }
    }
    public void addNewToTable(){
        if (MainActivity.piBankorder - 1 > -1) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBankorder[MainActivity.piBankorder - 1]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBankorder[MainActivity.piBankorder - 1]) + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv2);

            if (MainActivity.pEinnahenBankorder[MainActivity.piBankorder - 1] == 1){
                TblRow.setBackgroundColor(Color.rgb(0,39,0));
            }else{
                TblRow.setBackgroundColor(Color.rgb(59,0,0));
            }
            TextView Tv3 = new TextView(this);
            Tv3.setText(MainActivity.pWiederholung[MainActivity.piBankorder - 1]);
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv3);
            TextView Tv4 = new TextView(this);
            Tv4.setText(MainActivity.pDateBankorder[MainActivity.piBankorder - 1]);
            Tv4.setGravity(Gravity.CENTER);
            Tv4.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv4);

            System.out.println("--- [Bank Order Table] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Rythmus: " +Tv3.getText() + " Date: " + Tv3.getText());

            TableTblBko.addView(TblRow);
        }else{
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBankorder[0]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBankorder[0]) + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv2);
            if (MainActivity.pEinnahenBankorder[0] == 1){
                TblRow.setBackgroundColor(Color.rgb(0,39,0));
            }else{
                TblRow.setBackgroundColor(Color.rgb(59,0,0));
            }
            TextView Tv3 = new TextView(this);
            Tv3.setText(MainActivity.pWiederholung[0]);
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv3);
            TextView Tv4 = new TextView(this);
            Tv4.setText(MainActivity.pDateBankorder[0]);
            Tv4.setGravity(Gravity.CENTER);
            Tv4.setTextColor(Color.rgb(94,94,94));
            TblRow.addView(Tv4);

            System.out.println("--- [Bank Order Table] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Rythmus: " +Tv3.getText() + " Date: " + Tv3.getText());

            TableTblBko.addView(TblRow);
        }
    }
}