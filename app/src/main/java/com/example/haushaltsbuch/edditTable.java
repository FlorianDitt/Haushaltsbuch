package com.example.haushaltsbuch;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class edditTable {
    public static void InitializTable(int TblLength, TableLayout TL, Activity Activity){
        if (TblLength > 0){
            --TblLength;
        }
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
        );
        lp.setMargins(0,10,0,10);
        for (int i = TblLength ;i > -1; i--) {
            TableRow TblRow = new TableRow(Activity);
            TblRow.setBackgroundResource(R.drawable.border);
            TblRow.setMinimumHeight(80);
            TblRow.setGravity(Gravity.CENTER);
            TblRow.setLayoutParams(lp);
            TblRow.setId(i);
            TextView Tv1 = new TextView(Activity);
            Tv1.setText(Integer.toString(MainActivity.pID[i]));
            Tv1.setGravity(Gravity.CENTER);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(Activity);
            Tv2.setText(MainActivity.pDatum[i]);
            Tv2.setGravity(Gravity.CENTER);
            Tv2.setTextColor(Color.WHITE);
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(Activity);
            Tv3.setText(Double.toString(MainActivity.pBetrag[i])  + "€");
            Tv3.setGravity(Gravity.CENTER);
            Tv3.setTextColor(Color.WHITE);
            Tv3.setMaxWidth(150);
            TblRow.addView(Tv3);
            if (MainActivity.pEinnahmen[i] == 1){
                TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
            } else {
                TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
            }
            TextView Tv5 = new TextView(Activity);
            Tv5.setText(MainActivity.pGrund[i]);
            Tv5.setGravity(Gravity.CENTER);
            Tv5.setTextColor(Color.WHITE);
            Tv5.setMaxWidth(150);
            TblRow.addView(Tv5);

            TL.addView(TblRow);
        }
    }
    public static void addToTable(TableLayout TL, int ID, String DATE, double AMOUNT, String REASON, int INCOME, Activity Activity){
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);
        TableRow TblRow = new TableRow(Activity);
        TblRow.setBackgroundResource(R.drawable.border);
        TblRow.setMinimumHeight(80);
        TblRow.setGravity(Gravity.CENTER);
        TblRow.setLayoutParams(lp);
        TextView Tv1 = new TextView(Activity);
        Tv1.setText(Integer.toString(ID));
        Tv1.setGravity(Gravity.CENTER);
        Tv1.setTextColor(Color.WHITE);
        TblRow.addView(Tv1);
        TextView Tv2 = new TextView(Activity);
        Tv2.setText(DATE);
        Tv2.setGravity(Gravity.CENTER);
        Tv2.setTextColor(Color.WHITE);
        TblRow.addView(Tv2);
        TextView Tv3 = new TextView(Activity);
        Tv3.setText(Double.toString(AMOUNT)  + "€");
        Tv3.setGravity(Gravity.CENTER);
        Tv3.setTextColor(Color.WHITE);
        Tv3.setMaxWidth(150);
        TblRow.addView(Tv3);
        if (INCOME == 1){
            TblRow.setBackgroundColor(Color.rgb(0, 39, 0));
        } else {
            TblRow.setBackgroundColor(Color.rgb(59, 0, 0));
        }
        TextView Tv5 = new TextView(Activity);
        Tv5.setText(REASON);
        Tv5.setGravity(Gravity.CENTER);
        Tv5.setTextColor(Color.WHITE);
        Tv5.setMaxWidth(150);
        TblRow.addView(Tv5);

        TL.addView(TblRow, 0);
    }
}
