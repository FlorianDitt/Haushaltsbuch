package com.example.haushaltsbuch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

    public static int piBankorder = 0;
    public static int[] pIDBankorder = new int [1];
    public static double[] pBetragBankorder = new double [1];
    public static int[] pEinnahenBankorder = new int[1];
    public static String[] pWiederholung = new String[1];
    public static String[] pDateBankorder = new String [1];

    public static String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    @RequiresApi(api = Build.VERSION_CODES.O)
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

        if(piBankorder == 0){
            SelectFromBankOrders();
            CheckForTransaktion();
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
    public static void SelectFromBankOrders() {
        Cursor res = pDB.getdata(DBHelper.TABLE4_NAME);
        if (res.getCount() == 0){
            System.out.println("[Orders] No data found");
            piBankorder = 0;
            pIDBankorder = new int [1];
            pBetragBankorder = new double [1];
            pEinnahenBankorder = new int[1];
            pWiederholung = new String[1];
            pDateBankorder = new String [1];
            return;
        }

        piBankorder = 0;
        pIDBankorder = new int [1];
        pBetragBankorder = new double [1];
        pEinnahenBankorder = new int[1];
        pWiederholung = new String[1];
        pDateBankorder = new String [1];

        while (res.moveToNext()) {
            int ID = res.getInt(0);
            double Betrag = res.getDouble(1);
            int Einnahmen = res.getInt(2);
            String Wiederholung = res.getString(3);
            String Datum = res.getString(4);

            pIDBankorder[piBankorder] = ID;
            pBetragBankorder[piBankorder] = Betrag;
            pEinnahenBankorder[piBankorder] = Einnahmen;
            pWiederholung[piBankorder] = Wiederholung;
            pDateBankorder[piBankorder] = Datum;

            piBankorder++;

            pIDBankorder = Arrays.copyOf(pIDBankorder, pIDBankorder.length + 1);
            pBetragBankorder = Arrays.copyOf(pBetragBankorder, pBetragBankorder.length + 1);
            pEinnahenBankorder = Arrays.copyOf(pEinnahenBankorder, pEinnahenBankorder.length + 1);
            pWiederholung = Arrays.copyOf(pWiederholung, pWiederholung.length + 1);
            pDateBankorder = Arrays.copyOf(pDateBankorder, pDateBankorder.length + 1);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CheckForTransaktion(){
        if (piBalance -1 > -1 && piBankorder -1 > -1) {
            LocalDate start = LocalDate.parse(pDatumBalance[piBalance - 1]);
            LocalDate end = LocalDate.parse(today);
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.equals(end)) {
                start = start.plusDays(1);
                totalDates.add(start);
            }
            System.out.println(totalDates);
            for (int i = 0; i < piBankorder; i++) {
                for (int j = 0; j < totalDates.size(); j++) {
                    String CheckDAY = totalDates.get(i).toString().substring(8, 10);
                    String CheckMONTH = totalDates.get(i).toString().substring(5, 7);
                    String CheckYEAR = totalDates.get(i).toString().substring(0, 4);
                    String CheckWEEKDAY = totalDates.get(i).getDayOfWeek().toString();
                    String DAY = pDateBankorder[i].substring(8, 10);
                    String MONTH = pDateBankorder[i].substring(5, 7);
                    String YEAR = pDateBankorder[i].substring(0, 4);
                    switch (pWiederholung[i]) {
                        case "Wöchentlich":
                            if (CheckWEEKDAY.equals(pDateBankorder[i])){
                                boolean checkinsertdataDay = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                                if (checkinsertdataDay) {
                                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                    MainActivity.SelectFromTransaktion();
                                    calculateBalence();
                                } else {
                                    Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                        case "Täglich":
                            boolean checkinsertdataDay = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                            if (checkinsertdataDay) {
                                Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                MainActivity.SelectFromTransaktion();
                                calculateBalence();
                            } else {
                                Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Monatlich":
                            if (Integer.parseInt(DAY) > 28 && Integer.parseInt(CheckMONTH) == 2 && Integer.parseInt(CheckDAY) == 28) {
                                boolean checkinsertdataMonth = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                                if (checkinsertdataMonth) {
                                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                    MainActivity.SelectFromTransaktion();
                                    calculateBalence();
                                } else {
                                    Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                                }
                            } else if (Integer.parseInt(DAY) < 31 && DAY.equals(CheckDAY)) {
                                boolean checkinsertdataMonth = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                                if (checkinsertdataMonth) {
                                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                    MainActivity.SelectFromTransaktion();
                                    calculateBalence();
                                } else {
                                    Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                                }
                            } else if (Integer.parseInt(DAY) == 31 && Integer.parseInt(CheckDAY) == 30) {
                                boolean checkinsertdataMonth = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                                if (checkinsertdataMonth) {
                                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                    MainActivity.SelectFromTransaktion();
                                    calculateBalence();
                                } else {
                                    Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                System.out.println("[ERROR] Monthly Bank order");
                            }
                            break;
                        case "Jährlich":
                            if (!YEAR.equals(CheckYEAR)) {
                                if (Integer.parseInt(MONTH) == 2 && Integer.parseInt(DAY) == 29 && CheckMONTH.equals(MONTH) && Integer.parseInt(CheckDAY) == 28) {
                                    boolean checkinsertdataYear = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                                    if (checkinsertdataYear) {
                                        Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                        MainActivity.SelectFromTransaktion();
                                        calculateBalence();
                                    } else {
                                        Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (CheckMONTH.equals(MONTH) && CheckDAY.equals(DAY)) {
                                    boolean checkinsertdataYear = pDB.insertTransaktion(totalDates.get(i).toString(), pBetragBankorder[i], pEinnahenBankorder[i], "Dauerauftrag");
                                    if (checkinsertdataYear) {
                                        Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                                        MainActivity.SelectFromTransaktion();
                                        calculateBalence();
                                    } else {
                                        Toast.makeText(MainActivity.this, "New Entry not Inserted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;
                            }
                    }
                }
            }
        }
    }
    public static void calculateBalence(){
        SelectFromBankBalence();
        double newBalance;
        if (MainActivity.piBalance - 1 > -1) {
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1) {
                newBalance = (MainActivity.pBalance[MainActivity.piBalance - 1] + MainActivity.pBetrag[MainActivity.pi - 1]);
            } else {
                newBalance = (MainActivity.pBalance[MainActivity.piBalance - 1] - MainActivity.pBetrag[MainActivity.pi - 1]);
            }
        } else {
            if (MainActivity.pEinnahmen[MainActivity.pi - 1] == 1) {
                newBalance = (MainActivity.pBetrag[MainActivity.pi - 1]);
            } else {
                newBalance = - (MainActivity.pBetrag[MainActivity.pi - 1]);
            }
        }

        pDB.insertBankBelance(today, newBalance);
        MainActivity.bankBalance.setText(newBalance + "€");
    }
}
