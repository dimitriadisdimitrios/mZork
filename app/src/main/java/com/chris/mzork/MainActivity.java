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

    //TODO: Comments here
    private ActivityMainBinding bind;
    //TODO: Comments here
    private String mainText;
    //TODO: Comments here
    private boolean enableToErase;
    //TODO: Comments here
    private boolean disableUpdatedText = true;
    //TODO: Comments here
    private boolean systemNeedToAnswer = true; // Global private variable to trigger the times that need system to answer
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Comments here
        super.onCreate(savedInstanceState);
        //TODO: Comments here
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        //TODO: Comments here
        setContentView(bind.getRoot());
        //TODO: Comments here
        bind.etTextEditor.setText("Welcome. Please write something here:\n");
        //first text appears on screen
        bind.etTextEditor.addTextChangedListener(new TextWatcher() { //This is to watch and control text that appears on screen
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Save text before changing
                if (disableUpdatedText) {
                    //TODO: Comments here and why!!!!
                    mainText = s.toString();
                }
                // Check if last character in text is '\n'
                enableToErase = !mainText.substring(mainText.length() - 1).equals("\n");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { //text while changing
                Log.i(TAG, "");
            }

            @Override
            public void afterTextChanged(Editable s) { //After the text changes
                //TODO: Comments here
                boolean isAnswerEmpty = checkIfAnswerIsEmpty(s.toString());
                // Call function to check if need to block erasing
                boolean needToRevertText = checkToBlockErase(s.toString());
                //TODO: Comments here WHY we implement it
                disableUpdatedText = true;
                //call function to revert text changes
                if (needToRevertText || isAnswerEmpty) { //This is to appear the same text if answer is empty
                    //TODO: Comments here
                    setOldTextToEditText();
                } else {
                    //TODO: Comments here
                    appendAutoAnswer(s.toString());
                }
            }
        });
    }

    /**
     * Function that check if user should be able to erase text
     * @param textToCheck
     * @return
     */
    private boolean checkToBlockErase(String textToCheck) {
        // Compare length of text before and returns if text need erase or not
        return textToCheck.length() < mainText.length() && !enableToErase;
    }

    /**
     * Function that check if the answer is empty
     * @param textToCheck
     * @return
     */
    private boolean checkIfAnswerIsEmpty(String textToCheck) {
        //TODO: Comments here
        String twoLastChar = textToCheck.substring(mainText.length() - 1);
        //TODO: Comments here
        return twoLastChar.equals("\n\n");
    }

    /**
     * revert text if user gave empty answer
     */
    private void setOldTextToEditText() {
        //TODO: Comments here
        mainText = mainText.substring(0, mainText.length() - 1) + "\n"; //check if last character is new line
        //TODO: Comments here
        disableUpdatedText = false;
        //TODO: Comments here
        systemNeedToAnswer = false;
        // Set editText with text before erasing
        bind.etTextEditor.setText(mainText);
        // Set the position of cursor to be always at the end of the last line
        bind.etTextEditor.setSelection(mainText.length());
    }

    /**
     * Function that is responsible for auto-answering
     *
     * @param textUserAdded Text that user added
     */
    private void appendAutoAnswer(String textUserAdded) {
        //TODO: Comments here
        if (textUserAdded.length() > mainText.length()) {
            //TODO: Comments here
            String lastCharOfNewText = textUserAdded.substring(textUserAdded.length() - 1);
            //TODO: Comments here
            if (lastCharOfNewText.equals("\n") && systemNeedToAnswer) {
                //TODO: Comments here
                systemNeedToAnswer = false;
                String newTextWithAnswer = textUserAdded + "Add a new answer bellow:\n";
                //TODO: Comments here
                bind.etTextEditor.setText(newTextWithAnswer);
                //TODO: Comments here
                bind.etTextEditor.setSelection(newTextWithAnswer.length());
            } else {
                //TODO: Comments here
                systemNeedToAnswer = true;
            }
        }
    }

    //prevent the app to close when back button pressed
    @Override
    public void onBackPressed() {
    }
}