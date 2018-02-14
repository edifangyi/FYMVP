package com.fangyi.sample.ui.hencoder.hencoder_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Path mPath = new Path();

        RectF lRectF = new RectF(190, 90, 680, 580);

        // 画 Lollipop 部分
        mPaint.setColor(Color.rgb(242, 69, 61));
        canvas.drawArc(lRectF, -180, 125, true, mPaint);

        RectF rectF = new RectF(200, 100, 700, 600);

        // 画 KitKat 部分
        mPaint.setColor(Color.rgb(43, 152, 240));
        canvas.drawArc(rectF, 178, -103, true, mPaint);

        // 画 Jelly Bean 部分
        mPaint.setColor(Color.rgb(21, 149, 136));
        canvas.drawArc(rectF, 73, -60, true, mPaint);

        // 画 ICE 部分
        mPaint.setColor(Color.rgb(158, 158, 158));
        canvas.drawArc(rectF, 11, -4, true, mPaint);

        // 画 Gingerbread 部分
        mPaint.setColor(Color.rgb(155, 47, 174));
        canvas.drawArc(rectF, 6, -5, true, mPaint);

        // 画 Marshmallow 部分
        mPaint.setColor(Color.rgb(253, 192, 47));
        canvas.drawArc(rectF, -51, 50, true, mPaint);
    }
}
