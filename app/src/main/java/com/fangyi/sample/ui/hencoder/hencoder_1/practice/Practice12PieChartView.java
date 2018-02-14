package com.fangyi.sample.ui.hencoder.hencoder_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Practice12PieChartView extends View {

    public Practice12PieChartView(Context context) {
        super(context);
    }

    public Practice12PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        List<Integer> datas = new ArrayList<>();
        float per;// 单位角度
        RectF rectF = null;
        int r = 250;// 饼图 半径
        int txtSize = 24;// 文字大小
        int maxVal = 0;// 标记最大值
        int pieDif = 20;// 最大扇形偏移量

        Paint txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setStrokeWidth(3);
        txtPaint.setTextSize(txtSize);
        txtPaint.setAntiAlias(true);

        datas.add(30);
        datas.add(70);
        datas.add(15);
        datas.add(40);
        datas.add(3);
        datas.add(20);
        datas.add(100);
        datas.add(51);
        datas.add(16);
        datas.add(5);
        datas.add(8);
        datas.add(90);
        datas.add(100);

        int sum = 0;
        for (int i = 0; i < datas.size(); i++) {
            sum += datas.get(i);
            maxVal = Math.max(maxVal, datas.get(i));
        }
        per = 360.0f / sum;

        if (rectF == null) {
            rectF = new RectF(getWidth() / 2 - r, getHeight() / 2 - r, getWidth() / 2 + r, getHeight() / 2 + r);
        }


        float total = 0;
        for (int i = 0; i < datas.size(); i++) {

            // 绘制饼图 x y 相对于圆心
            RectF tempRectf = rectF;
            int lineDif = 0;
            if (maxVal == datas.get(i)) {
                lineDif = pieDif;
                double pieDifX = Math.cos((-total - datas.get(i) * per / 2) * Math.PI / 180) * pieDif;
                double pieDifY = Math.sin((-total - datas.get(i) * per / 2) * Math.PI / 180) * pieDif;

                tempRectf = new RectF(rectF.left + (float) pieDifX, rectF.top - (float) pieDifY,
                        rectF.right + (float) pieDifX, rectF.bottom - (float) pieDifY);
            }
            paint.setColor(Color.parseColor("#" + getRandColorCode()));
            canvas.drawArc(tempRectf, total, datas.get(i) * per, true, paint);

            // 划斜线
            double startX = Math.cos((-total - datas.get(i) * per / 2) * Math.PI / 180) * (r + lineDif);
            double startY = Math.sin((-total - datas.get(i) * per / 2) * Math.PI / 180) * (r + lineDif);

            double endX = Math.cos((-total - datas.get(i) * per / 2) * Math.PI / 180) * (r + 30 + lineDif);
            double endY = Math.sin((-total - datas.get(i) * per / 2) * Math.PI / 180) * (r + 30 + lineDif);

            txtPaint.setColor(Color.parseColor("#EBEBEB"));
            canvas.drawLine((float) (getWidth() / 2 + startX), (float) (getHeight() / 2 - startY),
                    (float) (getWidth() / 2 + endX), (float) (getHeight() / 2 - endY), txtPaint);

            // 绘制横线
            float hLength = startX > 0 ? 60 : -60;
            canvas.drawLine((float) (getWidth() / 2 + endX), (float) (getHeight() / 2 - endY),
                    (float) (getWidth() / 2 + endX) + hLength, (float) (getHeight() / 2 - endY), txtPaint);

            // 绘制文字
            float txtWidth = txtPaint.measureText(datas.get(i) + "");
            double txtX = endX > 0 ? endX + 10 : endX - txtWidth - 10;
            double txtY = endY - txtSize / 2;
            canvas.drawText(datas.get(i) + "", (float) (getWidth() / 2 + txtX) + hLength, (float) (getHeight() / 2 - txtY), txtPaint);

            total += datas.get(i) * per;
        }
    }

    /**
     * 获取十六进制的颜色代码. 例如  "#6E36B4" , For HTML ,
     *
     * @return String
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return r + g + b;
    }
}
