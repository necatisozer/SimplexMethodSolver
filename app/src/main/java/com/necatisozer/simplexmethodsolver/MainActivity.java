package com.necatisozer.simplexmethodsolver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.necatisozer.simplexmethodsolver.data.Equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  List<Equation> equations;
  List<Integer> pivotConstants;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    equations = new ArrayList<>();
    pivotConstants = new ArrayList<>();

    equations.add(new Equation(Arrays.asList(2.0, 3.0, 2.0, 1.0, 0.0, 0.0), 1000.0));
    pivotConstants.add(3);
    equations.add(new Equation(Arrays.asList(1.0, 1.0, 2.0, 0.0, 1.0, 0.0), 800.0));
    pivotConstants.add(4);
    equations.add(new Equation(Arrays.asList(-7.0, -8.0, -10.0, 0.0, 0.0, 1.0), 0.0));
    printEquations();
    calculate();
    printEquations();
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
            .addMultipliedEquation(pivotColumnElement * (-1), equations.get(pivotRowIndex));
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
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
