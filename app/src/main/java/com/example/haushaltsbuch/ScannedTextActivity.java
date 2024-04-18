package com.example.haushaltsbuch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    Button ReorderBrn;
    TableLayout TL;
    TableLayout.LayoutParams lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_text);
        intent = getIntent();
        Initialize();
        FillTable();

        ReorderBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reorder();
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
            Tv2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Tv2.setText("");
                    return false;
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
            Tv4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Tv4.setText("");
                    return false;
                }
            });
            TblRow.addView(Tv4);

            TL.addView(TblRow);
        }
    }
    private void Initialize(){
        ReorderBrn = findViewById(R.id.ReorderBrn);
        lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0,10,0,10);
        TL = findViewById(R.id.TableTblScanned);
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
                    TextView textView = (TextView) cell1;
                    if (!textView.getText().toString().equals("")){
                        Strings.add(textView.getText().toString());
                    }
                }
                View cell2 = row.getChildAt(3);
                if (cell2 instanceof TextView) {
                    TextView textView = (TextView) cell2;
                    if (!textView.getText().toString().equals("")){
                        Numbers.add(textView.getText().toString());
                    }
                }
            }
        }
        TL.removeAllViews();
        length = Math.max(Numbers.size(), Strings.size());
        FillTable();
    }
}