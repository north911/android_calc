package poms.bsuir.andrei.my_first_lab;

import org.mariuszgromada.math.mxparser.Expression;

public class Calculator {
    private MainActivity context;
    private Equation eq;
    private Expression e;

    public Calculator(MainActivity context) {
        this.context = context;
        this.eq = new Equation();
        e = new Expression();
    }

    public String getText() {
        return eq.getText();
    }

    public void setText(String text) {
        eq.setText(text);
    }

    public void decimal() {
        if (!Character.isDigit(eq.getLastChar()))
            digit("0");
        if (!eq.getLast().contains("."))
            eq.attachToLast(".");
        context.displayPrimaryScrollRight(getText());
    }

    public void delete() {

        if (eq.getLast().length() > 1 && (eq.isRawNumber(0) || eq.getLast().charAt(0) == '-'))
            eq.detachFromLast();
        else
            eq.removeLast();
        context.displayPrimaryScrollRight(getText());
    }

    public void digit(String digit) {
        if (eq.isRawNumber(0))
            eq.attachToLast(digit);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + digit);
        }
        context.displayPrimaryScrollRight(getText());
    }

    public void clear() {
        eq.clear();
        context.displayPrimaryScrollRight(getText());
    }

    public void equal() {
        String s;
        try {
            e.setExpressionString(context.getText());
            s = Double.toString(e.calculate());
        } catch (Exception e) {
            s = "Error";
        }
        context.displayPrimaryScrollLeft(s);
        eq = new Equation();
        if (!s.contains("∞") && !s.contains("Infinity") && !s.contains("NaN"))
            eq.add(s);
    }

    public void num(String number) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.isRawNumber(0) && eq.getLastChar() == '-')
            eq.attachToLast(number);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + number);
        }
        context.displayPrimaryScrollRight(getText());
    }

    public void numOp(String operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.isNumber(0))
            eq.add("" + operator);
        context.displayPrimaryScrollRight(getText());
    }

    public void numOpNum(String operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (!operator.equals("-") || (eq.isOperator(0) && eq.isStartCharacter(1)))
            while (eq.isOperator(0))
                eq.removeLast();
        if (operator.equals("-") || !eq.isStartCharacter(0))
            eq.add("" + operator);
        context.displayPrimaryScrollRight(getText());
    }

    public void opNum(String operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.getLast().equals("-"))
            eq.attachToLast(operator);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + operator);
        }
        context.displayPrimaryScrollRight(getText());
    }

    public void parenthesisLeft() {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.getLast().equals("-") && eq.isRawNumber(0))
            eq.attachToLast("(");
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("(");
        }
        context.displayPrimaryScrollRight(getText());
    }

    public void parenthesisRight() {
        if (eq.numOf('s') + eq.numOf('c') + eq.numOf('t') + eq.numOf('n') + eq.numOf('l')
                + eq.numOf('√') + eq.numOf('(') > eq.numOf(')') && eq.isNumber(0))
            eq.add(")");
        context.displayPrimaryScrollRight(getText());
    }
}
