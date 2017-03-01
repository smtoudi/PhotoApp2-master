package com.example.loop.photoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoListener {

    private PhotoAdapter photoAdapter;
    String mCurrentPhotoPath;
    Uri photoURI = null;
    private static final int REQUEST_CAPTURE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter = new PhotoAdapter(this, this);
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_photo) {
            takePhoto();
            return true;
        }
        return false;
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.loop.photoapp.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_CAPTURE_PHOTO);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURE_PHOTO && resultCode == RESULT_OK) {
            String desc = SimpleDateFormat.getDateTimeInstance().format(new Date());
            Photo photo = new Photo(desc, photoURI);
            photoAdapter.addPhoto(photo);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onPhotoClicked(Photo photo) {
        Log.d("MainActivity", "Photo clicked " + photo);
        Intent intent = new Intent(this, ImageActivity.class);
        intent.setData(photo.getUri());
        startActivity(intent);
    }
    private List<File> getListOfPhotoFiles(){
        List<File> list = new ArrayList<>();
        File containingFolder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(!containingFolder.exists()) return list;

        for (File file : containingFolder.listFiles()) {
            if (file.isFile()&&file.getName().contains("JPEG")) list.add(file);
        }

        return list;
    }

    private List<Photo> getListOfPhotosFromFileList(List<File> fileList){
        List<Photo> photoList = new ArrayList<>();
        for(File file: fileList){
            String timeStamp = file.getName().substring(5);
            Uri uri = FileProvider.getUriForFile(this,"com.example.rent.cameraapp.fileprovider",file);
            photoList.add(new Photo(timeStamp,uri));
        }

        return photoList;
    }
}
