package com.example.ubuntu.camer_trail;

import java.io.IOException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.util.Size;
import android.view.TextureView.SurfaceTextureListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;




import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends Activity implements SurfaceTextureListener {

    private TextureView t_view;
    private SurfaceTexture surface;
    private Camera mCamera;
    private Button button1;
    private Button button2;

    private float alpha;
    private float rotation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        t_view = findViewById(R.id.textureview_1);
        t_view.setSurfaceTextureListener(this);


        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alpha = 100.0f;
                rotation += 90.0f;
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alpha = 50.0f;
                rotation += -90.0f;
            }
        });

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Log.i("pref", mPrefs.getString("uri", null).toString());




    }

    public void onSelect(View view){
        switch(view.getId()){
            case R.id.radioButton:
                Log.i("test", "select");
                Toast.makeText(MainActivity.this,"Toggle this shit!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture arg0, int arg1,
                                          int arg2) {

        alpha = 1.0f;
        rotation = 90.0f;

        mCamera = Camera.open();

        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        List<Camera.Size> listPreview = mCamera.getParameters().getSupportedPreviewSizes();

        Log.i("test", listPreview.toString());


        try {
            mCamera.setPreviewTexture(arg0);
        } catch (IOException t) {
        }



        mCamera.startPreview();
        t_view.setAlpha(alpha);
        t_view.setRotation(rotation);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1,
                                            int arg2) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture arg0) {
        // TODO Auto-generated method stub
        t_view.setAlpha(alpha);
        t_view.setRotation(rotation);
        /*
        try {
            SessionBuilder.getInstance()
                    // 设置一个context可以不用每次都监测MPEG的参数
                    .setContext(getApplicationContext())
                    .setSurfaceHolder(surfaceHolder)
                    .setHost("192.168.50.19")
                    .setAppName("hls").build();
            ;
        } catch (Exception e) {
            Log.e(TAG, "Can't build session", e);
        }
        */
    }
}

