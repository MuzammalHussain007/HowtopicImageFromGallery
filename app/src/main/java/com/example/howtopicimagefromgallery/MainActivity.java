package com.example.howtopicimagefromgallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button btn_Gallery,btn_Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection();
        btn_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesrForuploadImage();
            }
        });

        btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesrForuploadImageCamera();
            }
        });
    }

    private void requesrForuploadImageCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }


    private void requesrForuploadImage() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            Uri image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                mImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode==2)
        {
            Bitmap captureImage= (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(captureImage);
        }
    }

    private void connection() {
        mImageView = findViewById(R.id.imageView);
        btn_Gallery = findViewById(R.id.btn_uploadImage);
        btn_Camera = findViewById(R.id.btn_camera);
    }
}