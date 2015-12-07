/*
Created By: Joe Novak and Kole Woodley
Copyright 2015
Date: November and December 2015
Title: Distractless
Created in Android Studio Version 1.4, 1.5, and 2.0
Asset Attribution:
        Icon: Joe Novak
        Splash Screen Video: Joe Novak

Frozen in Frobisher Bay:
James Gordon

        Chorus
    Cold is the arctic sea
    Far are your arms from me
    Long will this winter be
    Frozen in Frobisher Bay
    Frozen in Frobisher Bay

    "One more whale," our captain cried
    "One more whale and well beat the ice."
    But the winter star was in the sky
    The seas were rough the winds were high.

    Chorus

    Deep were the crashing waves
    That tore our whalers mast away
    Dark are these sunless days
    Waiting for the ice to break.

    Chorus

    Strange is a whaler's fate
    To be saved from the raging waves
    Only to waste away
    Frozen in this lonely grave.

    Chorus
*/
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

import java.io.IOException;

public class Splash extends Activity implements TextureView.SurfaceTextureListener {

    MediaPlayer mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextureView textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);
        Button goButton = (Button) findViewById(R.id.b_splash_next);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tutorial tut = new Tutorial();
                Intent intent = tut.ActivitySwitch(Splash.this, Pin.class, 0);
                startActivity(intent);
            }

        });
        final Switch runAss = (Switch) findViewById(R.id.sw_runass);

        runAss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Tutorial.setRunAssistant = isChecked;
            }
        });
    }


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
