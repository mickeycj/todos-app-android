package ssd.project.mickeycj.todosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewHolders();
    }

    private void initViewHolders() {
        usernameTextView = (TextView) findViewById(R.id.textview_username);

        helpButton = (Button) findViewById(R.id.button_help);
        helpButton.setOnClickListener(onHelpClickListener);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
