package com.necatisozer.simplexmethodsolver;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.necatisozer.simplexmethodsolver.data.Equation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nested_scroll_view_main)
    NestedScrollView nestedScrollView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.linearlayout_main_focus)
    LinearLayoutCompat linearLayoutCompatFocus;

    @BindView(R.id.spinner_main_goal_maximize)
    Spinner spinnerGoalMaximize;

    @BindViews({R.id.edittext_main_goal_x1, R.id.edittext_main_goal_x2, R.id.edittext_main_goal_x3,
            R.id.edittext_main_goal_x4, R.id.edittext_main_goal_x5})
    List<EditText> editTextsConstantGoal;

    @BindViews({R.id.spinner_main_goal_x1_plus, R.id.spinner_main_goal_x2_plus,
            R.id.spinner_main_goal_x3_plus, R.id.spinner_main_goal_x4_plus,
            R.id.spinner_main_goal_x5_plus})
    List<Spinner> spinnersPlusGoal;

    @BindViews({R.id.edittext_main_e1x1, R.id.edittext_main_e1x2, R.id.edittext_main_e1x3,
            R.id.edittext_main_e1x4, R.id.edittext_main_e1x5})
    List<EditText> editTextsConstantE1;

    @BindViews({R.id.spinner_main_e1x1_plus, R.id.spinner_main_e1x2_plus, R.id.spinner_main_e1x3_plus,
            R.id.spinner_main_e1x4_plus, R.id.spinner_main_e1x5_plus})
    List<Spinner> spinnersPlusE1;

    @BindViews({R.id.edittext_main_e2x1, R.id.edittext_main_e2x2, R.id.edittext_main_e2x3,
            R.id.edittext_main_e2x4, R.id.edittext_main_e2x5})
    List<EditText> editTextsConstantE2;

    @BindViews({R.id.spinner_main_e2x1_plus, R.id.spinner_main_e2x2_plus, R.id.spinner_main_e2x3_plus,
            R.id.spinner_main_e2x4_plus, R.id.spinner_main_e2x5_plus})
    List<Spinner> spinnersPlusE2;

    @BindViews({R.id.edittext_main_e3x1, R.id.edittext_main_e3x2, R.id.edittext_main_e3x3,
            R.id.edittext_main_e3x4, R.id.edittext_main_e3x5})
    List<EditText> editTextsConstantE3;

    @BindViews({R.id.spinner_main_e3x1_plus, R.id.spinner_main_e3x2_plus, R.id.spinner_main_e3x3_plus,
            R.id.spinner_main_e3x4_plus, R.id.spinner_main_e3x5_plus})
    List<Spinner> spinnersPlusE3;

    @BindViews({R.id.edittext_main_e4x1, R.id.edittext_main_e4x2, R.id.edittext_main_e4x3,
            R.id.edittext_main_e4x4, R.id.edittext_main_e4x5})
    List<EditText> editTextsConstantE4;

    @BindViews({R.id.spinner_main_e4x1_plus, R.id.spinner_main_e4x2_plus, R.id.spinner_main_e4x3_plus,
            R.id.spinner_main_e4x4_plus, R.id.spinner_main_e4x5_plus})
    List<Spinner> spinnersPlusE4;

    @BindViews({R.id.edittext_main_e5x1, R.id.edittext_main_e5x2, R.id.edittext_main_e5x3,
            R.id.edittext_main_e5x4, R.id.edittext_main_e5x5})
    List<EditText> editTextsConstantE5;

    @BindViews({R.id.spinner_main_e5x1_plus, R.id.spinner_main_e5x2_plus, R.id.spinner_main_e5x3_plus,
            R.id.spinner_main_e5x4_plus, R.id.spinner_main_e5x5_plus})
    List<Spinner> spinnersPlusE5;

    @BindViews({R.id.spinner_main_e1_greater, R.id.spinner_main_e2_greater,
            R.id.spinner_main_e3_greater, R.id.spinner_main_e4_greater, R.id.spinner_main_e5_greater})
    List<Spinner>
            spinnersGreater;

    @BindViews({R.id.edittext_main_e1_result, R.id.edittext_main_e2_result,
            R.id.edittext_main_e3_result, R.id.edittext_main_e4_result, R.id.edittext_main_e5_result})
    List<EditText>
            editTextsResult;

    @BindView(R.id.textview_main_solution)
    TextView textViewsolution;

    List<List<Spinner>> spinnersPlus;
    List<List<EditText>> editTextsConstant;

    private List<Equation> equations;
    private List<Integer> pivotConstantIndexList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
    }

    private void initializeViews() {
        spinnersPlus = new ArrayList<>();
        spinnersPlus.add(spinnersPlusE1);
        spinnersPlus.add(spinnersPlusE2);
        spinnersPlus.add(spinnersPlusE3);
        spinnersPlus.add(spinnersPlusE4);
        spinnersPlus.add(spinnersPlusE5);

        editTextsConstant = new ArrayList<>();
        editTextsConstant.add(editTextsConstantE1);
        editTextsConstant.add(editTextsConstantE2);
        editTextsConstant.add(editTextsConstantE3);
        editTextsConstant.add(editTextsConstantE4);
        editTextsConstant.add(editTextsConstantE5);

        ArrayAdapter<CharSequence> spinnerAdapterPlus = ArrayAdapter.createFromResource(this,
                R.array.main_spinner_items_plus, android.R.layout.simple_spinner_item);
        spinnerAdapterPlus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (List<Spinner> spinnerList : spinnersPlus) {
            for (Spinner spinner : spinnerList) {
                spinner.setAdapter(spinnerAdapterPlus);
            }
        }

        ArrayAdapter<CharSequence> spinnerAdapterGreater = ArrayAdapter.createFromResource(this,
                R.array.main_spinner_items_greater, android.R.layout.simple_spinner_item);
        spinnerAdapterGreater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Spinner spinner : spinnersGreater) {
            spinner.setAdapter(spinnerAdapterGreater);
        }

        ArrayAdapter<CharSequence> spinnerAdapterMaximize = ArrayAdapter.createFromResource(this,
                R.array.main_spinner_items_maximize, android.R.layout.simple_spinner_item);
        spinnerAdapterMaximize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoalMaximize.setAdapter(spinnerAdapterMaximize);

        for (Spinner spinner : spinnersPlusGoal) {
            spinner.setAdapter(spinnerAdapterPlus);
        }
    }

    private void getInputs() {
        equations = new ArrayList<>();
        pivotConstantIndexList = new ArrayList<>();

        for (int i = 0; i < editTextsConstant.size(); i++) {
            List<Double> constants = new ArrayList<>();
            for (int j = 0; j < editTextsConstant.get(i).size(); j++) {
                String sConstant = editTextsConstant.get(i).get(j).getText().toString();
                Double constant = sConstant.isEmpty() ? 0.0 : Double.parseDouble(sConstant);
                Double multiply = spinnersPlus.get(i).get(j).getSelectedItemPosition() == 0 ? 1.0 : -1.0;
                constants.add(constant == 0 ? 0.0 : constant * multiply);
            }

            for (int k = 0; k < editTextsConstant.size() + 1; k++) {
                if (k == i) {
                    switch (spinnersGreater.get(i).getSelectedItemPosition()) {
                        case 0:
                            constants.add(1.0);
                            break;
                        case 1:
                            constants.add(-1.0);
                            break;
                    }
                } else {
                    constants.add(0.0);
                }
            }

            String sResult = editTextsResult.get(i).getText().toString();
            Double result = sResult.isEmpty() ? 0.0 : Double.parseDouble(sResult);

            equations.add(new Equation(constants, result));
        }

        for (int i = 0; i < editTextsConstant.size(); i++) {
            pivotConstantIndexList.add(5 + i);
        }

        List<Double> constantsResult = new ArrayList<>();
        for (EditText editTextConstant : editTextsConstantGoal) {
            String sConstant = editTextConstant.getText().toString();
            Double constant = sConstant.isEmpty() ? 0.0 : Double.parseDouble(sConstant);
            constantsResult.add(constant == 0 ? 0.0 : constant * (-1));
        }
        for (int i = 0; i < editTextsConstant.size(); i++) {
            constantsResult.add(0.0);
        }
        constantsResult.add(1.0);
        equations.add(new Equation(constantsResult, 0.0));
    }

    private void calculate() {
        while (ifContinueOrNot()) {
            // Find pivot column index by comparing objective function constants
            int numberOfEquation = equations.size();
            List<Double> constants = equations.get(numberOfEquation - 1).getConstants();
            int pivotColumnIndex = constants.indexOf(Collections.min(constants));

            // Find row index of pivot by comparing ratios
            int pivotRowIndex = 0;
            double minRatio = Double.POSITIVE_INFINITY;

            for (int i = 0; i < equations.size() - 1; i++) {
                double ratio =
                        equations.get(i).getResult() / equations.get(i).getConstants().get(pivotColumnIndex);
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRowIndex = i;
                }
            }

            // Divide pivot's row by pivot
            double pivot = equations.get(pivotRowIndex).getConstants().get(pivotColumnIndex);
            equations.get(pivotRowIndex).divideBy(pivot);

            // Divide other rows by their pivot column element
            for (int i = 0; i < equations.size(); i++) {
                if (i == pivotRowIndex) continue;
                double pivotColumnElement = equations.get(i).getConstants().get(pivotColumnIndex);
                equations.get(i)
                        .addMultipliedEquation(pivotColumnElement * (-1), equations.get(pivotRowIndex).clone());
            }
            pivotConstantIndexList.set(pivotRowIndex, pivotColumnIndex);
        }

        String solution = "";
        for (int i = 0; i < 5; i++) {
            solution += "x" + (i + 1) + " = ";
            int index = pivotConstantIndexList.indexOf(i);
            if (index > -1) {
                solution += equations.get(index).getResult();
            } else {
                solution += 0;
            }
            solution += ", ";
        }

        solution += "Solution: " + equations.get(equations.size() - 1).getResult();
        textViewsolution.setText(solution);
        nestedScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private boolean ifContinueOrNot() {
        int numberOfEquation = equations.size();
        List<Double> constants = equations.get(numberOfEquation - 1).getConstants();
        for (double constant : constants) {
            if (constant < 0) return true;
        }
        return false;
    }

    @OnClick(R.id.fab)
    void onCalculateClicked() {
        linearLayoutCompatFocus.requestFocus();
        getInputs();
        calculate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            linearLayoutCompatFocus.requestFocus();
            getInputs();
            calculate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
