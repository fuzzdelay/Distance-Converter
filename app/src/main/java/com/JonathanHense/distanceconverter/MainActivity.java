package com.JonathanHense.distanceconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView inputDisplay;
    private TextView outputDisplay;

    private EditText userInput;
    private TextView output;

    private TextView convHistory;

    private static final String TAG = "MainActivity_JH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDisplay = findViewById(R.id.inputLabel);
        outputDisplay = findViewById(R.id.outputLabel);

        userInput = findViewById(R.id.inputText);
        output = findViewById(R.id.outputText);

        convHistory = findViewById(R.id.conversionHistory);
        convHistory.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("HISTORY", convHistory.getText().toString());
        outState.putString("OUTPUT", output.getText().toString());
        outState.putString("INPUT", userInput.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        convHistory.setText(savedInstanceState.getString("HISTORY"));
        output.setText(savedInstanceState.getString("OUTPUT"));
        userInput.setText(savedInstanceState.getString("INPUT"));
    }

    public void radioClicked(View v){
        String inputField = "";
        String outputField = "";
        switch(v.getId()){
            case R.id.m2kButton:
                inputField += "Miles Value: ";
                outputField += "Kilometers Value: ";
                break;
            case R.id.k2mButton:
                inputField += "Kilometers Value: ";
                outputField += "Miles Value: ";
                break;
        }
        inputDisplay.setText(inputField);
        outputDisplay.setText(outputField);
    }

    public void convert(View v) {
        String valIn = userInput.getText().toString();
        if (!valIn.trim().isEmpty()) {
            //in the event the user presses convert without entering a value, we don't want
            //the app to crash
            Log.d(TAG, "convert: " + valIn);

            String k2mResult = String.format("%.1f", Double.valueOf(valIn) * 0.621371);
            String m2kResult = String.format("%.1f", Double.valueOf(valIn) * 1.60934);

            Log.d(TAG, "outputKM: " + k2mResult);
            Log.d(TAG, "outputMi: " + m2kResult);

            String historyText = convHistory.getText().toString();

            RadioButton mButton;
            mButton = (RadioButton) findViewById(R.id.m2kButton);

            userInput.setText("");
            if (mButton.isChecked()) {
                output.setText(m2kResult);
                convHistory.setText(String.format("Mi to Km: %s ➔ %s\n%s", valIn, m2kResult, historyText));
                Log.d(TAG, "new val " + m2kResult);
            } else {
                output.setText(k2mResult);
                convHistory.setText(String.format("Km to Mi: %s ➔ %s\n%s", valIn, k2mResult, historyText));
                Log.d(TAG, "new val " + k2mResult);
            }
        }
    }

    public void clear(View v){

        convHistory.setText("");
    }
}