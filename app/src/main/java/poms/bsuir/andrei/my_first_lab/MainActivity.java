package poms.bsuir.andrei.my_first_lab;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    private TextView displayPrimary;
    private HorizontalScrollView hsv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        displayPrimary = findViewById(R.id.display_primary);
        hsv = findViewById(R.id.display_hsv);
        TextView[] digits = {
                findViewById(R.id.button_0),
                findViewById(R.id.button_1),
                findViewById(R.id.button_2),
                findViewById(R.id.button_3),
                findViewById(R.id.button_4),
                findViewById(R.id.button_5),
                findViewById(R.id.button_6),
                findViewById(R.id.button_7),
                findViewById(R.id.button_8),
                findViewById(R.id.button_9)};
        for (int i = 0; i < digits.length; i++) {
            final String id = (String) digits[i].getText();
            digits[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculator.digit(Character.toString(id.charAt(0)));
                }
            });
        }
        TextView[] buttons = {
                findViewById(R.id.button_sin),
                findViewById(R.id.button_cos),
                findViewById(R.id.button_tan),
                findViewById(R.id.button_ln),
                findViewById(R.id.button_log),
                findViewById(R.id.button_factorial),
                findViewById(R.id.button_pi),
                findViewById(R.id.button_e),
                findViewById(R.id.button_exponent),
                findViewById(R.id.button_start_parenthesis),
                findViewById(R.id.button_end_parenthesis),
                findViewById(R.id.button_square_root),
                findViewById(R.id.button_add),
                findViewById(R.id.button_subtract),
                findViewById(R.id.button_multiply),
                findViewById(R.id.button_divide),
                findViewById(R.id.button_decimal),
                findViewById(R.id.button_equals),
                findViewById(R.id.button_arccos),
                findViewById(R.id.button_arcsin),
                findViewById(R.id.button_arccos),
                findViewById(R.id.button_arctan),
                findViewById(R.id.button_clr)};
        for (int i = 0; i < buttons.length; i++) {
            final String id = (String) buttons[i].getText();
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id.equals("sin"))
                        calculator.opNum("sin(rad(");
                    if (id.equals("cos"))
                        calculator.opNum("cos(rad(");
                    if (id.equals("tan"))
                        calculator.opNum("tan(rad(");
                    if (id.equals("arcsin"))
                        calculator.opNum("arcsin(");
                    if (id.equals("arccos"))
                        calculator.opNum("arccos(");
                    if (id.equals("arctan"))
                        calculator.opNum("arctan(");
                    if (id.equals("clr"))
                        calculator.clear();
                    if (id.equals("ln"))
                        calculator.opNum("ln(");
                    if (id.equals("log"))
                        calculator.opNum("log10(");
                    if (id.equals("!"))
                        calculator.numOp("!");
                    if (id.equals("π"))
                        calculator.num("pi");
                    if (id.equals("e"))
                        calculator.num("e");
                    if (id.equals("^"))
                        calculator.numOpNum("^");
                    if (id.equals("("))
                        calculator.parenthesisLeft();
                    if (id.equals(")"))
                        calculator.parenthesisRight();
                    if (id.equals("√"))
                        calculator.opNum("sqrt(");
                    if (id.equals("÷"))
                        calculator.numOpNum("/");
                    if (id.equals("×"))
                        calculator.numOpNum("*");
                    if (id.equals("−"))
                        calculator.numOpNum("-");
                    if (id.equals("+"))
                        calculator.numOpNum("+");
                    if (id.equals("."))
                        calculator.decimal();
                    if (id.equals("=") && !getText().equals(""))
                        calculator.equal();
                }
            });
        }
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.delete();
            }
        });
        findViewById(R.id.button_delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!displayPrimary.getText().toString().trim().equals("")) {
                    final View displayOverlay = findViewById(R.id.display_overlay);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Animator circle = ViewAnimationUtils.createCircularReveal(
                                displayOverlay,
                                displayOverlay.getMeasuredWidth() / 2,
                                displayOverlay.getMeasuredHeight(),
                                0,
                                (int) Math.hypot(displayOverlay.getWidth(), displayOverlay.getHeight()));
                        circle.setDuration(300);
                        circle.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                calculator.setText("");
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                calculator.setText("");
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        });
                        ObjectAnimator fade = ObjectAnimator.ofFloat(displayOverlay, "alpha", 0f);
                        fade.setInterpolator(new DecelerateInterpolator());
                        fade.setDuration(200);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playSequentially(circle, fade);
                        displayOverlay.setAlpha(1);
                        animatorSet.start();
                    } else
                        calculator.setText("");
                }
                return false;
            }
        });
        calculator = new Calculator(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText(getText());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("text", getText());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setText(savedInstanceState.getString("text"));
    }

    public String getText() {
        return calculator.getText();
    }

    public void setText(String s) {
        calculator.setText(s);
    }

    public void displayPrimaryScrollLeft(String val) {
        displayPrimary.setText(val);
        ViewTreeObserver vto = hsv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                hsv.fullScroll(View.FOCUS_LEFT);
            }
        });
    }

    public void displayPrimaryScrollRight(String val) {
        displayPrimary.setText(val);
        ViewTreeObserver vto = hsv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                hsv.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }


    private String formatToDisplayMode(String s) {
        return s.replace("/", "÷").replace("*", "×").replace("-", "−")
                .replace("ln(", "ln(").replace("log(", "log(").replace("sqrt(", "√(")
                .replace("sin", "sin(rad(").replace("cos", "cos(rad(").replace("tan", "tan(rad(")
                .replace(" ", "").replace("∞", "Infinity").replace("NaN", "Undefined");
    }
}