package ssd.project.mickeycj.todosapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ssd.project.mickeycj.todosapp.R;

/**
 * Created by user on 4/6/60.
 */

public class OptionsDialog extends Dialog {

    private TextView optionsTitleTextView;
    private Button firstOptionButton, secondOptionButton, thirdOptionButton, cancelOptionButton;

    public OptionsDialog(@NonNull Context context) {
        super(context);

        initViewHolders();

        setCanceledOnTouchOutside(false);
    }

    private void initViewHolders() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_options);

        optionsTitleTextView = (TextView) findViewById(R.id.textview_options_title);

        firstOptionButton = (Button) findViewById(R.id.button_option_1);
        secondOptionButton = (Button) findViewById(R.id.button_option_2);
        thirdOptionButton = (Button) findViewById(R.id.button_option_3);
        cancelOptionButton = (Button) findViewById(R.id.button_option_cancel);
        cancelOptionButton.setOnClickListener(onCancelOptionClickListener);
    }

    private View.OnClickListener onCancelOptionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public OptionsDialog setOptionsTitle(String title) {
        optionsTitleTextView.setText(title);
        return this;
    }

    public OptionsDialog setFirstOptionButton(String title, View.OnClickListener onClickListener) {
        firstOptionButton.setVisibility(View.VISIBLE);
        firstOptionButton.setText(title);
        firstOptionButton.setOnClickListener(onClickListener);
        return this;
    }

    public OptionsDialog setSecondOptionButton(String title, View.OnClickListener onClickListener) {
        secondOptionButton.setVisibility(View.VISIBLE);
        secondOptionButton.setText(title);
        secondOptionButton.setOnClickListener(onClickListener);
        return this;
    }

    public OptionsDialog setThirdOptionButton(String title, View.OnClickListener onClickListener) {
        thirdOptionButton.setVisibility(View.VISIBLE);
        thirdOptionButton.setText(title);
        thirdOptionButton.setOnClickListener(onClickListener);
        return this;
    }
}
