package com.necatisozer.simplexmethodsolver;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.necatisozer.simplexmethodsolver.data.Equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.linearlayout_main_focus) LinearLayoutCompat linearLayoutCompatFocus;

  @BindViews({R.id.edittext_main_e1x1, R.id.edittext_main_e1x2, R.id.edittext_main_e1x3,
      R.id.edittext_main_e1x4, R.id.edittext_main_e1x5})
  List<EditText> editTextsConstantE1;

  @BindViews({R.id.spinner_main_e1x1_plus, R.id.spinner_main_e1x2_plus, R.id.spinner_main_e1x3_plus,
      R.id.spinner_main_e1x4_plus, R.id.spinner_main_e1x5_plus})
  List<Spinner> spinnersPlusE1;

  @BindViews({R.id.spinner_main_e2x1_plus, R.id.spinner_main_e2x2_plus, R.id.spinner_main_e2x3_plus,
      R.id.spinner_main_e2x4_plus, R.id.spinner_main_e2x5_plus})
  List<Spinner> spinnersPlusE2;

  @BindViews({R.id.edittext_main_e2x1, R.id.edittext_main_e2x2, R.id.edittext_main_e2x3,
      R.id.edittext_main_e2x4, R.id.edittext_main_e2x5})
  List<EditText> editTextsConstantE2;

  @BindViews({R.id.spinner_main_e1_greater, R.id.spinner_main_e2_greater}) List<Spinner>
      spinnersGreater;

  @BindViews({R.id.edittext_main_e1_result, R.id.edittext_main_e2_result}) List<EditText>
      editTextsResult;

  List<List<Spinner>> spinnersPlus;
  List<List<EditText>> editTextsConstant;

  private List<Equation> equations;
  private List<Integer> pivotConstants;

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

    editTextsConstant = new ArrayList<>();
    editTextsConstant.add(editTextsConstantE1);
    editTextsConstant.add(editTextsConstantE2);

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
  }

  private void getInputs() {
    equations = new ArrayList<>();
    pivotConstants = new ArrayList<>();

    for (int i = 0; i < editTextsConstant.size(); i++) {
      List<Double> constants = new ArrayList<>();
      for (int j = 0; j < editTextsConstant.get(i).size(); j++) {
        String sConstant = editTextsConstant.get(i).get(j).getText().toString();
        Double constant = sConstant.isEmpty() ? 0.0 : Double.parseDouble(sConstant);
        Double multiply = spinnersPlus.get(i).get(j).getSelectedItemPosition() == 0 ? 1.0 : -1.0;
        constants.add(constant * multiply);
      }

      for (int k = 0; k < editTextsConstant.size(); k++) {
        constants.add(k == i ? 1.0 : 0.0);
      }

      String sResult = editTextsResult.get(i).getText().toString();
      Double result = sResult.isEmpty() ? 0.0 : Double.parseDouble(sResult);

      equations.add(new Equation(constants, result));
    }
  }

  private void printEquations() {
    String sEquation = " \n";

    for (Equation equation : equations) {
      sEquation += "\n";
      sEquation +=
          "Equation " + equations.indexOf(equation) + " = " + equation.getConstants().toString();
      sEquation += " Result = " + equation.getResult();
    }

    Log.d(getClass().getSimpleName(), sEquation);
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
      pivotConstants.set(pivotRowIndex, pivotColumnIndex);
      printEquations();
    }
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
