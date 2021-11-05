package com.chris.mzork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chris.mzork.databinding.ActivityMainBinding;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bind;
    String mainText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.etTextEditor.setText("Welcome. Please write something here:\n");
        bind.etTextEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mainText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfAnswerIsValid(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("");
            }
        });
    }

    // Function that get an answer and return a valid text
    private String checkIfAnswerIsValid(String answer){
        // If diffText = "", we need to test if the last char from the saved text is \n
        String diffText = StringUtils.difference(mainText, answer);
        return "";
    }
}