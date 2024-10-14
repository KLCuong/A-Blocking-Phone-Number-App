package com.example.dialclassic;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class DialActivity extends AppCompatActivity {
    ContentResolver contentResolver;
    ArrayList<String> listDial = new ArrayList<String>();
    ListView listView;
    Context context;
    ArrayList<Dial> dialList;
    DialListAdapter dialListAdapter;
    TextView textView, textView2;
    ImageView imageView;
    ArrayList<String> namelist ;
    ArrayList<String> numberlist;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialmain);
        context = this;
        listView = findViewById(R.id.listDial);
        textView = findViewById(R.id.tvSreach);
        linearLayout = findViewById(R.id.lineardiallayout);
        textView2 = findViewById(R.id.tvlineardial);
        textView.setText("Nhap ten hoac so dien thoai muon tim kiem");
        imageView = findViewById(R.id.dial_setting);
        namelist = new ArrayList<>();
        numberlist = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.ANSWER_PHONE_CALLS,
                    android.Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_CONTACTS
            }, 1);
        } else {
            try {
                getData();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        connectAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(context, DialInform.class);
                intent.putExtra("info_name", namelist.get(i));
                intent.putExtra("info_number", numberlist.get(i));
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchDialActivity.class);

                intent.putExtra("listdial", listDial);
                startActivity(intent);
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility() == View.GONE){
                linearLayout.setVisibility(View.VISIBLE);
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SettingActivity.class);
                        startActivity(intent);
                    }
                });}else{
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });



    }

    private void getData() throws Exception {
        listDial = new ArrayList<>();
        contentResolver = getContentResolver(); // URI truy vấn danh bạ
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        // Các cột cần lấy
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        // Điều kiện lọc: Chỉ lấy liên hệ có số điện thoại
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0";

        // Truy vấn danh bạ
        Cursor cursor = contentResolver.query(uri, projection, selection, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                try {
                    // Lấy chỉ số cột contactId và displayName với try-catch
                    String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                    // Lấy số điện thoại của liên hệ
                    Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String phoneSelection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
                    Cursor phoneCursor = contentResolver.query(phoneUri, null, phoneSelection, new String[]{contactId}, null);

                    if (phoneCursor != null && phoneCursor.getCount() > 0) {
                        while (phoneCursor.moveToNext()) {
                            // Lấy số điện thoại với try-catch
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNumber= phoneNumber.replaceAll("\\s+","");
                            namelist.add(displayName);
                            numberlist.add(phoneNumber);
                            String contactInfo = displayName + "\n " + phoneNumber;
                            listDial.add(contactInfo);
                        }
                        phoneCursor.close();
                    }

                } catch (Exception e) {
                    // Ghi log hoặc xử lý lỗi nếu xảy ra ngoại lệ
                    e.printStackTrace();
                    throw e; // Quăng lại ngoại lệ nếu muốn xử lý tiếp ngoài hàm
                }
            }
            cursor.close();
        } else {
            listDial.add("No contact");
        }

    }

    private void connectAdapter() {
        dialList = new ArrayList<>();
        for(int i = 0; i < listDial.size(); i++){
            dialList.add(new Dial(R.drawable.user, R.drawable.menu, listDial.get(i)));
            dialListAdapter = new DialListAdapter(this, R.layout.each_dial, dialList);
            listView.setAdapter(dialListAdapter);
        }
    }
}
