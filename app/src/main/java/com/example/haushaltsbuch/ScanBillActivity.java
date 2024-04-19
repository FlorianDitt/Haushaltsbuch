

package com.example.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.canhub.cropper.CropImageView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;

public class ScanBillActivity extends AppCompatActivity {

    Button CutBtnScan, SelectPhotoBtn, ScanBtn;
    CropImageView ImgCrop;
    ImageView imageView, ImgToCrop;
    TextView detectedText;
    final Activity activity = this;
    boolean i = true;
    private static final String TAG = "TextRecognition";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bill);
        SelectPhotoBtn = findViewById(R.id.SelectPhotoBtnScan);
        CutBtnScan = findViewById(R.id.CutBtnScan);
        ScanBtn = findViewById(R.id.ScanBtnScan);
        imageView = findViewById(R.id.ImgToScan);
        detectedText = findViewById(R.id.detectedText);
        ImgCrop = findViewById(R.id.ImgCrop);
        ImgToCrop = findViewById(R.id.ImgToCrop);
        ScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageToBitmap();
            }
        });
        SelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        CutBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCropper();
            }
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            // do your operation from here....
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap
                            = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(),
                            selectedImageUri);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(selectedImageBitmap);
                ImgToCrop.setImageBitmap(selectedImageBitmap);
            }
        }
    });
    private void imageCropper(){
        Bitmap bitmap1 = ((BitmapDrawable)ImgToCrop.getDrawable()).getBitmap();
        if(i){
            i = false;
            ImgCrop.setImageBitmap(bitmap1);
        }else{
            i = true;
            Bitmap bitmap2= ImgCrop.getCroppedImage();
            ImgCrop.setImageBitmap(null);
            imageView.setImageBitmap(bitmap2);
        }


    }
    private void ImageToBitmap(){
        if(!i){
            i = true;
            Bitmap bitmap2= ImgCrop.getCroppedImage();
            ImgCrop.setImageBitmap(null);
            imageView.setImageBitmap(bitmap2);
        }
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        recognizeText(bitmap);
    }
    private void recognizeText(Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(activity).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> sparseArray =  recognizer.detect(frame);
        String[] All = new ArrayList<String>().toArray(new String[0]);
        ArrayList<String> Numbers = new ArrayList<String>();
        ArrayList<String> Strings = new ArrayList<String>();

        for(int i=0;i < sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            Log.i(TAG, str);
            All = str.split("\\r?\\n");
        }
        for (String string : All) {
            String s = null;
            if (!string.matches("(?i).*pfand.*|.*kg.*|.*x.*|.*%.*")) {
                s = string.replaceAll("[^0-9,.€]", "");
            }
            if ( s != null && !s.equals("")&& !s.equals(".") && !s.equals(",")){
                Numbers.add(s);
            }
        }
        for (String string : All) {
            String s;
            if (!string.matches("(?i).*pfand.*|.*kg.*|.*x.*|.*%.*")) {
                s = string.replaceAll("\\d", "");
            }else{
                s = string;
            }
            if (s.replaceAll(" ", "").length() > 3) {
                Strings.add(s);
            }
        }
        for(int i=0;i < Numbers.size(); i++){
            Log.i(TAG + "Numbers", String.valueOf(Numbers.get(i)));
        }
        for(int i=0;i < Strings.size(); i++){
            Log.i(TAG + "Strings", String.valueOf(Strings.get(i)));
        }
        Intent intent = new Intent(this, ScannedTextActivity.class);

        intent.putExtra("Numbers", Numbers);
        intent.putExtra("Strings", Strings);

        startActivity(intent);
    }
}
