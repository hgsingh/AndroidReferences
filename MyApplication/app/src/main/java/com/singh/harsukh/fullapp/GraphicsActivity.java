package com.singh.harsukh.fullapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class GraphicsActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private ImageView im;
    private final static int PIC_REQ = 1;
    private final static int PIC_TAKE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        mVideoView= (VideoView) findViewById(R.id.videoView);
        im = (ImageView) findViewById(R.id.imageView2);
    }

    public void playVideo(View view)
    {
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.moon;
        Uri uri = Uri.parse(uriPath);
        if(uri != null) {
            mVideoView.setVideoURI(uri);
            try {
                mVideoView.setMediaController(new MediaController(this));
                mVideoView.requestFocus();
                mVideoView.start();
            }
            catch(Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }
        else System.out.println("Can't find the path");
    }

    public void takePicture(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PIC_TAKE);
    }

    public void pickImage(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PIC_REQ);
    }

    public static Intent aIntent(Context this_Context)
    {
        Intent intent = new Intent(this_Context, GraphicsActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode == PIC_TAKE)
        {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            im.setImageBitmap(bm);
        }

        if(requestCode == PIC_REQ  && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            im.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
}
