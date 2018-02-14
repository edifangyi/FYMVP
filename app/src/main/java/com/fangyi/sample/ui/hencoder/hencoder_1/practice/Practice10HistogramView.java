package com.fangyi.sample.ui.hencoder.hencoder_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Path mPath = new Path();
        String[] mTexts = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
        int[] mHeights = {4, 20, 20, 220, 380, 440, 200};
        int mTextGap = 116;
        int mHistogramWidth = 85;

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPath.moveTo(160, 100);
        mPath.lineTo(160, 600);
        mPath.lineTo(920, 600);
        canvas.drawPath(mPath, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(116, 183, 42));


        int size = mTexts.length;
        for (int i = 0; i < size; i++) {
            canvas.drawRect(180 + mHistogramWidth * i + 18 * i, 599 - mHeights[i], 265 + mHistogramWidth * i + 18 * i, 599, mPaint);
        }

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(24);
        for (int i = 0; i < size; i++) {
            if (i == 2 || i == 3) mTextGap = 110;
            if (i == 4) mTextGap = 103;
            if (i == 5) mTextGap = 108;
            if (i == 6) mTextGap = 107;
            canvas.drawText(mTexts[i], 190 + mTextGap * i, 622, mPaint);
        }
        mPaint.setTextSize(40);
        canvas.drawText(" 直方图 ", 460, 720, mPaint);

    }
}
