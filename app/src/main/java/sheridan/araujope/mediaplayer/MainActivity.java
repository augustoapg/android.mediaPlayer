package sheridan.araujope.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgSound;
    private ImageView mImgPlayPause;
    private SeekBar mSeekBarProgress;
    private SeekBar mSeekBarVolume;
    private MediaPlayer mp;
    private int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgSound = findViewById(R.id.imgSound);
        mImgPlayPause = findViewById(R.id.imgPlayPause);
        mSeekBarProgress = findViewById(R.id.seekBarSoundProgress);
        mSeekBarVolume = findViewById(R.id.seekBarVolume);

        // media player
        mp = MediaPlayer.create(this, R.raw.metal_gear_codec);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();

        // position bar
        mSeekBarProgress.setMax(totalTime);
        mSeekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(i);
                    mSeekBarProgress.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // volume bar
        mSeekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float volumeNum = progress / 100f;
                mp.setVolume(volumeNum, volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();

        mImgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mp.isPlaying()) {
                    mp.start();
                    mImgPlayPause.setBackgroundResource(R.drawable.stop);
                    mImgSound.setBackgroundResource(R.drawable.metal_gear_alert);
                } else {
                    mp.stop();
                    mImgPlayPause.setBackgroundResource(R.drawable.play);
                    mImgSound.setImageResource(R.drawable.metal_gear_alert);
                    mImgSound.setBackgroundResource(R.drawable.image);
                }
            }
        });
    }
}
