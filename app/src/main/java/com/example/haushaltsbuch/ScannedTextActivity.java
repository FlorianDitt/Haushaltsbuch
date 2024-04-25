package com.example.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class ScannedTextActivity extends AppCompatActivity {
    Intent intent;
    int length;
    ArrayList<String> Numbers, Strings;
    Button ReorderBtn, FinishBtn;
    TableLayout TL, FL;
    TableLayout.LayoutParams lp;
    Activity activity = this;
    String Store = "";
    String Sum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_text);
        intent = getIntent();
        Initialize();
        FillTable();

        ReorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reorder();
            }
        });
        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TransactionActivity.class);

                intent.putExtra("Store", Store);
                intent.putExtra("Sum", Sum);

                startActivity(intent);
            }
        });
    }
    private void FillTable(){
        for (int i = 0; i < length; i++){
            TableRow TblRow = new TableRow(this);
            TblRow.setMinimumHeight(80);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText(String.valueOf(i + 1));
            Tv1.setGravity(Gravity.START);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setId(i);
            if (Strings.size() > i){
                Tv2.setText(Strings.get(i));
            }
            Tv2.setGravity(Gravity.START);
            Tv2.setTextColor(Color.WHITE);
            Tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color = Tv2.getCurrentTextColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    switch(hexColor) {
                        case "#FFFFFF":
                            Tv2.setTextColor(Color.rgb(235, 45, 0));
                            break;
                        case "#EB2D00":
                            Tv2.setTextColor(Color.rgb(235, 161, 0));
                            break;
                        case "#EBA100":
                            Tv2.setTextColor(Color.WHITE);
                            break;
                    }
                }
            });
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(this);
            Tv3.setText(String.valueOf(i + 1));
            Tv3.setGravity(Gravity.END);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            TextView Tv4 = new TextView(this);
            if (Numbers.size() > i){
                Tv4.setText(Numbers.get(i));
            }
            Tv4.setGravity(Gravity.END);
            Tv4.setTextColor(Color.WHITE);
            Tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color = Tv4.getCurrentTextColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    switch(hexColor) {
                        case "#FFFFFF":
                            Tv4.setTextColor(Color.rgb(235, 45, 0));
                            break;
                        case "#EB2D00":
                            Tv4.setTextColor(Color.rgb(139, 167, 152));
                            break;
                        case "#8BA798":
                            Tv4.setTextColor(Color.WHITE);
                            break;
                    }
                }
            });
            TblRow.addView(Tv4);

            TL.addView(TblRow);
        }
    }
    private void Initialize(){
        ReorderBtn = findViewById(R.id.ReorderBtn);
        FinishBtn = findViewById(R.id.FinishBtn);
        lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0,10,0,10);
        TL = findViewById(R.id.TableTblScanned);
        FL = findViewById(R.id.TableTblFinish);
        Numbers = (ArrayList<String>) intent.getSerializableExtra("Numbers");
        Strings = (ArrayList<String>) intent.getSerializableExtra("Strings");
        assert Strings != null;
        length = Math.max(Numbers.size(), Strings.size());
    }
    private void Reorder(){
        Numbers = new ArrayList<>();
        Strings = new ArrayList<>();
        for (int i = 0; i<TL.getChildCount();i++){
            View child = TL.getChildAt(i);
            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;
                View cell1 = row.getChildAt(1);
                if (cell1 instanceof TextView) {
                    TextView textView1 = (TextView) cell1;
                    int color = textView1.getCurrentTextColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    if (!textView1.getText().toString().equals("") && hexColor.equals("#FFFFFF")){
                        Strings.add(textView1.getText().toString());
                    } else if (hexColor.equals("#EBA100")) {
                        Strings.add(0,textView1.getText().toString());
                        Numbers.add(0, "");
                        Store = textView1.getText().toString();
                    }
                }
                View cell3 = row.getChildAt(3);
                if (cell3 instanceof TextView) {
                    TextView textView3 = (TextView) cell3;
                    int color = textView3.getCurrentTextColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    Log.i("test", hexColor);
                    if (!textView3.getText().toString().equals("") && (hexColor.equals("#FFFFFF") || hexColor.equals("#8BA798"))){
                        Numbers.add(textView3.getText().toString());
                    }
                    if (hexColor.equals("#8BA798")) {
                        Sum = textView3.getText().toString();
                    }
                }
            }
        }
        TL.removeAllViews();
        FL.removeAllViews();
        length = Math.max(Numbers.size(), Strings.size());
        FillTable();
        FillFinishTable();
    }
    private void FillFinishTable(){
        if (!Store.equals("")||!Sum.equals("")){
            TableRow TblRow = new TableRow(this);
            TblRow.setMinimumHeight(80);
            TblRow.setLayoutParams(lp);
            TextView Tv1 = new TextView(this);
            Tv1.setText("Laden");
            Tv1.setGravity(Gravity.START);
            Tv1.setTextColor(Color.WHITE);
            TblRow.addView(Tv1);
            TextView Tv2 = new TextView(this);
            Tv2.setText(Store);
            Tv2.setGravity(Gravity.START);
            Tv2.setTextColor(Color.WHITE);
            Tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color = Tv2.getCurrentTextColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    switch(hexColor) {
                        case "#FFFFFF":
                            Tv2.setTextColor(Color.rgb(235, 161, 0));
                            break;
                        case "#EBA100":
                            Tv2.setTextColor(Color.rgb(235, 45, 0));
                            break;
                        case "#EB2D00":
                            Tv2.setTextColor(Color.WHITE);
                            break;
                    }
                }
            });
            TblRow.addView(Tv2);
            TextView Tv3 = new TextView(this);
            Tv3.setText("Summe");
            Tv3.setGravity(Gravity.END);
            Tv3.setTextColor(Color.WHITE);
            TblRow.addView(Tv3);
            TextView Tv4 = new TextView(this);
            Tv4.setText(Sum);
            Tv4.setGravity(Gravity.END);
            Tv4.setTextColor(Color.WHITE);
            Tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color = Tv4.getCurrentTextColor();
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    switch(hexColor) {
                        case "#FFFFFF":
                            Tv4.setTextColor(Color.rgb(235, 161, 0));
                            break;
                        case "#EBA100":
                            Tv4.setTextColor(Color.rgb(139, 167, 152));
                            break;
                        case "#8BA798":
                            Tv4.setTextColor(Color.WHITE);
                            break;
                    }
                }
            });
            TblRow.addView(Tv4);
            FL.addView(TblRow);
        }
    }
}