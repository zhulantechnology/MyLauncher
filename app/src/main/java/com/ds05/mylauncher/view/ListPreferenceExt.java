package com.ds05.mylauncher.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.AdapterView;

/**
 * Created by Jun.wang on 2018/5/12.
 */

public class ListPreferenceExt extends ListPreference {
    public interface OnListItemClickListener {
        void onItemClick(int index);
    }

    private OnListItemClickListener mItemClickListener;
    private int mCurrIndex;

    public ListPreferenceExt(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurrIndex = getValueIndex();
    }

    private int getValueIndex() {
        return findIndexOfValue(getValue());
    }

    public ListPreferenceExt(Context context) {
        this(context,null);
    }

    public void setOnListItemClickListener(OnListItemClickListener l) {
        mItemClickListener = l;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);

        builder.setSingleChoiceItems(getEntries(), getValueIndex(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onItemClick(which);
                        }
                        mCurrIndex = which;
                    }
                });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListPreferenceExt.this.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListPreferenceExt.this.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
            }
        });
    }
}














