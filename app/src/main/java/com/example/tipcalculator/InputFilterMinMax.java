package com.example.tipcalculator;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {
    private double totalMin;
    private double totalMax;

    private int tipMin;
    private int tipMax;

    public InputFilterMinMax(double min, double max) {
        this.totalMin = min;
        this.totalMax = max;
    }

    public InputFilterMinMax(int min, int max) {
        this.tipMin = min;
        this.tipMax = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //noinspection EmptyCatchBlock
        try {
            double input = Double.parseDouble(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
            if (isInRange(totalMin, totalMax, input))
                return null;
        } catch (NumberFormatException nfe) { }
        try {
            int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
            if (isInRange(tipMin, tipMax, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
