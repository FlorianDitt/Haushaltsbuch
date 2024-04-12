package com.example.haushaltsbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PopupBankActivity extends AppCompatActivity {

    EditText amountTxtPopup;
    Button SubmitBtnPopup;
    static DBHelper DB;
    public static String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_bank);

        amountTxtPopup = findViewById(R.id.amountTxtPopup);
        SubmitBtnPopup = findViewById(R.id.SubmitBtnPopup);

        DB = new DBHelper(this);

        SubmitBtnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountTxt = amountTxtPopup.getText().toString();

                if(amountTxt.equals("")){
                    Toast.makeText(PopupBankActivity.this, "No Data to Insert", Toast.LENGTH_SHORT).show();
                }else{
                    amountTxtPopup.getText().clear();
                    startActivity(new Intent(PopupBankActivity.this, MainActivity.class));
                    BigDecimal amountint = new BigDecimal(amountTxt);
                    boolean checkinsertdata = DB.insertBankBelance(today, amountint);
                    if (checkinsertdata) {
                        Toast.makeText(PopupBankActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PopupBankActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}