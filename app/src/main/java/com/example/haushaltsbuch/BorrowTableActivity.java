package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BorrowTableActivity extends AppCompatActivity {

    TableLayout TableTblBrw;
    TableLayout.LayoutParams lp;
    ImageButton backBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_table);

        TableTblBrw = findViewById(R.id.TableTblBrw);
        lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);

        addToTable();
        backBtn5 = findViewById(R.id.backBtn5);

        backBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

            System.out.println("--- [Borrow Table] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Partner: " + Tv4.getText() + " Refund Date: " + Tv5.getText());

            TableTblBrw.addView(TblRow);
        }
    }
}