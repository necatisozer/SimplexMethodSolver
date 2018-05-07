package com.necatisozer.simplexmethodsolver;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

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
    TextView textViewSolution;

    List<List<Spinner>> spinnersPlus;
    List<List<EditText>> editTextsConstant;

    private LinearObjectiveFunction objectiveFunction;
    private List<LinearConstraint> constraints;
    private GoalType goalType;

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
        double[] objectiveFunctionCoefficients = new double[editTextsConstantGoal.size()];

        for (int i = 0; i < editTextsConstantGoal.size(); i++) {
            String sCoefficient = editTextsConstantGoal.get(i).getText().toString();
            Double coefficient = sCoefficient.isEmpty() ? 0.0 : Double.parseDouble(sCoefficient);
            Double multiply = spinnersPlusGoal.get(i).getSelectedItemPosition() == 0 ? 1.0 : -1.0;
            objectiveFunctionCoefficients[i] = coefficient == 0 ? 0.0 : coefficient * multiply;
        }

        objectiveFunction = new LinearObjectiveFunction(objectiveFunctionCoefficients, 0);

        constraints = new ArrayList<>();

        for (int i = 0; i < editTextsConstant.size(); i++) {
            double[] coefficients = new double[editTextsConstant.get(i).size()];

            for (int j = 0; j < editTextsConstant.get(i).size(); j++) {
                String sCoefficient = editTextsConstant.get(i).get(j).getText().toString();
                Double coefficient = sCoefficient.isEmpty() ? 0.0 : Double.parseDouble(sCoefficient);
                Double multiply = spinnersPlus.get(i).get(j).getSelectedItemPosition() == 0 ? 1.0 : -1.0;
                coefficients[j] = coefficient == 0 ? 0.0 : coefficient * multiply;
            }

            Relationship relationship = spinnersGreater.get(i).getSelectedItemPosition() == 0 ? Relationship.LEQ : Relationship.GEQ;

            String sResult = editTextsResult.get(i).getText().toString();
            Double result = sResult.isEmpty() ? 0.0 : Double.parseDouble(sResult);

            constraints.add(new LinearConstraint(coefficients, relationship, result));
        }

        goalType = spinnerGoalMaximize.getSelectedItemPosition() == 0 ? GoalType.MAXIMIZE : GoalType.MINIMIZE;
    }

    private void calculate() {
        getInputs();

        String sSolution = "";

        try {
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(new MaxIter(editTextsConstantGoal.size()), objectiveFunction, new LinearConstraintSet(constraints), goalType, new NonNegativeConstraint(true));

            for (int i = 0; i < editTextsConstantGoal.size(); i++) {
                sSolution += "x" + (i + 1) + " = " + formatDecimal(solution.getPoint()[i]) + "\n";
            }

            sSolution += "Solution: " + formatDecimal(solution.getValue());
        } catch (Throwable exception) {
            handleError(exception);
        }

        textViewSolution.setText(sSolution);
        appBarLayout.setExpanded(false);
        nestedScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private String formatDecimal(double number) {
        if (number == Math.abs(number)) return String.valueOf((int) number);
        String text = Double.toString(Math.abs(number));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;
        return decimalPlaces > 2 ? String.format(Locale.getDefault(), "%.2f", number) : Double.toString(number);
    }

    private void handleError(Throwable error) {
        if (error instanceof UnboundedSolutionException)
            showSnackBar("Unbounded Solution");
        else if (error instanceof NoFeasibleSolutionException)
            showSnackBar("No Feasible Solution");
        else
            showSnackBar(error.getClass().getSimpleName());
    }

    private void showSnackBar(String text) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT).show();
    }

    private void reset() {
        for (List<EditText> editTexts : editTextsConstant) {
            for (EditText editText : editTexts)
                editText.setText("");
        }

        for (EditText editText : editTextsResult)
            editText.setText("");

        for (EditText editText : editTextsConstantGoal)
            editText.setText("");

        for (List<Spinner> spinners : spinnersPlus) {
            for (Spinner spinner : spinners)
                spinner.setSelection(0);
        }

        for (Spinner spinner : spinnersPlusGoal)
            spinner.setSelection(0);

        for (Spinner spinner : spinnersGreater)
            spinner.setSelection(0);

        spinnerGoalMaximize.setSelection(0);

        textViewSolution.setText("");
        appBarLayout.setExpanded(true);
        nestedScrollView.fullScroll(View.FOCUS_UP);
    }


    @OnClick(R.id.fab)
    void onCalculateClicked() {
        linearLayoutCompatFocus.requestFocus();
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
        if (id == R.id.action_calculate) {
            linearLayoutCompatFocus.requestFocus();
            calculate();
            return true;
        } else if (id == R.id.action_reset) {
            linearLayoutCompatFocus.requestFocus();
            reset();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
