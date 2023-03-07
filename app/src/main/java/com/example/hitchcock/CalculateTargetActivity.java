package com.example.hitchcock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalculateTargetActivity extends AppCompatActivity {

    TextView resultTextTarget;
    Button estimateTargetButton;
    private RadioButton maleButtonTarget;
    private RadioButton femaleButtonTarget;
    private RadioButton imperialButtonTarget;
    private RadioButton metricButtonTarget;
    private RadioButton sedentaryButtonTarget;
    private RadioButton slightlyActiveButtonTarget;
    private RadioButton moderatelyActiveButtonTarget;
    private RadioButton veryActiveButtonTarget;
    private RadioButton extremelyActiveButtonTarget;
    private EditText feetMetersEditText;
    private EditText inchesCmEditText;
    private EditText weightTargetEditText;
    private EditText ageTargetEditText;
    private EditText desiredWeightLossEditText;
    private EditText daysToAchieveTargetEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_target);

    }

    private void findViews() {

        resultTextTarget = findViewById(R.id.text_view_calculate_target);
        estimateTargetButton = findViewById(R.id.button_estimate_target);
        maleButtonTarget = findViewById(R.id.radiobutton_male_target);
        femaleButtonTarget = findViewById(R.id.radiobutton_female_target);
        imperialButtonTarget = findViewById(R.id.radiobutton_imperial_target);
        metricButtonTarget = findViewById(R.id.radiobutton_metric_target);
        sedentaryButtonTarget = findViewById(R.id.radio_button_sedentary);
        slightlyActiveButtonTarget = findViewById(R.id.radio_button_slightly_active);
        moderatelyActiveButtonTarget = findViewById(R.id.radio_button_moderately_active);
        veryActiveButtonTarget = findViewById(R.id.radio_button_very_active);
        extremelyActiveButtonTarget = findViewById(R.id.radio_button_extremely_active);
        feetMetersEditText = findViewById(R.id.edit_text_feet_meters_target);
        inchesCmEditText = findViewById(R.id.edit_text_inches_cm_target);
        weightTargetEditText = findViewById(R.id.edit_text_weight_target);
        ageTargetEditText = findViewById(R.id.edit_text_age_target);
        desiredWeightLossEditText = findViewById(R.id.edit_text_expected_weight_loss);
        daysToAchieveTargetEditText = findViewById(R.id.edit_text_time_to_achieve_loss);
    }

    private double calculateBmr(){
        boolean checkedMetric = metricButtonTarget.isChecked();
        boolean female = femaleButtonTarget.isChecked();
        String ageText = ageTargetEditText.getText().toString();
        String metersOrFeetText = feetMetersEditText.getText().toString();
        String centimetersOrInchesText = inchesCmEditText.getText().toString();
        String weightText = weightTargetEditText.getText().toString();
        int age = Integer.parseInt(ageText);
        int meters = Integer.parseInt(metersOrFeetText);
        int centimeters = Integer.parseInt(centimetersOrInchesText);
        double weight = Double.parseDouble(weightText);

        if (checkedMetric) {
            double totalHeightInCentimeters = (centimeters) + (meters * 100);
            if (female) {
                return 655.1 + (9.563 * weight) + (1.850 * totalHeightInCentimeters) - (4.676 * age);
            }
            else {
                return 66.5 + (13.75 * weight) + (5.003 * totalHeightInCentimeters) - (6.75 * age);
            }
        }
        else {
            double totalHeightInInches = (meters * 12) + centimeters;
            double totalHeightInCentimeters = totalHeightInInches * 2.54;
            double weightInKg = weight * 0.45;
            if (female) {
                return 655.1 + (9.563 * weightInKg) + (1.850 * totalHeightInCentimeters) - (4.676 * age);
            }
            else {
                return 66.5 + (13.75 * weightInKg) + (5.003 * totalHeightInCentimeters) - (6.75 * age);
            }
        }
    }
    private double calculateBmrWithActivityExpenditure(){
        double bMr = calculateBmr();
        boolean sedentary = sedentaryButtonTarget.isChecked();
        boolean slightlyActive = slightlyActiveButtonTarget.isChecked();
        boolean moderatelyActive = moderatelyActiveButtonTarget.isChecked();
        boolean veryActive = veryActiveButtonTarget.isChecked();
        //boolean extremelyActive = extremelyActiveButtonTarget.isChecked();

        if (sedentary){
            return bMr * 1.2;
        } else if (slightlyActive) {
            return bMr * 1.37;
        } else if (moderatelyActive) {
            return bMr * 1.55;
        } else if (veryActive) {
            return bMr * 1.725;
        }
        else {
            return bMr * 1.9;
        }
    }
}


