package edu.calvin.cs262.pjh26.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Calculate(View view) {
        EditText textValue1 = (EditText) findViewById(R.id.editText);
        EditText textValue2 = (EditText) findViewById(R.id.editText2);
        Integer value1 = parseInt(textValue1.getText().toString());
        Integer value2 = parseInt(textValue2.getText().toString());

        Spinner operator = (Spinner) findViewById(R.id.spinner3);
        String operStr = operator.getSelectedItem().toString();

        int output = 0;

        if (operStr.equals("+")) {
            output = value1 + value2;
        } else if (operStr.equals("-")) {
            output = value1 - value2;
        } else if (operStr.equals("*")) {
            output = value1 * value2;
        } else if (operStr.equals("/")) {
            output = value1 / value2;
        }

        TextView outputTextView = (TextView) findViewById(R.id.textView5);

        outputTextView.setText(Integer.toString(output));
    }
}
