package com.example.dialclassic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DialInform extends AppCompatActivity {
    private ImageView btnback;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dial_infor);
        String info_name = getIntent().getStringExtra("info_name");
        String info_number = getIntent().getStringExtra("info_number");
        tachString(info_name);
        if(info_number != null && !info_number.isEmpty())number = onlynumber(info_number);
        TextView textView = findViewById(R.id.tvdialname);
        textView.setText(name);
        btnback = (ImageView) findViewById(R.id.ivback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DialActivity.class);
                startActivity(intent);
            }
        });
    }
    private String number,name;
    private void tachString(String info){
        String[] parts = info.split("\n");
        name = parts[0];
        number = onlynumber(parts[1]);
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
