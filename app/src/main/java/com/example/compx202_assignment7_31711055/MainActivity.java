package com.example.compx202_assignment7_31711055;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorMgr;
    Sensor accelerometer;
    private int x;
    private int y;
    private int radius;
    private Paint paint;
    private double xMove;
    private double yMove;
    SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            xMove = event.values[0];
            yMove = event.values[2];
            //Treating y as z and vice versa
            Log.i("TAG", "" + event.values[0] +
                    ", " + event.values[1] +
                    ", " + event.values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Get a listener to receive accelerometer information
        Log.d("TAG", "Obtained accelerometer " + accelerometer);

        //Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Set sticky fullscreen mode
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        //Get the ConstraintLayout
        ConstraintLayout root = findViewById(R.id.MainView);

        //Create an instance of the custom view
        GraphicsView myView = new GraphicsView(this);

        //Add the custom view to the ConstraintLayout
        root.addView(myView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(accelerometerListener, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(accelerometerListener, accelerometer);
    }

    //Create a graphic view class extending view
    public class GraphicsView extends View {
        public GraphicsView(Context context) {
            super(context);
            x = 50;
            y = 50;
            radius = 30;
            paint = new Paint();
            paint.setColor(getColor(android.R.color.holo_blue_bright));
        }

        //Set OnDraw to draw a circle
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(x, y, radius, paint);
            x += xMove;
            y += yMove;

            //remain the ball on the screen
            if (x < 40) {
                x = 40;
            }
            if (width - x < 40) {
                x = width - 40;
            }
            if (y < 40) {
                y = 40;
            }
            if (height - y < 40) {
                y = height - 40;
            }
            invalidate();
        }

        @Override
        protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
            super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
            width = newWidth;
            height = newHeight;
        }
    }

}
