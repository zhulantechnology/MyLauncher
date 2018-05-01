package com.ds05.mylauncher.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by jun.wang on 2018/4/30.
 */

public class CornerImageView extends ImageView {
    private Paint paint;

    public CornerImageView(Context context) {
        super(context);
        //Log.e("XXX", "WANGJUN-------------1111");
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
       // Log.e("XXX", "WANGJUN-------------2222");
    }

    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // Log.e("XXX", "WANGJUN-------------3333");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            Bitmap b = getRoundBitmap(bitmap, 20);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0, getWidth(), getHeight());
           // Log.e("XXX", "WANGJUN-----b.getWidth():" + b.getWidth()); // 550 图片的宽度
           // Log.e("XXX", "WANGJUN-------getWidth():" + getWidth()); // 989 此view所占的宽度
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        paint = new Paint();

        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
                Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawARGB(0, 0, 0, 0); // 填充整个画布
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // 此处的PorterDuff.SRC_IN实现的圆角效果！
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 此处只是改变bitmap的圆角效果，drawBitmap的src和des是一样的。
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
























