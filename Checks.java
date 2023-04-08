package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {


    private CheckBox cbHarry, cbMatrix, cbJoker;
    private RadioGroup rgGender;
    private ProgressBar pB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start code here
        cbHarry = findViewById(R.id.cBHarry);
        cbJoker = findViewById(R.id.cBJoker);
        cbMatrix = findViewById(R.id.cBMatrix);
        rgGender = findViewById(R.id.rgGender);

        //Progress Bar
        pB = findViewById(R.id.progressBar);




        int checkedButton = rgGender.getCheckedRadioButtonId();
        switch(checkedButton){
            case R.id.rbMale:
                Toast.makeText(MainActivity.this, "You're a male", Toast.LENGTH_SHORT).show();
            case R.id.rbFemale:
                Toast.makeText(MainActivity.this, "female", Toast.LENGTH_SHORT).show();
            case R.id.rbOther:
                Toast.makeText(MainActivity.this, "other", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.rbMale:
                        Toast.makeText(MainActivity.this, "You're a male", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbFemale:
                        Toast.makeText(MainActivity.this, "female", Toast.LENGTH_SHORT).show();
                        pB.setVisibility(View.VISIBLE);

                        break;
                    case R.id.rbOther:
                        Toast.makeText(MainActivity.this, "other", Toast.LENGTH_SHORT).show();
                        pB.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
        if(cbHarry.isChecked()){

        }

        cbHarry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(MainActivity.this, "you have watched harry potter", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "go watch hp", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }



}
