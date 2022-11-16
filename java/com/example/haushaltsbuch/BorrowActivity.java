package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import org.hsqldb.Table;

public class BorrowActivity extends AppCompatActivity {

    EditText amountTxtBrw, partnerTxtBrw;
    DatePicker RefundDatBrw;
    Switch borrowesBolBrw;
    Button submitBtnBrw, tableBtnBrw;
    ImageButton backBtn;
    DBHelper DB;
    TableLayout TableTblBrw;
    TableLayout.LayoutParams lp;
    public static int borrowedInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        backBtn = findViewById(R.id.backBtn2);

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
        TableTblBrw = (TableLayout) findViewById(R.id.TableTblBrw);
        if (TableTblBrw.getChildCount() == 1){
            addToTable();
        }

        amountTxtBrw = findViewById(R.id.amountTxtBrw);
        borrowesBolBrw = findViewById(R.id.borrowesBolBrw);
        partnerTxtBrw = findViewById(R.id.partnerTxtBrw);
        RefundDatBrw = findViewById(R.id.RefundDatBrw);
        tableBtnBrw = findViewById(R.id.tableBtnBrw);

        submitBtnBrw = findViewById(R.id.submitBtnBrw);

        DB = new DBHelper(this);


        tableBtnBrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this,BorrowTableActivity.class));
            }
        });

        submitBtnBrw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountTxt = amountTxtBrw.getText().toString();
                String partnerTxt = partnerTxtBrw.getText().toString();

                amountTxtBrw.getText().clear();
                partnerTxtBrw.getText().clear();

                int dayInt = RefundDatBrw.getDayOfMonth();
                String day;
                if (String.valueOf(dayInt).length() == 1){
                    day = "0" + dayInt;
                }else{
                    day = Integer.toString(dayInt);
                }
                int monthInt = RefundDatBrw.getMonth() + 1;
                String month;
                if (String.valueOf(monthInt).length() == 1){
                    month = "0" + monthInt;
                }else{
                    month = Integer.toString(monthInt);
                }
                int year = RefundDatBrw.getYear();

                if (amountTxt.equals("") || partnerTxt.equals("")) {
                    Toast.makeText(BorrowActivity.this, "No Data to Insert", Toast.LENGTH_SHORT).show();
                } else {
                    double amountDbl = Double.parseDouble(amountTxt);
                    if (borrowesBolBrw.isChecked()){
                        borrowedInt = 1;
                    } else {
                        borrowedInt = 0;
                    }
                    boolean checkinsertdata = DB.insertBorrow(amountDbl, borrowedInt, partnerTxt, year + "-" + month + "-" + day);
                    if (checkinsertdata) {
                        Toast.makeText(BorrowActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                        MainActivity.SelectFromBorrow();
                        addNewToTable();
                    } else {
                        Toast.makeText(BorrowActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void addToTable() {
        for (int i = 0; i < MainActivity.piBorrow; i++) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TblRow.setId(i);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBorrow[i]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBorrow[i])  + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            if (MainActivity.pBorrowed[i] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv4 = new TextView(this);
            Tv4.setText(MainActivity.pPartner[i]);
            Tv4.setGravity(Gravity.CENTER);
            Tv4.setTextColor(Color.WHITE);
            TblRow.addView(Tv4);
            TextView Tv5 = new TextView(this);
            Tv5.setText(MainActivity.pRefundDate[i]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Table Borrow] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Partner: " + Tv4.getText() + " Refund Date: " + Tv5.getText());

            TableTblBrw.addView(TblRow);
        }
    }
    public void addNewToTable(){
        if (MainActivity.piBorrow - 1 > -1) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBorrow[MainActivity.piBorrow - 1]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBorrow[MainActivity.piBorrow - 1]) + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            if (MainActivity.pBorrowed[MainActivity.piBorrow - 1] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv4 = new TextView(this);
            Tv4.setText(MainActivity.pPartner[MainActivity.piBorrow - 1]);
            Tv4.setGravity(Gravity.CENTER);
            Tv4.setTextColor(Color.WHITE);
            TblRow.addView(Tv4);
            TextView Tv5 = new TextView(this);
            Tv5.setText(MainActivity.pRefundDate[MainActivity.piBorrow - 1]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Incerted Borrow] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Partner: " + Tv4.getText() + " Refund Date: " + Tv5.getText());

            TableTblBrw.addView(TblRow);
        }else{
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBorrow[0]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBorrow[0]) + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            if (MainActivity.pBorrowed[0] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv4 = new TextView(this);
            Tv4.setText(MainActivity.pPartner[0]);
            Tv4.setGravity(Gravity.CENTER);
            Tv4.setTextColor(Color.WHITE);
            TblRow.addView(Tv4);
            TextView Tv5 = new TextView(this);
            Tv5.setText(MainActivity.pRefundDate[0]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Incerted Borrow] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Partner: " + Tv4.getText() + " Refund Date: " + Tv5.getText());

            TableTblBrw.addView(TblRow);
        }
    }
}