package com.example.dialclassic;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DialInform extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dial_inform);
        String info_name = getIntent().getStringExtra("info_name");
        String info_number = getIntent().getStringExtra("info_number");
        String info = info_name + "\n" + info_number;

        TextView textView = findViewById(R.id.infodial);
        textView.setText(info);
    }

    private String onlynumber(String info) {
        StringBuilder number = new StringBuilder();;
        for (char c : info.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            }
        }
        return number.toString();
    }
}
