package com.example.hitchcock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    // Class Variables (highlighted below in purple,) are also called fields (in java)
    TextView resultText;
    Button calculateButton;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    //private RadioButton nonBinaryButton;

    private RadioButton imperialButton;
    private RadioButton metricButton;
    private EditText feetEditText;
    private EditText inchesEditText;
    private EditText weightEditText;
    private EditText ageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setupButtonClickListener();
    }

    private void findViews() {

        // Class Variable up top in public highlighted in purple
        resultText = findViewById(R.id.text_view_result);
        maleButton = findViewById(R.id.radiobutton_male);
        femaleButton = findViewById(R.id.radiobutton_female);
        //nonBinaryButton = findViewById(R.id.radiobutton_non_binary);
        imperialButton = findViewById(R.id.radiobutton_imperial);
        metricButton = findViewById(R.id.radiobutton_metric);
        feetEditText = findViewById(R.id.edit_text_feet);
        inchesEditText = findViewById(R.id.edit_text_inches);
        weightEditText = findViewById(R.id.edit_text_weight);
        ageEditText = findViewById(R.id.edit_text_age);

        // Same here:
        calculateButton = findViewById(R.id.button_calculate);

    }

    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double bmiResult = calculateB();
                boolean checkMissingInput = checkForEmptyInput();

                if (checkMissingInput) {
                    String alertPopUp = "Please enter in the missing values";
                    Toast.makeText(MainActivity.this, alertPopUp, Toast.LENGTH_LONG).show();
                } else {

                    if (!checkIfUnderage()) displayResult(bmiResult);
                    else {
                        displayGuidance(bmiResult);
                    }
                }
            }
        });
    }

    private boolean checkForEmptyInput() {

        String feetStrText = feetEditText.getText().toString();
        String inchesStrText = inchesEditText.getText().toString();
        String weightStrText = weightEditText.getText().toString();
        String ageStrText = ageEditText.getText().toString();

        return ageStrText.isEmpty() || feetStrText.isEmpty() || inchesStrText.isEmpty() || weightStrText.isEmpty();
    }

    private boolean checkIfUnderage() {
        String ageStrText = ageEditText.getText().toString();
        int age = Integer.parseInt(ageStrText);

        return age < 18; //true
    }

    public boolean onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        return metricButton.isChecked();
    }

    private double calculateB() {

        boolean checkedMetric = onRadioButtonClicked(metricButton);

        String feetText = feetEditText.getText().toString();
        String inchesText = inchesEditText.getText().toString();
        String weightText = weightEditText.getText().toString();

        //For the metric System:
        if (checkedMetric) {
            int meters = Integer.parseInt(feetText);
            float centimeters = Float.parseFloat(inchesText);
            double weight = Double.parseDouble(weightText);

            double totalHeightInMeters = (centimeters / 100) + meters;

            return weight / (totalHeightInMeters * totalHeightInMeters);
        }

        //For the imperial System:
        else {
            int feet = Integer.parseInt(feetText);
            int inches = Integer.parseInt(inchesText);
            double weight = Double.parseDouble(weightText);

            weight = weight * 0.45;

            int totalInches = (feet * 12) + inches;

            double heightInMeters = totalInches * 0.0254;

            //BMI formula
            return weight / (heightInMeters * heightInMeters);
        }
    }

    private void displayResult(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTextResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (bmi < 18.5) {
            // Display underweight
            fullResultString = bmiTextResult + " - You are underweight";
        } else if (bmi > 25) {
            // Display overweight
            fullResultString = bmiTextResult + " - You are overweight";
        } else {
            // Display healthy
            fullResultString = bmiTextResult + " - You are a healthy weight";
        }
        resultText.setText(fullResultString);
    }

    private void displayGuidance(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTextResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (maleButton.isChecked()) {
            //display boy guidance
            fullResultString = bmiTextResult + " - As you are under 18. please consult with your doctor for the healthy range for boys";
        } else if ((femaleButton.isChecked())) {
            fullResultString = bmiTextResult + " - As you are under 18. please consult with your doctor for the healthy range for girls";
        } else {
            fullResultString = bmiTextResult + " - As you are under 18. please consult with your doctor for the healthy range";
        }
        resultText.setText(fullResultString);
    }
}