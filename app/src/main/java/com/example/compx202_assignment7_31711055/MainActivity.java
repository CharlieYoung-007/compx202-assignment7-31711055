package com.example.compx202_assignment7_31711055;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    //Create a graphic view class extending view
    public class GraphicsView extends View {
        private int x;
        private int y;
        private int radius;
        private Paint paint;

        public GraphicsView(Context context) {
            super(context);
            x = 0;
            y = 0;
            radius = 30;
            paint = new Paint();
            paint.setColor(getColor(android.R.color.holo_blue_bright));
        }

        //Set OnDraw to draw a circle
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);


            canvas.drawCircle(x, y, radius, paint);
            x += 2;
            y += 2;
            // some code
            invalidate();
        }
    }


}
