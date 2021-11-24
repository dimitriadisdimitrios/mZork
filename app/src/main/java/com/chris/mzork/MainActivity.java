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
    private boolean enableToErase;
    private boolean disableUpdatedText = true;
    private boolean systemNeedToAnswer = true; // Global private variable to trigger the times that need system to answer
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
                if (disableUpdatedText){
                    mainText = s.toString();
                }
                // Check if last character in text is '\n'
                enableToErase = !mainText.substring(mainText.length() - 1).equals("\n");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "");
            }

            @Override
            public void afterTextChanged(Editable s) {

                boolean isAnswerEmpty = checkIfAnswerIsEmpty(s.toString());
                // Call function to check if need to block erasing
                boolean needToRevertText = checkToBlockErase(s.toString());
                disableUpdatedText = true;
                if (needToRevertText || isAnswerEmpty){
                    setOldTextToEditText();
                } else {
                    appendAutoAnswer(s.toString());
                }
            }
        });
    }

    // Function that check if user should be able to erase text
    private boolean checkToBlockErase(String textToCheck){
        // Compare length of text before and returns if text need erase or not
        return textToCheck.length() < mainText.length() && !enableToErase;
    }

    private boolean checkIfAnswerIsEmpty(String textToCheck){
        String twoLastChar = textToCheck.substring(mainText.length() - 1);
        return twoLastChar.equals("\n\n");
    }

    private void setOldTextToEditText(){
        mainText = mainText.substring(0, mainText.length() - 1) + "\n";
        disableUpdatedText = false;
        systemNeedToAnswer = false;
        // Set editText with text before erasing
        bind.etTextEditor.setText(mainText);
        // Set the position of cursor to be always at the end of the last line
        bind.etTextEditor.setSelection(mainText.length());
    }

    /** Function that is responsible for auto-answering
     *
     * @param textUserAdded Text that user added
     */
    private void appendAutoAnswer(String textUserAdded){
        if(textUserAdded.length() > mainText.length()){
            String lastCharOfNewText = textUserAdded.substring(textUserAdded.length() -1);
            if (lastCharOfNewText.equals("\n") && systemNeedToAnswer){
                systemNeedToAnswer = false;
                String newTextWithAnswer = textUserAdded + "Add a new answer bellow:\n";
                bind.etTextEditor.setText(newTextWithAnswer);
                bind.etTextEditor.setSelection(newTextWithAnswer.length());
            } else {
                systemNeedToAnswer = true;
            }
        }
    }
}