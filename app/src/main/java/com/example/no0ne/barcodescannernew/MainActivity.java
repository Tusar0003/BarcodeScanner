package com.example.no0ne.barcodescannernew;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.no0ne.barcodescannernew.database.DatabaseHelper;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private DatabaseHelper mDatabaseHelper;

    private ZXingScannerView mScannerView;
    private TextView mCodeTextView;

    private String mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting permission for using camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }

        mResult = getIntent().getStringExtra("result");

        mCodeTextView = findViewById(R.id.text_view_code);
        mCodeTextView.setText(mResult);
    }

    public void scanCode(View view) {
        mScannerView = new ZXingScannerView(MainActivity.this);
        mScannerView.setResultHandler(MainActivity.this);

        setContentView(mScannerView);
        mScannerView.startCamera();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        mScannerView.stopCamera();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_save:
                saveResult();
                break;
            case R.id.action_list:
                FragmentManager manager = getSupportFragmentManager();
                CodeListFragment listFragment = new CodeListFragment();
                listFragment.show(manager, "Fragment");
            default:
                return false;
        }

        return true;
    }

    @Override
    public void handleResult(Result result) {
        mResult = result.getText();
        Toast.makeText(this, mResult, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("result", mResult);
        startActivity(intent);

        setContentView(R.layout.activity_main);
        mScannerView.stopCamera();
    }

    private void saveResult() {
        if (TextUtils.isEmpty(mResult)) {
            Toast.makeText(this, "Nothing to save!", Toast.LENGTH_SHORT).show();
        } else {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Title");
            dialog.show();

            final EditText titleEditText = dialog.findViewById(R.id.edit_text_title);
            Button doneButton = dialog.findViewById(R.id.button_done);

            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = titleEditText.getText().toString();

                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(MainActivity.this, "Title can not be empty!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        insertData(title);
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    private void insertData(String title) {
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.insertCode(title, mResult);
        Toast.makeText(MainActivity.this, "Code is saved!", Toast.LENGTH_SHORT).show();
    }

    private void getCodeList() {
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.getTitle();
    }
}
