package cn.check.permission.demo.cn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import cn.project.demo.com.R;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

/**
 * Created by chawei on 2017/10/20.
 */

public class PermissionHelperActivity extends ActivityManagePermission {

    private static final int REQUEST_IMAGE_CAPTURE=0x00;
    public static final String TAG = "ez";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_helper_layout);
        askSinglePermission();
        final String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};
        askMultiPermission(permissionAsk);
    }

    private void askMultiPermission(final String[] permissionAsk) {
        findViewById(R.id.id_btn_multi_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCompactPermissions(permissionAsk, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        intentToPicture();
                        Log.d(TAG, "permissionGranted: 权限被允许了");
                    }

                    @Override
                    public void permissionDenied() {
                        Log.d(TAG, "permissionGranted: 权限被拒绝了");
                    }

                    @Override
                    public void permissionForeverDenied() {
                        showDialog();
                        Log.d(TAG, "permissionGranted: 权限被禁止了");
                    }
                });
            }
        });
    }

    private void intentToPicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void askSinglePermission() {
        findViewById(R.id.id_btn_imei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCompactPermission(PermissionUtils.Manifest_READ_PHONE_STATE, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        Log.d(TAG, "permissionGranted: ---->已经授权了");
                    }

                    @Override
                    public void permissionDenied() {
                        Log.d(TAG, "permissionGranted: ---->权限被拒绝了");
                    }

                    @Override
                    public void permissionForeverDenied() {
                        showDialog();
                        Log.d(TAG, "permissionGranted: ---->权限被禁止了");
                    }
                });
            }
        });
    }


    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意");
        builder.setMessage("需要您去设置中授予该权限，才能继续操作！");
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettingsApp(PermissionHelperActivity.this);
            }
        });
        builder.setNegativeButton("算了", null);
        builder.show();
    }
}
