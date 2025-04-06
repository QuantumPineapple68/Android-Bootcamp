package com.nachiket.calcitest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;
    private static final String SECRET_CODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = findViewById(R.id.display);

        // Number Buttons
        Button btn0 = findViewById(R.id.btn_0);
        btn0.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn0.getText().toString());
        });

        Button btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn1.getText().toString());
        });

        Button btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn2.getText().toString());
        });

        Button btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn3.getText().toString());
        });

        Button btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn4.getText().toString());
        });

        Button btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn5.getText().toString());
        });

        Button btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn6.getText().toString());
        });

        Button btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn7.getText().toString());
        });

        Button btn8 = findViewById(R.id.btn_8);
        btn8.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn8.getText().toString());
        });

        Button btn9 = findViewById(R.id.btn_9);
        btn9.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btn9.getText().toString());
        });

        Button btnDot = findViewById(R.id.btn_dot);
        btnDot.setOnClickListener(v -> {
            if (isNewOperation) {
                displayText.setText("");
                currentInput = "";
                isNewOperation = false;
            }
            numberPressed(btnDot.getText().toString());
        });

        // Operator Buttons
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> operatorPressed(btnAdd.getText().toString()));

        Button btnSubtract = findViewById(R.id.btn_subtract);
        btnSubtract.setOnClickListener(v -> operatorPressed(btnSubtract.getText().toString()));

        Button btnMultiply = findViewById(R.id.btn_multiply);
        btnMultiply.setOnClickListener(v -> operatorPressed(btnMultiply.getText().toString()));

        Button btnDivide = findViewById(R.id.btn_divide);
        btnDivide.setOnClickListener(v -> operatorPressed(btnDivide.getText().toString()));

        // Clear Button
        Button btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(v -> {
            currentInput = "";
            displayText.setText("0");
            isNewOperation = true;
        });

        // Equals Button with secret code check
        Button btnEquals = findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(v -> {
            if (currentInput.equals(SECRET_CODE)) {
                openVault();
            } else {
                calculateResult();
            }
        });
    }

    private void numberPressed(String digit) {
        // Avoid multiple decimals
        if (digit.equals(".") && currentInput.contains(".")) {
            return;
        }
        currentInput = currentInput + digit;
        displayText.setText(currentInput);
    }

    private void operatorPressed(String op) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = op;
            currentInput = "";
            isNewOperation = false;
            displayText.setText(op);
        }
    }

    private void calculateResult() {
        if (currentInput.isEmpty() || operator.isEmpty()) {
            return;
        }
        double secondNumber = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "×":
                result = firstNumber * secondNumber;
                break;
            case "÷":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    displayText.setText("Error");
                    currentInput = "";
                    operator = "";
                    isNewOperation = true;
                    return;
                }
                break;
        }
        // Remove trailing .0 for whole numbers
        String resultString = result % 1 == 0 ? String.valueOf((int) result) : String.valueOf(result);
        displayText.setText(resultString);
        currentInput = resultString;
        operator = "";
        isNewOperation = true;
    }

    private void openVault() {
        Intent intent = new Intent(MainActivity.this, VaultActivity.class);
        startActivity(intent);
        currentInput = "";
        displayText.setText("0");
        isNewOperation = true;
    }
}
