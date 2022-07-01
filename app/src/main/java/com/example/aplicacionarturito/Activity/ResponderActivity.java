package com.example.aplicacionarturito.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ColorFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.example.aplicacionarturito.Model.Memoria;
import com.example.aplicacionarturito.Presenter.PresenterMemoria;
import com.example.aplicacionarturito.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponderActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivoSalida = null;
    private Button btn_recorder;

    private DatabaseReference databaseReference;
    private DatabaseReference reference;
    private StorageReference storageReference;

    MediaRecorder recorder;
    File audiofile = null;
    static final String TAG = "MediaRecording";
    Button startButton,stopButton;
    ProgressDialog progressDialog;

    Uri newUri;
    MediaPlayer mediaPlayer;
    Button playBtn, pauseBtn,btnenviar;
    String  fullpaht;
    ImageView imggrabar,imgstop,imgplay;
    private final int frames = 9;
    private int currentAnimationFrame = 0;
    private LottieAnimationView animationView;

    PresenterMemoria presenter;

    String id,paciente_id,CategoriaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResponderActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }


        reference= FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        btnenviar = (Button) findViewById(R.id.btnenviar);

        imggrabar=(ImageView)findViewById(R.id.imggrabar);
        imgstop=(ImageView)findViewById(R.id.imggstop);
        imgplay=(ImageView)findViewById(R.id.imgplay);

        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  reproducir(archivoSalida);
                reproducir(fullpaht);
            }
        });

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newUri!=null){
                    setAudio(newUri);
                }else{
                    Toast.makeText(ResponderActivity.this, "no hay audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        animationView = findViewById(R.id.lottierecord);
        resetAnimationView();
        id=getIntent().getStringExtra("id");
        paciente_id=getIntent().getStringExtra("paciente_id");
        CategoriaId=getIntent().getStringExtra("CategoriaId");

        presenter= new PresenterMemoria(this,reference,paciente_id);

    }
    private void playAudio(String ruta) {

        String audioUrl = ruta;

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }

    public void startRecording(View view) throws IOException {
        //startButton.setEnabled(false);
       /// stopButton.setEnabled(true);

        imggrabar.setEnabled(false);
        imgstop.setEnabled(true);
        imgplay.setEnabled(false);
        imggrabar.setImageResource(R.drawable.ic_record);
        goAnimate();
        //Creating file
        File dir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }
        //Creating MediaRecorder and specifying audio source, output format, encoder & output format
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.prepare();
        recorder.start();
    }

    private  void goAnimate(){
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();
        animationView.loop(true);
    }
    private  void stopAnimate(){
        animationView.loop(false);
        animationView.setVisibility(View.INVISIBLE);
    }


    protected void addRecordingToMediaLibrary() {
        //creating content values of size 4
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

        //creating content resolver and storing it in the external content uri
        ContentResolver contentResolver = getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        newUri = contentResolver.insert(base, values);
       // setImage(newUri);
        //sending broadcast message to scan the media file so that it can be available
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));

        archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() +  audiofile.getName();
        fullpaht= audiofile.getName();
        ////storage/emulated/0/sound2259638484520578445.3gp
        Toast.makeText(this, "ruta:" + newUri, Toast.LENGTH_LONG).show();


    }


    public void stopRecording(View view) {
        //startButton.setEnabled(true);
        //stopButton.setEnabled(false);
        imggrabar.setEnabled(true);
        imgstop.setEnabled(false);
        imgplay.setEnabled(true);
        imggrabar.setImageResource(R.drawable.ic_grabar);
        stopAnimate();
        //stopping recorder
        recorder.stop();
        recorder.release();
        //after stopping the recorder, create the sound file and add it to media library.
        addRecordingToMediaLibrary();
    }


    public void setAudio(Uri urAudio) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Cargando..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StorageReference fotoref=storageReference.child("Audio").child(urAudio.getLastPathSegment());
        fotoref.putFile(urAudio).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull @org.jetbrains.annotations.NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw  new Exception();
                }
                return fotoref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri uridownload =task.getResult();
                    Memoria o = new Memoria();
                    o.setAudio(uridownload.toString());
                    o.setId(id);
                    o.setCategoriaId(CategoriaId);
                    presenter.update(o);
                    finish();
                }
            }
        });
    }

    public void reproducir(String path) {
        String stogra=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+path;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(stogra);
            mediaPlayer.prepare();
        } catch (IOException e){
        }
        mediaPlayer.start();
      //  if (mediaPlayer.te)
        Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_SHORT).show();
    }

    private void resetAnimationView() {
        currentAnimationFrame = 0;
        animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return null;
                    }
                }
        );
    }






















    public void Recorder(View view){
        if(grabacion == null){
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            grabacion.setOutputFile(archivoSalida);

            try{
                grabacion.prepare();
                grabacion.start();
            } catch (IOException e){
            }

            btn_recorder.setBackgroundResource(R.drawable.rec);
            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
        } else if(grabacion != null){
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            Uri uri  = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            btn_recorder.setBackgroundResource(R.drawable.stop_rec);
            Toast.makeText(getApplicationContext(), "Grabación finalizada", Toast.LENGTH_SHORT).show();
        }
    }




//
//    public void reproducir(View v) {
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, url1);
//        mediaPlayer.start();
//    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == peticion) {
//            url1 = data.getData();
//        }
//    }

}