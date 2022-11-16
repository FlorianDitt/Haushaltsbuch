package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class TransaktionTableActivity extends AppCompatActivity {

    public static TableLayout pBigTableTblTran;
    TableLayout.LayoutParams lp;
    ImageButton backBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaktion_table);

        pBigTableTblTran = findViewById(R.id.BigTableTblTran);
        lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);
        backBtn4 = findViewById(R.id.backBtn4);

        addToTable();

        backBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addToTable() {
        for (int i = 0; i < MainActivity.pi; i++) {
            TableRow TblRow = new TableRow(this);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TblRow.setId(i);
            TextView Tv1 = new TextView(this);
            Tv1.setText(Integer.toString(MainActivity.pID[i]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(MainActivity.pDatum[i]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(this);
            Tv3.setText(Double.toString(MainActivity.pBetrag[i]) + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[i] == 1) {
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(this);
            Tv5.setText(MainActivity.pGrund[i]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Teansaktion Table] ID: " + Tv1.getText() + " Date: " + Tv2.getText() + " Amount: " + Tv3.getText() + " Reson: " + Tv5.getText());

            pBigTableTblTran.addView(TblRow);
        }
    }
}