package correia.felipe.video_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private int VIDEO_REQUEST_CODE = 100;
    private int START_CAMERA = 0;
    Button brecord, bplay;
    VideoView vv;
    private boolean touched = false;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        brecord = (Button) findViewById(R.id.brecord);
        vv = (VideoView) findViewById(R.id.videoView);


        brecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callVideo = new Intent();
                callVideo.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(callVideo, START_CAMERA);

            }
        });

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        vv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!touched) {
                    touched = true;
                    vv.requestFocus();

                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            touched = false;
                        }
                    }, 100);
                }

                return true;
            }
        });

    }

/*    public void captureVideo(){
        Intent camera_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File video_file = getFilepath();
        Uri video_uri = Uri.fromFile(video_file);
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, video_uri);
        camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(camera_intent, VIDEO_REQUEST_CODE);
    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==START_CAMERA && resultCode==RESULT_OK){
                Uri videoUri = data.getData();
                vv.setVideoURI(videoUri);
                vv.start();
            }else {
                Toast.makeText(getApplicationContext(), "Opsss.....", Toast.LENGTH_SHORT).show();
            }

    }
/*
    public File getFilepath(){
        File folder = new File("sdcard/video_app");
        if(!folder.exists()){
            folder.mkdir();
        }

        File video_file = new File(folder,"sample_video.mp4");

        return video_file;
    }
    */
}
