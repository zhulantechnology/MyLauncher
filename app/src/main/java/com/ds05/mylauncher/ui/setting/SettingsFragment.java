package com.ds05.mylauncher.ui.setting;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.ds05.mylauncher.LauncherApplication;
import com.ds05.mylauncher.ModuleBaseFragment;
import com.ds05.mylauncher.R;
import com.ds05.mylauncher.common.ConnectUtils;
import com.ds05.mylauncher.qrcode.QRCodeScanActivity;

import java.util.Locale;

/**
 * Created by Jun.wang on 2018/5/2.
 */

public class SettingsFragment extends ModuleBaseFragment
                implements Preference.OnPreferenceChangeListener{

    public static final String KEY_DISPLAY = "key_settings_display";
    public static final String KEY_CAMERA_SETTINGS = "key_settings_camera_settings";
    public static final String KEY_QR_CODE = "key_settings_scan_qr_code";
    public static final String KEY_DOORBELL_SETTINGS = "key_settings_doorbell_settings";
    public static final String KEY_ENERGY_SAVING_MODE = "key_Settings_energy_saving_mode";
    public static final String KEY_TIME_AND_DATE = "key_Settings_time_date";
    public static final String KEY_LANGUAGE = "key_Settings_language";
    public static final String KEY_WIFI = "key_Settings_wifi";
    public static final String KEY_BT = "key_Settings_bt";
    public static final String KEY_INTELLIGNET_LOCK = "key_Settings_intelligent_lock";
    public static final String KEY_STORE = "key_Settings_store";
    public static final String KEY_HOME_SETTINGS = "key_Settings_home_settings";
    public static final String KEY_RESET_FACTORY = "key_Settings_reset_factory";
    public static final String KEY_CIT_TEST = "key_Settings_cit_test";
    public static final String KEY_VERSION = "key_Settings_version";
    private static final int TIMEOUT = 30;

    private static final int NETWORKCONNECTED = 0;
    private static final int NETWORKDISCONNECTED = 1;
    private static final int NETWORKISOK = 2;

    private boolean wifiConnected = false;

    private WifiManager mWifiManager;
    private BluetoothAdapter mBluetoothAdapter;
    private WifiAutoConnectManager wac;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_fragment);

        mWifiManager = (WifiManager) LauncherApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        findPreference(KEY_ENERGY_SAVING_MODE).setEnabled(false);
        SwitchPreference energySaveingMode = (SwitchPreference) findPreference(KEY_ENERGY_SAVING_MODE);
        SwitchPreference wifiPref = (SwitchPreference) findPreference(KEY_WIFI);
        getPreferenceScreen().removePreference(wifiPref);
        // wifiPref.setOnPreferenceChangeListener(this);
        energySaveingMode.setOnPreferenceChangeListener(this);
        wac = new WifiAutoConnectManager(mWifiManager);

        boolean isSavingMode = energySaveingMode.isChecked();
        //wifiPref.setEnabled(!isSavingMode);
        findPreference(KEY_BT).setEnabled(!isSavingMode);
        findPreference(KEY_INTELLIGNET_LOCK).setEnabled(!isSavingMode);
        if (isSavingMode) {
            // wifiPref.setChecked(false);
            /*if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(false);
            } */
            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }
        } else {
            // wifiPref.setChecked(mWifiManager.isWifiEnabled());
        }

        if(mWifiManager == null) {
            getPreferenceScreen().removePreference(wifiPref);
        }
        if(mBluetoothAdapter == null) {
            getPreferenceScreen().removePreference(findPreference(KEY_BT));
        }
        initLanguage();
    }

    private void initLanguage() {
        Configuration config = getResources().getConfiguration();
        String currLang = config.locale.getLanguage() + "_" + config.locale.getCountry();
        Log.e("XXX", "currLang------:" + currLang);

        if(!currLang.equals("zh_CN") && !currLang.equals("en_US")) {
            String defLang = getResources().getString(R.string.def_language);
            Locale defLocale = Locale.SIMPLIFIED_CHINESE;
            if(defLang.equals("en_US")) {
                defLocale = Locale.ENGLISH;
            }
            config.locale = defLocale;
            DisplayMetrics dm = getResources().getDisplayMetrics();
            getResources().updateConfiguration(config, dm);
            currLang = defLang;
        }

        ListPreference langPref = (ListPreference)findPreference(KEY_LANGUAGE);
        int index = 0;
        if(currLang.equals("zh_CN")) {
            index = 0;
        } else if(currLang.equals("en_US")) {
            index = 1;
        }

        langPref.setValue("" + index);
        langPref.setSummary(langPref.getEntries()[index]);
        //langPref.setOnPreferenceChangeListener(this);
    }

    /**
     * 申请指定的权限.
     */
    public void requestPermission(int code, String... permissions) {

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, code);
        }
    }
    /**
     * 判断是否有指定的权限
     */
    public boolean hasPermission(String... permissions) {

        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permisson)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOpenCameraQR();
                }
                break;
        }
    }

    public void doOpenCameraQR() {
        Intent intent = new Intent(getActivity(), QRCodeScanActivity.class);
        intent.putExtra(QRCodeScanActivity.EXTRA_REQ_REASON, QRCodeScanActivity.REASON_GET_WIFI);
        startActivityForResult(intent, QRCodeScanActivity.ACT_REQUEST_CODE_GET_WIFI);
    }

    public static final int WRITE_READ_EXTERNAL_CODE = 0x01;
    public static final String[] WRITE_READ_EXTERNAL_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final int HARDWEAR_CAMERA_CODE = 0x02;
    public static final String[] HARDWEAR_CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key.equals(KEY_QR_CODE)) {
            if (hasPermission(HARDWEAR_CAMERA_PERMISSION)) {
                Intent intent = new Intent(getActivity(), QRCodeScanActivity.class);
                intent.putExtra(QRCodeScanActivity.EXTRA_REQ_REASON, QRCodeScanActivity.REASON_GET_WIFI);
                startActivityForResult(intent, QRCodeScanActivity.ACT_REQUEST_CODE_GET_WIFI);
            } else {
                requestPermission(HARDWEAR_CAMERA_CODE, HARDWEAR_CAMERA_PERMISSION);
            }

            return true;
        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    String userId = null, ssid = null, pwd = null; // ssid是wifi名称，pwd是密码
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case QRCodeScanActivity.ACT_REQUEST_CODE_GET_WIFI: {
                if(resultCode != Activity.RESULT_OK) return;

                String result = data.getStringExtra(QRCodeScanActivity.EXTRA_QRCODE_RESULT);
                if(TextUtils.isEmpty(result)) return;

                final String[] wifiInfo = result.split(",");
                if(wifiInfo == null || (wifiInfo.length != 2 && wifiInfo.length != 3)) return;

                if(wifiInfo.length == 2) {
                    ssid = wifiInfo[0];
                    pwd = wifiInfo[1];
                } else if(wifiInfo.length == 3) {
                    userId = wifiInfo[0];
                    ssid = wifiInfo[1];
                    pwd = wifiInfo[2];
                    if(ConnectUtils.NETWORK_IS_OK  && ConnectUtils.CONNECT_SERVER_STATUS){
                        Message message = new Message();
                        message.what = NETWORKISOK;
                        mHandler.sendMessage(message);

                        Intent intent = new Intent();
                        intent.setAction("com.ds05.Broadcast.ToServer.NOTIFY_QRCODE_RESULT");
                        intent.putExtra("QRCodeResult_UserId", userId);
                        intent.putExtra("QRCodeResult_WifiSSID", ssid);
                        intent.putExtra("QRCodeResult_WifiPassword", pwd);
                        getActivity().sendBroadcast(intent);
                    }else{
                        wac.connect(ssid, pwd, 
                                pwd.equals("")? WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS: WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
                        showWaitDialog();
                        Thread mThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                wifiConnected = true;
                                int timeout = 0;

                                while (!ConnectUtils.NETWORK_IS_OK || !ConnectUtils.CONNECT_SERVER_STATUS) {
                                    try {
                                        // 涓轰簡閬垮厤绋嬪簭涓€鐩磜hile寰幆锛岃瀹冪潯涓?00姣妫€娴嬧€︹€?
                                        timeout++;
                                        if(timeout > TIMEOUT){
                                            wifiConnected = false;
                                            break;
                                        }
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ie) {
                                    }
                                }
                                Log.d("ZXH","###  wifiConnected = " + wifiConnected);

                                dismissDlg();
                                if(wifiConnected){
                                    Message message = new Message();
                                    message.what = NETWORKCONNECTED;
                                    mHandler.sendMessage(message);
                                    Intent intent = new Intent();
                                    intent.setAction("com.ds05.Broadcast.ToServer.NOTIFY_QRCODE_RESULT");
                                    intent.putExtra("QRCodeResult_UserId", userId);
                                    intent.putExtra("QRCodeResult_WifiSSID", ssid);
                                    intent.putExtra("QRCodeResult_WifiPassword", pwd);
                                    getActivity().sendBroadcast(intent);
                                }else{
                                    Message message = new Message();
                                    message.what = NETWORKDISCONNECTED;
                                    mHandler.sendMessage(message);
                                }
                            }
                        });
                        mThread.start();
                    }

//                    Intent intent = new Intent();
//                    intent.setAction("com.ds05.Broadcast.ToServer.NOTIFY_QRCODE_RESULT");
//                    intent.putExtra("QRCodeResult_UserId", userId);
//                    intent.putExtra("QRCodeResult_WifiSSID", ssid);
//                    intent.putExtra("QRCodeResult_WifiPassword", pwd);
//                    getActivity().sendBroadcast(intent);
                }
//                if(ssid == null) return;
//
//                final String ssidF = ssid, pwdF = pwd;
//                createDialog(getActivity(),
//                        getString(R.string.string_connect_wifi),
//                        getString(R.string.string_ap_name) + " " + ssid + "\n"
//                            + getString(R.string.string_pwd) + " " + pwd,
//                        getString(R.string.string_connect),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                WifiUtils wifiUtils = new WifiUtils(getActivity());
//                                wifiUtils.connectWifi(ssidF, pwdF);
//                            }
//                        });
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NETWORKCONNECTED:
//                    ToastUtil.showToast(getActivity(), getActivity().getResources().getString(R.string.wifi_connected));
                    Toast.makeText(getActivity(), R.string.wifi_connected, Toast.LENGTH_SHORT).show();
                    break;
                case NETWORKDISCONNECTED:
//                    ToastUtil.showToast(getActivity(), getActivity().getResources().getString(R.string.wifi_disconnected));
                    Toast.makeText(getActivity(), R.string.wifi_disconnected, Toast.LENGTH_SHORT).show();
                    break;
                case NETWORKISOK:
//                    ToastUtil.showToast(getActivity(), getActivity().getResources().getString(R.string.not_configure_wifi));
                    Toast.makeText(getActivity(), R.string.not_configure_wifi, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private ProgressDialog mWaitingDialog;

    private void showWaitDialog() {
        if (mWaitingDialog != null && mWaitingDialog.isShowing()) return;
        if (mWaitingDialog == null) {
            mWaitingDialog = new ProgressDialog(getActivity());
            mWaitingDialog.setMessage(getString(R.string.waiting));
            mWaitingDialog.setIndeterminate(true);
            mWaitingDialog.setCancelable(false);
            mWaitingDialog.setCanceledOnTouchOutside(false);
        }
        mWaitingDialog.show();
    }

    private void dismissDlg() {
        if (mWaitingDialog != null && mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
        }
    }
}




















