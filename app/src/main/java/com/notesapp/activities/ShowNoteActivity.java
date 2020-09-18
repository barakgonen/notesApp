package com.notesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.R;

public class ShowNoteActivity extends AppCompatActivity {

    TextView txtTitleVal;
    TextView txtBodyVal;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtTitleVal = findViewById(R.id.titleTxtVal);
        txtBodyVal = findViewById(R.id.bodyVal);
        tableLayout = findViewById(R.id.tblLayout);
        tableLayout.setBackgroundColor(-74056);
        Bundle bundle = getIntent().getExtras();
        initSetupFields(bundle);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);

        finishAfterTransition();
    }

    private void initSetupFields(Bundle bundle) {
        if (bundle != null) {
            String title = bundle.getString("title");
            String body = bundle.getString("body");
            int bgColor = Integer.parseInt(bundle.getString("color"));
            txtBodyVal.setText(body);
            txtTitleVal.setText(title);
            tableLayout.setBackgroundColor(bgColor);
        }
    }
}
