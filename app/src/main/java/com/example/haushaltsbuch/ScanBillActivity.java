

package com.example.haushaltsbuch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class ScanBillActivity extends AppCompatActivity {

    Button PhotoScanBtn, SelectPhotoBtn, ScanBtn;

    ImageView imageView;
    TextView Text23;

    final Activity activity = this;

    private static final String TAG = "TextRecognition";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bill);
        PhotoScanBtn = findViewById(R.id.PhotoBtnScan);
        SelectPhotoBtn = findViewById(R.id.SelectPhotoBtnScan);
        ScanBtn = findViewById(R.id.ScanBtnScan);
        imageView = findViewById(R.id.ImgToScan);
        Text23 = findViewById(R.id.Text23);
        PhotoScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture();
            }
        });
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
    }
    private void TakePicture(){
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
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

        // create an instance of the
        // intent of the type image
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
            }
        }
    });

    private void ImageToBitmap(){
        // Convert ImageView to Bitmap
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        recognizeText(bitmap);
    }
    private void recognizeText(Bitmap bitmap) {

    }
}
