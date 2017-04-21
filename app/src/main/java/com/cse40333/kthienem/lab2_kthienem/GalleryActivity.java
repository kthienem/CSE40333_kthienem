package com.cse40333.kthienem.lab2_kthienem;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kris on 4/20/2017.
 */

public class GalleryActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private DBHelper dbHelper;
    private long team_id;
    private GridView gridView;
    private String timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        team_id = intent.getLongExtra("id", -1);

        gridView = (GridView) findViewById(R.id.gridView);
        populateGridView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_image);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File storageDirectory = new File(getApplication().getFilesDir(), "images");
                    if (!storageDirectory.exists()) {
                        storageDirectory.mkdirs();
                    }

                    setTimeStamp();
                    File imageFile = new File(storageDirectory, getPictureName());
                    Uri pictureUri = FileProvider.getUriForFile(getApplicationContext(),
                            getPackageName() + ".fileprovider", imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        timeStamp = sdf.format(new Date());
    }
    private String getPictureName() {
        return "GameImage" + timeStamp + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQUEST) {
                File imagePath = new File(getApplicationContext().getFilesDir(), "images");
                File imageFile = new File(imagePath, getPictureName());
                Uri imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);
                byte[] byteArray = getBytes(imageUri, 150);

                //Insert Image
                dbHelper.insertImage(team_id, byteArray, imageUri.toString());
                populateGridView();
            }
        }
    }

    private byte[] getBytes(Uri imageUri, int maxSize) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float)width/(float)height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width/bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height*bitmapRatio);
        }

        Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }

    private void populateGridView() {
        String[] fields = new String[] {DBHelper.IMAGE_COLS[0], DBHelper.IMAGE_COLS[2]};
        String where = "team_id = ?";
        String[] args = new String[] {Long.toString(team_id)};
        int[] items = new int[] {R.id.game_image};

        Cursor cursor = dbHelper.getSelectEntries(DBHelper.TABLE_IMAGES, fields, where, args, DBHelper.IMAGE_COLS[0] + " DESC");

        if (cursor != null) {
            SimpleCursorAdapter galleryCursorAdapter = new SimpleCursorAdapter(this, R.layout.image_layout, cursor, fields, items, 0);

            galleryCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int i) {
                    if (view.getId() == R.id.game_image) {
                        ImageView imageView = (ImageView) view;
                        byte[] imageArray = cursor.getBlob(1);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
                        imageView.setImageBitmap(bitmap);
                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(galleryCursorAdapter);
        }
    }
}
