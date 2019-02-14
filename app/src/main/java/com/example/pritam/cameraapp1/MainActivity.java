package com.example.pritam.cameraapp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView result;
    Button click;
    //static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST=1888;
    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeTextToSpeech();

        result=(ImageView)findViewById(R.id.imageView);


    }

    //code snippet to capture image by volume button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    picture1(result);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    picture1(result);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    public void picture1(View view){

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //directory to save image --> Picture directory
                File pictureDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                //picture name
                String pictureName=getPictureName();
                //complete location address of picture
                File imagefile=new File(pictureDirectory,pictureName);
                //uri
                Uri pictureUri=Uri.fromFile(imagefile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);

    }

    private String getPictureName() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd_HHmmss");
        String timestamp=sdf.format(new Date());
        return "noteCapture_"+timestamp+".jpg";
    }

    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==RESULT_OK){
            if (requestCode==CAMERA_REQUEST){

            }
        }
    }

    private void initializeTextToSpeech() {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(myTTS.getEngines().size()==0){
                    Toast.makeText(MainActivity.this,"No TTS Engine", Toast.LENGTH_LONG).show();
                    finish();
                }           else {
                    myTTS.setLanguage(Locale.ENGLISH);
                    speak("Clickkkk Volume Button to take image. Again clickkk volume button to capture image");
                }
            }

        });
    }

    private void speak(String s) {
        if(Build.VERSION.SDK_INT >= 21){
            myTTS.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
        }else{
            myTTS.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }
    }





  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");   //imageBitmap is the captured image
            result.setImageBitmap(imageBitmap);
        }
    }*/

   /* public void picture(View view) {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/
}
