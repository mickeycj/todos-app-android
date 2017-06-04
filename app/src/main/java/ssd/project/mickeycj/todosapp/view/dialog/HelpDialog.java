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

public class HelpDialog extends Dialog {

    private TextView helpTitleTextView, helpFirstContentTextView, helpSecondContentTextView, helpThirdContentTextView, helpFourthContentTextView;
    private Button closeHelpTextView;

    public HelpDialog(@NonNull Context context) {
        super(context);

        initViewHolders();
    }

    private void initViewHolders() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_help);

        helpTitleTextView = (TextView) findViewById(R.id.textview_help_title);
        helpFirstContentTextView = (TextView) findViewById(R.id.textview_help_content_1);
        helpSecondContentTextView = (TextView) findViewById(R.id.textview_help_content_2);
        helpThirdContentTextView = (TextView) findViewById(R.id.textview_help_content_3);
        helpFourthContentTextView = (TextView) findViewById(R.id.textview_help_content_4);

        closeHelpTextView = (Button) findViewById(R.id.button_close_help);
        closeHelpTextView.setOnClickListener(onCloseHelpClickListener);
    }

    private View.OnClickListener onCloseHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public HelpDialog setHelpTitle(String title) {
        helpTitleTextView.setText(title);
        return this;
    }

    public HelpDialog setFirstHelpContent(String content) {
        helpFirstContentTextView.setVisibility(View.VISIBLE);
        helpFirstContentTextView.setText(content);
        return this;
    }

    public HelpDialog setSecondHelpContent(String content) {
        helpSecondContentTextView.setVisibility(View.VISIBLE);
        helpSecondContentTextView.setText(content);
        return this;
    }

    public HelpDialog setThirdHelpContent(String content) {
        helpThirdContentTextView.setVisibility(View.VISIBLE);
        helpThirdContentTextView.setText(content);
        return this;
    }

    public HelpDialog setFourthHelpContent(String content) {
        helpFourthContentTextView.setVisibility(View.VISIBLE);
        helpFourthContentTextView.setText(content);
        return this;
    }
}
