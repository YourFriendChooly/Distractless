package com.project.distractless;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class AlarmSplash extends Activity implements TextureView.SurfaceTextureListener {

    MediaPlayer mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextureView textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);
        TextView textView = (TextView) findViewById(R.id.runPrompt);
        Button goButton = (Button) findViewById(R.id.b_splash_next);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmSplash.this, AlarmFragment.class);
                startActivity(intent);
            }

        });
        final Switch runAss = (Switch) findViewById(R.id.sw_runass);
        runAss.setVisibility(View.GONE);
        textView.setText("Focus Mode Activated! Let's Get Productive!");
    }

    //
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface surfaceTexture = new Surface(surface);
        String VIDEO_NAME = "splash.mp4";
        try {
            AssetFileDescriptor video = getApplicationContext().getAssets().openFd(VIDEO_NAME);
            mVideoPlayer = new MediaPlayer();
            mVideoPlayer.setDataSource(video.getFileDescriptor(), video.getStartOffset(), video.getLength());
            mVideoPlayer.setSurface(surfaceTexture);
            mVideoPlayer.setLooping(false);
            mVideoPlayer.setVolume(0, 0);
            mVideoPlayer.prepareAsync();
            mVideoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoPlayer.start();
                }
            });
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

}
