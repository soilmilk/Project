package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TextView needs to be global in order to be used in the onclick method
    private TextView t;
    private EditText e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start code here
        Button  b = findViewById(R.id.textHelloButton);
        e = findViewById(R.id.edtTxt);
        t = findViewById(R.id.textHello);

        b.setOnClickListener(this);
        e.setOnClickListener(this);
        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Long Click!", Toast.LENGTH_LONG).show();
                return true;
            }
        });




    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textHelloButton:

                //toString converts the Editable return of getText() to a string to be printed out
                t.setText("Hello" + e.getText().toString());

                break;
            case R.id.edtTxt:
                Toast.makeText(this, "Attempting to run something!", Toast.LENGTH_SHORT).show();

            default:
                break;
        }
    }
}
