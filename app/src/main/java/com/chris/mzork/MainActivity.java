package com.chris.mzork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.chris.mzork.databinding.ActivityMainBinding;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bind;
    private String mainText;
    private Boolean enableToErase;
    private Boolean systemNeedToAnswer = true; // Global private variable to trigger the times that need system to answer
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.etTextEditor.setText("Welcome. Please write something here:\n");
        bind.etTextEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Save text before changing
                mainText = s.toString();
                // Check if last character in text is '\n'
                enableToErase = !mainText.substring(mainText.length() - 1).equals("\n");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Call function to check if need to block erasing
                checkToBlockErase(s.toString());

                if(s.toString().length() > mainText.length()){
                    String lastCharOfNewText = s.toString().substring(s.toString().length() -1);
                    if (lastCharOfNewText.equals("\n") && systemNeedToAnswer){
                        systemNeedToAnswer = false;
                        //TODO: The answers must be added dynamically
                        bind.etTextEditor.setText("Add a new answer bellow:\n");
                    } else {
                        systemNeedToAnswer = true;
                    }
                }
            }
        });
    }

    // Function that check if user should be able to erase text
    private void checkToBlockErase(String textToCheck){
        // Compare length of text before and after of changing to see if user is able to delete a character
        if (textToCheck.length() < mainText.length() && !enableToErase) {
            // Set editText with text before erasing
            bind.etTextEditor.setText(mainText);
            // Set the position of cursor to be always at the end of the last line
            bind.etTextEditor.setSelection(mainText.length()+1);
        }
    }
}