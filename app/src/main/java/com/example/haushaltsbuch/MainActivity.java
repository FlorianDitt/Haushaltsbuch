package com.example.haushaltsbuch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static DBHelper pDB;
    public static TextView bankBalance;

    public static int pi = 0;
    public static int[] pID = new int [1];
    public static String[] pDatum = new String [1];
    public static double[] pBetrag = new double [1];
    public static int[] pEinnahmen = new int[1];
    public static String[] pGrund = new String[1];

    public static int piBorrow = 0;
    public static int[] pIDBorrow = new int [1];
    public static double[] pBetragBorrow = new double [1];
    public static int[] pBorrowed = new int[1];
    public static String[] pPartner = new String[1];
    public static String[] pRefundDate = new String [1];

    public static int piBalance = 0;
    public static int[] pIdBalance = new int [1];
    public static double[] pBalance = new double [1];
    public static String[] pDatumBalance = new String [1];

    public static String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button transaktionBtn = findViewById(R.id.transaktionBtn);
        Button borrowBtn = findViewById(R.id.borrowBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        pDB = new DBHelper(this);
        SelectFromBankBalence();
        bankBalance = findViewById(R.id.bankBalance);
        if (piBalance - 1 > -1) {
            bankBalance.setText(pBalance[piBalance - 1] + "€");
        }else{
            startActivity(new Intent(MainActivity.this, PopupBankActivity.class));
        }


        transaktionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransactionActivity.class));
                SelectFromTransaktion();
            }

        });

        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BorrowActivity.class));
                SelectFromBorrow();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DeleteActivity.class));
            }
        });

    }
    public static void SelectFromTransaktion() {

        Cursor res = pDB.getdata(DBHelper.TABLE1_NAME);
        if (res.getCount() == 0){
            System.out.println("[Transaktion] No data found");
            pi = 0;
            pID = new int [1];
            pDatum = new String [1];
            pBetrag = new double [1];
            pEinnahmen = new int[1];
            pGrund = new String[1];
            return;
        }

        pi = 0;
        pID = new int [1];
        pDatum = new String [1];
        pBetrag = new double [1];
        pEinnahmen = new int[1];
        pGrund = new String[1];
        while (res.moveToNext()) {
            int ID = res.getInt(0);
            String Datum = res.getString(1);
            double Betrag = res.getDouble(2);
            int Einnahmen = res.getInt(3);
            String Grund = res.getString(4);

            pID[pi] = ID;
            pDatum[pi] = Datum;
            pBetrag[pi] = Betrag;
            pEinnahmen[pi] = Einnahmen;
            pGrund[pi] = Grund;

            pi++;

            pID = Arrays.copyOf(pID, pID.length + 1);
            pDatum = Arrays.copyOf(pDatum, pDatum.length + 1);
            pBetrag = Arrays.copyOf(pBetrag, pBetrag.length + 1);
            pEinnahmen = Arrays.copyOf(pEinnahmen, pEinnahmen.length + 1);
            pGrund = Arrays.copyOf(pGrund, pGrund.length + 1);

        }

    }
    public static void SelectFromBorrow() {

        Cursor res = pDB.getdata(DBHelper.TABLE2_NAME);
        if (res.getCount() == 0) {
            System.out.println("[Borrow] No data found");
            piBorrow = 0;
            pIDBorrow = new int[1];
            pBetragBorrow = new double[1];
            pBorrowed = new int[1];
            pPartner = new String[1];
            pRefundDate = new String[1];
            return;
        }
        piBorrow = 0;
        pIDBorrow = new int[1];
        pBetragBorrow = new double[1];
        pBorrowed = new int[1];
        pPartner = new String[1];
        pRefundDate = new String[1];

        while (res.moveToNext()) {
            int ID = res.getInt(0);
            double Betrag = res.getDouble(1);
            int Borrowed = res.getInt(2);
            String Partner = res.getString(3);
            String RefundDate = res.getString(4);

            pIDBorrow[piBorrow] = ID;
            pBetragBorrow[piBorrow] = Betrag;
            pBorrowed[piBorrow] = Borrowed;
            pPartner[piBorrow] = Partner;
            pRefundDate[piBorrow] = RefundDate;

            piBorrow++;

            pIDBorrow = Arrays.copyOf(pIDBorrow, pIDBorrow.length + 1);
            pBetragBorrow = Arrays.copyOf(pBetragBorrow, pBetragBorrow.length + 1);
            pBorrowed = Arrays.copyOf(pBorrowed, pBorrowed.length + 1);
            pPartner = Arrays.copyOf(pPartner, pPartner.length + 1);
            pRefundDate = Arrays.copyOf(pRefundDate, pRefundDate.length + 1);
        }

    }
    public static void SelectFromBankBalence() {

        Cursor res = pDB.getdata(DBHelper.TABLE3_NAME);
        if (res.getCount() == 0){
            System.out.println("[Balance] No data found");
            piBalance = 0;
            pIdBalance = new int[1];
            pBalance = new double [1];
            pDatumBalance = new String [1];
            return;
        }

        piBalance = 0;
        pIdBalance = new int[1];
        pBalance = new double [1];
        pDatumBalance = new String [1];
        while (res.moveToNext()) {
            int ID = res.getInt(0);
            double Balance = res.getDouble(2);
            String Datum = res.getString(1);

            pIdBalance[piBalance] = ID;
            pBalance[piBalance] = Balance;
            pDatumBalance[piBalance] = Datum;

            piBalance++;

            pBalance = Arrays.copyOf(pBalance, pBalance.length + 1);
            pIdBalance = Arrays.copyOf(pIdBalance, pIdBalance.length + 1);
            pDatumBalance = Arrays.copyOf(pDatumBalance, pDatumBalance.length + 1);

        }
    }
    public static void calculateBalence(){
        SelectFromBankBalence();
        BigDecimal newBalance;
        BigDecimal currentBalance = BigDecimal.valueOf(MainActivity.pBalance[MainActivity.piBalance - 1]);
        BigDecimal Transaction = BigDecimal.valueOf(MainActivity.pBetrag[MainActivity.pi - 1]);
        if (MainActivity.piBalance - 1 > -1) {
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1) {
                newBalance = currentBalance.add(Transaction);
            } else {
                newBalance = currentBalance.subtract(Transaction);
            }
        } else {
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1) {
                newBalance = Transaction;
            } else {
                newBalance = new BigDecimal("0").subtract(Transaction);
            }
        }

        pDB.insertBankBelance(today, newBalance);
        System.out.println("--- [Incerted Bankbalance] Date: " + today + " Amount: " + newBalance + "€");
        bankBalance.setText(newBalance.toString() + "€");
    }
}
