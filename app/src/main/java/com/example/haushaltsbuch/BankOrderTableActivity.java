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

public class BankOrderTableActivity extends AppCompatActivity {

    TableLayout TableTblBko;
    TableLayout.LayoutParams lp;
    ImageButton backBtn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_order_table);

        TableTblBko = findViewById(R.id.TableTblBko);
        lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);

        addToTable();
        backBtn7 = findViewById(R.id.backBtn7);

        backBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addToTable() {
        for (int i = 0; i < MainActivity.piBankorder; i++) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TblRow.setId(i);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pIDBankorder[i]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Double.toString(MainActivity.pBetragBankorder[i]) + "€");
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            if (MainActivity.pEinnahenBankorder[i] == 1) {
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
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

            System.out.println("--- [Bank Order Table] ID: " + Tv1.getText() + " Amount: " + Tv2.getText() + " Rythmus: " + Tv3.getText() + " Date: " + Tv3.getText());

            TableTblBko.addView(TblRow);
        }
    }
}