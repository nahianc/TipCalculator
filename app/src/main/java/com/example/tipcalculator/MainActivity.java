package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Fullscreen immersive view
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        EditText billTotalText = (EditText) findViewById(R.id.billTotalField);
        EditText tipPercentText = (EditText) findViewById(R.id.tipPercentField);
        TextView splitNumText = (TextView) findViewById(R.id.splitNumField);
        ImageButton plusButton = (ImageButton) findViewById(R.id.plusButton);
        ImageButton minusButton = (ImageButton) findViewById(R.id.minusButton);

        // Attach a textChangedListener to billTotal, tipPercent, and split fields
        // so split total is dynamically updated upon any change
        billTotalText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        tipPercentText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        splitNumText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Add a onClick listener to plus button to increment party size if clicked
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int partySize = Integer.parseInt( ((TextView)findViewById(R.id.splitNumField)).getText().toString() );
                partySize++;
                ((TextView)findViewById(R.id.splitNumField)).setText(Integer.toString(partySize));
            }
        });

        // Add a onClick listener to minus button to decrement party size if clicked
        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int partySize = Integer.parseInt(((TextView) findViewById(R.id.splitNumField)).getText().toString());
                // If partySize is 1, do nothing
                if (partySize == 1) {
                    return;
                } else {
                    partySize--;
                    ((TextView) findViewById(R.id.splitNumField)).setText(Integer.toString(partySize));
                }
            }
        });

    }

    protected void calculate() {
        double billTotal;
        // Before parsing check if field is empty
        if ( TextUtils.isEmpty(((EditText)findViewById(R.id.billTotalField)).getText()) ) {
            // If field is empty, set bill total to 0
            billTotal = 0.0;
        } else {
            // If not empty, parse total
            billTotal = Double.parseDouble( ((EditText)findViewById(R.id.billTotalField)).getText().toString() );
        }

        double tipPercent = 1.0;
        // Before parsing check if field is empty
        if ( TextUtils.isEmpty(((EditText)findViewById(R.id.tipPercentField)).getText()) ) {
            // If field is empty, set percent to 1 for no tip
            tipPercent = 1.0;
        } else {
            // If not empty, parse percentage and divide by 100 for later calculation
            tipPercent += Integer.parseInt( ((EditText)findViewById(R.id.tipPercentField)).getText().toString() ) / 100.00;
        }

        // Parse party size
        int partySize = Integer.parseInt( ((TextView)findViewById(R.id.splitNumField)).getText().toString() );

        // Calculate bill total split by party size
        double splitTotal = (billTotal * (tipPercent)) / partySize;

        // Update party total text on screen
        ((TextView)findViewById(R.id.splitTotalNumText)).setText(String.valueOf(String.format(Locale.US, "%.2f", splitTotal)));
    }

}


