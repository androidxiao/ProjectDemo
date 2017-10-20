package cn.check.permission.demo.cn;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/3.
 * 自己写，调用系统
 */

public class CheckPermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    Context context;
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permission_layout);

        context = this;


        Button request_permission = (Button) findViewById(R.id.request_permission);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
        button13 = (Button) findViewById(R.id.button13);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
        button13.setOnClickListener(this);

        request_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getBaseContext(), AllPermissionActivity.class);
                startActivity(ii);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == button1) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissions()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissions(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button2) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionss()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionss(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button3) {
            if (Build.VERSION.SDK_INT >= 23) {
                Log.e("value", "checkPermissionsrp（） --->" + checkPermissionsrp());
                if (checkPermissionsrp()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    Log.e("value", "requestPermissionsrp（）");

                    requestPermissionsrp(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button4) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionsc()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionsc(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button5) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionswc()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionswc(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button6) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionsrds()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionsrds(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button7) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionsga()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionsga(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button8) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionsred()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionsred(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button9) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionsacl()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionsacl(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button11) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionscp()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionscp(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button12) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionsxx()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionsxx(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        } else if (v == button13) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionszz()) {
                    // Code for above or equal 23 API Oriented Device
                    // Your Permission granted already .Do next code
                } else {
                    requestPermissionszz(); // Code for permission
                }
            } else {

                // Code for Below 23 API Oriented Device
                // Do next code
            }
        }
    }


    //************************************ACCESS_FINE_LOCATION*********************************************************************
    private boolean checkPermissionss() {
        int result1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionss() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

//************************************************CAMERA**********************************************************************

    private boolean checkPermissions() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissions() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }

    //************************************************READ_PHONE_STATE**********************************************************************

    private boolean checkPermissionsrp() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsrp() {
        boolean permissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE);
        Log.e("value", "requestPermissionsrp: --->" + permissionRationale);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setMessage("跳转到设置页面")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent); // 打开系统设置界面
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        } else {
            Log.e("value", "ActivityCompat.requestPermissions----->");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************READ_CALENDAR**********************************************************************

    private boolean checkPermissionsc() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsc() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CALENDAR)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CALENDAR}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************WRITE_CALENDAR**********************************************************************

    private boolean checkPermissionswc() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionswc() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_CALENDAR)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CALENDAR}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************READ_CONTACTS**********************************************************************

    private boolean checkPermissionsrds() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsrds() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************GET_ACCOUNTS**********************************************************************

    private boolean checkPermissionsga() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsga() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.GET_ACCOUNTS}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************RECORD_AUDIO**********************************************************************

    private boolean checkPermissionsred() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsred() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************ACCESS_COARSE_LOCATION**********************************************************************

    private boolean checkPermissionsacl() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsacl() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            //Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    //************************************************CALL_PHONE**********************************************************************

    private boolean checkPermissionscp() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionscp() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {
            //Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************READ_EXTERNAL_STORAGE**********************************************************************

    private boolean checkPermissionsxx() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionsxx() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    //************************************************WRITE_EXTERNAL_STORAGE**********************************************************************

    private boolean checkPermissionszz() {
        int result2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionszz() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                Log.e("value", "onRequestPermissionsResult: --->" + permissions.length);
                break;
        }
    }
}
