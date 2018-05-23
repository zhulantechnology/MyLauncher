package com.ds05.mylauncher.qrcode;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ds05.mylauncher.R;

public class QRCodeScanActivity extends Activity {

    public static final int ACT_REQUEST_CODE_GET_WIFI = 1;
    public static final String EXTRA_QRCODE_RESULT = "QRCode-Result";

    public static final String EXTRA_REQ_REASON = "REQ-REASON";
    public static final int REASON_NONE = -1;
    public static final int REASON_GET_WIFI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_layout);

        CameraManager.init(getApplication());

    }
}
