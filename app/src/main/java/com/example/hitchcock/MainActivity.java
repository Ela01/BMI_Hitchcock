package com.example.hitchcock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;

import java.text.DecimalFormat;
public class MainActivity extends AppCompatActivity {
    // Class Variables (highlighted below in purple,) are also called fields (in java)
    TextView resultText;
    Button calculateButton;
    Button calculateButtonRmr;
    Button calculateButtonTarget;
    private RadioButton maleButton;
    private RadioButton femaleButton;
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
        metricButton = findViewById(R.id.radiobutton_metric);
        feetEditText = findViewById(R.id.edit_text_feet);
        inchesEditText = findViewById(R.id.edit_text_inches);
        weightEditText = findViewById(R.id.edit_text_weight);
        ageEditText = findViewById(R.id.edit_text_age);
        calculateButton = findViewById(R.id.button_calculate);
        calculateButtonRmr = findViewById(R.id.button_calculate_rmr);
        calculateButtonTarget = findViewById(R.id.button_calculate_target);
    }
    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(v -> {

            boolean checkMissingInput = checkForEmptyInput();
            if (checkMissingInput) {
                String alertPopUp = "Please enter in the missing values";
                Toast.makeText(MainActivity.this, alertPopUp, Toast.LENGTH_LONG).show();
            } else {
                double bmiResult = calculateB();
                if (!checkIfUnderage()) displayResult(bmiResult);
                else {
                    displayGuidance(bmiResult);
                }
            }
        });
        calculateButtonRmr.setOnClickListener(v -> {

            boolean checkMissingInput = checkForEmptyInput();
            if (checkMissingInput) {
                String alertPopUp = "Please enter in the missing values";
                Toast.makeText(MainActivity.this, alertPopUp, Toast.LENGTH_LONG).show();
            } else {
                double rmrResult = calculateR();
                displayResultRmr(rmrResult);
            }
            });
            calculateButtonTarget.setOnClickListener(v -> {

            Intent calculateTargetActivityIntent = new Intent(MainActivity.this, CalculateTargetActivity.class);

            startActivity(calculateTargetActivityIntent);
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
    private double calculateB() {
        boolean checkedMetric = metricButton.isChecked();
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
    private double calculateR() {
        //The Mifflin-St Jeor equation, created in the 1990s, provided an alternative and more valid estimate of RMR.
        //The equations for males and females are:
        //Men: (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
        //Women: (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161
        boolean checkedMetric = metricButton.isChecked();
        boolean female = femaleButton.isChecked();
        String ageText = ageEditText.getText().toString();
        String metersOrFeetText = feetEditText.getText().toString();
        String centimetersOrInchesText = inchesEditText.getText().toString();
        String weightText = weightEditText.getText().toString();
        int age = Integer.parseInt(ageText);
        //For the metric System:
        int meters = Integer.parseInt(metersOrFeetText);
        int centimeters = Integer.parseInt(centimetersOrInchesText);
        double weight = Double.parseDouble(weightText);
        if (checkedMetric) {
            double totalHeightInCentimeters = (centimeters) + (meters * 100);
            if (female) {
                return (10 * weight) + (6.25 * totalHeightInCentimeters) - (5 * age) - 161;
            }
            else {
                return (10 * weight) + (6.25 * totalHeightInCentimeters) - (5 * age) + 5;
            }
        }
        else {
            double totalHeightInInches = (meters * 12) + centimeters;
            double totalHeightInCentimeters = totalHeightInInches * 2.54;
            double weightInKg = weight * 0.45;
            if (female) {
                return (10 * weightInKg) + (6.25 * totalHeightInCentimeters) - (5 * age) -161;
            }
            else {
                return (10 * weightInKg) + (6.25 * totalHeightInCentimeters) - (5 * age) + 5;
            }
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
    private void displayResultRmr(double rmr){
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String rmrTextResult = myDecimalFormatter.format(rmr);
        String fullResultString;
        if (maleButton.isChecked()) {
            //display boy guidance
            fullResultString = rmrTextResult + " kcal - is your resting metabolic rate";
        } else if ((femaleButton.isChecked())) {
            fullResultString = rmrTextResult + " kcal - is your resting metabolic rate";
        } else {
            fullResultString = rmrTextResult + " kcal - is your resting metabolic rate";
        }
        resultText.setText(fullResultString);
    }
}