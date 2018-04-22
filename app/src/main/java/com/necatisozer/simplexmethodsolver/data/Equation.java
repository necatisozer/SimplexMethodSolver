package com.necatisozer.simplexmethodsolver.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Equation implements Cloneable {
  private List<Double> constants;
  private Double result;

  public Equation() {
    constants = new ArrayList<>();
    result = 0.0;
  }

  public Equation(List<Double> constants, Double result) {
    this.constants = constants;
    this.result = result;
  }

  public List<Double> getConstants() {
    return constants;
  }

  public void setConstants(List<Double> constants) {
    this.constants = constants;
  }

  public Double getResult() {
    return result;
  }

  public void setResult(Double result) {
    this.result = result;
  }

  public void divideBy(double value) {
    if (value != 0) {
      for (int i = 0; i < constants.size(); i++) {
        constants.set(i, constants.get(i) / value);
      }
      result /= value;
    } else {
      Log.d(getClass().getSimpleName(), "Divide by 0");
    }
  }

  public void multiply(double value) {
    for (int i = 0; i < constants.size(); i++) {
      constants.set(i, constants.get(i) * value);
    }
    result *= value;
  }

  public void add(Equation equation) {
    for (int i = 0; i < constants.size(); i++) {
      constants.set(i, constants.get(i) + equation.getConstants().get(i));
    }
    result += equation.getResult();
  }

  public void addMultipliedEquation(double multiplyValue, Equation equation) {
    Equation mEquation = equation.clone();
    mEquation.multiply(multiplyValue);
    add(mEquation);
  }

  public Equation clone() {
    List<Double> constants = new ArrayList<>();
    for (double constant : this.constants) {
      constants.add(constant);
    }
    double result = this.result;
    return new Equation(constants, result);
  }
}
