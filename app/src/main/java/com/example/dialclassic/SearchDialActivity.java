package com.example.dialclassic;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;

public class SearchDialActivity extends AppCompatActivity {
    Context context;
    ImageView imageView1,imageView2;
    EditText editText;
    AssetManager assetManager;
    LinearLayout linearLayout;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> listdial;
    ArrayList<String> sampleData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dial_activity);
        context = this;

        listdial = getIntent().getStringArrayListExtra("listdial");
        imageView1 = findViewById(R.id.dialsearh_back);
        imageView2 = findViewById(R.id.dialsearch_camera);
        editText = findViewById(R.id.dialsearh_text);
        linearLayout = findViewById(R.id.hiddengroup);
        listView = findViewById(R.id.listsearch);
        assetManager = getAssets();
        try{
            InputStream inputStream = assetManager.open("camera.png");
            imageView2.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DialActivity.class);
                startActivity(intent);
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setText("");  // Xóa văn bản khi nhấp vào
                }
            }
        });



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    linearLayout.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    ArrayList<String> filteredList = new ArrayList<>();
                    for(String item : listdial){
                        if(item.toLowerCase().contains(s.toString().toLowerCase())){
                                filteredList.add(item);
                        }
                    }
                    // Cập nhật adapter với danh sách đã lọc
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, filteredList);
                    listView.setAdapter(adapter);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listdial);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed here
            }
        });


    }
}
