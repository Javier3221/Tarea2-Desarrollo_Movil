package com.example.tarea2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnTouchListener, View.OnKeyListener {
    LinearLayout layout;
    EditText monto;
    TextView dollars;
    TextView cents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monto = findViewById(R.id.monto);
        dollars = findViewById(R.id.dollars);
        cents = findViewById(R.id.cents);
        layout = findViewById(R.id.background_gradient);

        layout.setOnTouchListener(this);

        monto.setOnKeyListener(this);

        TextView clear = findViewById(R.id.clear);
        TextView exit = findViewById(R.id.exit);

        exit.setOnTouchListener(exitSwipeListener);
        clear.setOnTouchListener(clearSwipeListener);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (monto.getText().toString().equals("")) {
                Toast errorToast = Toast.makeText(getApplicationContext(), "Monto inválido", Toast.LENGTH_SHORT);
                errorToast.show();
                return false;
            }
            EditText amount = (EditText) view;
            int[] moneyCount = CalcDollarsAndCents(Float.parseFloat(amount.getText().toString()));
            dollars.setText("Billetes de 100: " + moneyCount[0] + "\n Billetes de 50: " + moneyCount[1] + "\n Billetes de 20: " + moneyCount[2]);
            dollars.setVisibility(View.VISIBLE);
            cents.setText("Monedas de 10: " + moneyCount[3] + "\n Monedas de 5: " + moneyCount[4]);
            cents.setVisibility(View.VISIBLE);
            return false;
        }
        return false;
    }

    private int[] CalcDollarsAndCents(float amount) {
        float temp = amount;
        int[] moneyCount = new int[5];

        moneyCount[0] = 0;
        for (int i = 0; temp >= 100; temp -= 100) {
            i += 1;
            moneyCount[0] = i;
        }

        moneyCount[1] = 0;
        for (int i = 0; temp >= 50; temp -= 50) {
            i += 1;
            moneyCount[1] = i;
        }

        moneyCount[2] = 0;
        for (int i = 0; temp >= 20; temp -= 20) {
            i += 1;
            moneyCount[2] = i;
        }

        moneyCount[3] = 0;
        for (int i = 0; temp >= 0.10; temp -= 0.10) {
            i += 1;
            moneyCount[3] = i;
        }

        moneyCount[4] = 0;
        for (int i = 0; temp >= 0.05; temp -= 0.05) {
            i += 1;
            moneyCount[4] = i;
        }

        return moneyCount;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float initialY = 0;

        switch(action){
            case MotionEvent.ACTION_DOWN:
                initialY = motionEvent.getY();
                return true;

            case MotionEvent.ACTION_UP:
                float finalY = motionEvent.getY();

                ConstraintLayout container = findViewById(R.id.main_container);
                RelativeLayout childContainer = findViewById(R.id.content);
                LinearLayout swipeZone = findViewById(R.id.swipe_zone);

                if (initialY < finalY) {
                    container.setBackgroundColor(getResources().getColor(R.color.black));

                    for (int i = 0; i < childContainer.getChildCount(); i++) {
                        View child = childContainer.getChildAt(i);
                        if (child instanceof TextView) {
                            TextView text = (TextView) child;
                            text.setTextColor(getResources().getColor(R.color.white));
                        } else if (child instanceof EditText) {
                            EditText text = (EditText) child;
                            text.setTextColor(getResources().getColor(R.color.white));
                        }
                    }

                    for (int i = 0; i < swipeZone.getChildCount(); i++) {
                        View child = swipeZone.getChildAt(i);
                        TextView text = (TextView) child;
                        text.setTextColor(getResources().getColor(R.color.white));
                    }
                } else if (initialY > finalY) {
                    container.setBackgroundColor(getResources().getColor(R.color.white));

                    for (int i = 0; i < childContainer.getChildCount(); i++) {
                        View child = childContainer.getChildAt(i);
                        if (child instanceof TextView) {
                            TextView text = (TextView) child;
                            text.setTextColor(getResources().getColor(R.color.black));
                        } else if (child instanceof EditText) {
                            EditText text = (EditText) child;
                            text.setTextColor(getResources().getColor(R.color.black));
                        }
                    }

                    for (int i = 0; i < swipeZone.getChildCount(); i++) {
                        View child = swipeZone.getChildAt(i);
                        TextView text = (TextView) child;
                        text.setTextColor(getResources().getColor(R.color.black));
                    }
                }
                return true;
        }
        return false;
    }

    private View.OnTouchListener exitSwipeListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
            finishAndRemoveTask();
            return false;
        }
    };

    private View.OnTouchListener clearSwipeListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
            monto.setText("");
            return false;
        }
    };
}