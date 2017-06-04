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

public class AlertDialog extends Dialog {

    private TextView alertTitleTextView, alertContentTextView;
    private Button closeAlertButton;

    public AlertDialog(@NonNull Context context) {
        super(context);

        initViewHolders();
    }

    private void initViewHolders() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);

        alertTitleTextView = (TextView) findViewById(R.id.textview_alert_title);
        alertContentTextView = (TextView) findViewById(R.id.textview_alert_content);

        closeAlertButton = (Button) findViewById(R.id.button_close_alert);
        closeAlertButton.setOnClickListener(onCloseAlertClickListener);
    }

    private View.OnClickListener onCloseAlertClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public AlertDialog setAlertTitle(String title) {
        alertTitleTextView.setText(title);
        return this;
    }

    public AlertDialog setAlertContent(String content) {
        alertContentTextView.setVisibility(View.VISIBLE);
        alertContentTextView.setText(content);
        return this;
    }
}
