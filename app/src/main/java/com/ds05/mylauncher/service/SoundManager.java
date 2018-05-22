package com.ds05.mylauncher.service;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.provider.MediaStore;

import com.ds05.mylauncher.R;
import com.ds05.mylauncher.common.manager.PrefDataManager;

import java.net.PortUnreachableException;
import java.util.IllegalFormatCodePointException;

/**
 * Created by Jun.wang on 2018/5/21.
 */


public class SoundManager {

    private static final int[] ALARM_SOUND = new int[] {
            R.raw.di_xi_alarm,
            R.raw.jian_xiao
    };

    public static final int SOUND_STREAM = AudioManager.STREAM_ALARM;
    private static final int ALARM_SOUND_LOOP = 3;
    private boolean mAlarmIsSilence = false;
    private SoundPool mSoundPool;
    private SoundPool mTestLoop;
    private int mTestLoopId = -1;

    private int mAlarmSoundId;
    private float mAlarmSoundVolume;

    private Context mContext;
    private static SoundManager mInstance;

    SoundManager(Context ctx) {
        mContext = ctx;
        mInstance = this;

        int alarmSoundIndex = PrefDataManager.getAlarmSoundIndex();
        mAlarmIsSilence = false;
        if (alarmSoundIndex == 0) {
            mAlarmIsSilence = true;
        } else if (alarmSoundIndex < 0|| alarmSoundIndex > ALARM_SOUND.length) {
            alarmSoundIndex = 0;
        }

        mSoundPool = new SoundPool(1,SOUND_STREAM, 1);
        if (!mAlarmIsSilence) {
            mAlarmSoundId = mSoundPool.load(mContext, ALARM_SOUND[alarmSoundIndex - 1],1);
        }

        mAlarmSoundVolume = PrefDataManager.getAlarmSoundVolume();
    }

    public static SoundManager getInstance() {
        if (mInstance == null) {
            throw  new IllegalStateException("SoundManager not create");
        }
        return mInstance;
    }

    public float getAlarmSoundVolume() {
        return mAlarmSoundVolume;
    }

    public void playAlarmSound() {
        if (mAlarmIsSilence) return;

        stopTestSound();
        stopALarmSound();
        mSoundPool.play(mAlarmSoundId, mAlarmSoundVolume,mAlarmSoundVolume,1,ALARM_SOUND_LOOP,1);
    }

    public void stopTestSound() {
        if (mTestLoopId < 0) return;
        mTestLoop.stop(mTestLoopId);
        mTestLoopId = -1;
    }

    public void stopALarmSound() {
        if (mAlarmIsSilence) return;
        mSoundPool.stop(mAlarmSoundId);
    }

    public void testAlarmSound(final int index, final float volume) {
        if(index < 0 || index >= ALARM_SOUND.length) {
            throw new IllegalArgumentException("Invalide sound index: " + index);
        }

        if(mTestLoop != null) {
            mTestLoop.stop(mTestLoopId);
            mTestLoop.setOnLoadCompleteListener(null);
            mTestLoop.release();
            mTestLoop = null;
        }
        mTestLoop = new SoundPool(1, SOUND_STREAM, 1);
        mTestLoop.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mTestLoop.play(mTestLoopId, volume, volume, 1, ALARM_SOUND_LOOP, 1);
            }
        });
        mTestLoopId = mTestLoop.load(mContext, ALARM_SOUND[index], 1);
    }
}









































