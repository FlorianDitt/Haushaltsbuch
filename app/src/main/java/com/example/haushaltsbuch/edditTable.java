package com.example.haushaltsbuch;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
public class edditTable {
    public static void InitializTable(int TblLength, TableLayout.LayoutParams lp, TableLayout TL, Activity Activity){
        for (int i = TblLength-1; i > -1; i--) {
            TableRow TblRow = new TableRow(Activity);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TblRow.setId(i);
            TextView Tv1 = new TextView(Activity);
            Tv1.setMaxWidth(100);
            Tv1.setText(Integer.toString(MainActivity.pID[i]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(Activity);
            Tv2.setMaxWidth(200);
            Tv2.setText(MainActivity.pDatum[i]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(Activity);
            Tv3.setMaxWidth(200);
            Tv3.setText(MainActivity.pBetrag[i] + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[i] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(Activity);
            Tv5.setMaxWidth(200);
            Tv5.setText(MainActivity.pGrund[i]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            TL.addView(TblRow);
        }
    }
    public static void addToTable(int TblLength, TableLayout.LayoutParams lp, TableLayout TL, Activity Activity){
        TableRow TblRow = new TableRow(Activity);
        if (TblLength > 0) {
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(Activity);
            Tv1.setText(Integer.toString(MainActivity.pID[MainActivity.pi - 1]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(Activity);
            Tv2.setText(MainActivity.pDatum[MainActivity.pi - 1]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(Activity);
            Tv3.setText(MainActivity.pBetrag[MainActivity.pi - 1] + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(Activity);
            Tv5.setText(MainActivity.pGrund[MainActivity.pi - 1]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Incerted Transaktion] ID: " + Tv1.getText() + " Date: " + Tv2.getText() + " Amount: " + Tv3.getText() + " Reson: " + Tv5.getText());

        }else{
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(Activity);
            Tv1.setText(Integer.toString(MainActivity.pID[0]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(Activity);
            Tv2.setText(MainActivity.pDatum[0]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(Activity);
            Tv3.setText(MainActivity.pBetrag[0] + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[0] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(Activity);
            Tv5.setText(MainActivity.pGrund[0]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            TblRow.addView(Tv5);

            System.out.println("--- [Incerted Transaktion] ID: " + Tv1.getText() + " Date: " + Tv2.getText() + " Amount: " +Tv3.getText() + " Reson: " + Tv5.getText());

        }
        TL.addView(TblRow, 0);
    }
}
