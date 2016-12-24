package sbingo.com.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


/**
 * Author: Sbingo
 * Date:   2016/12/22
 * viewpager滑动时底部的圆点指示器
 */

public class ViewPagerIndicator extends View {

    /**
     * 绘图中心X坐标的百分比值
     */
    private float mPercentX = 0f;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 画笔颜色数组
     */
    private int[] colors = {Color.BLUE, Color.BLACK, Color.GREEN, Color.YELLOW, Color.RED};
    /**
     * 随机生成的画笔颜色序号
     */
    private int colorIndex = 0;
    /**
     * 当前画笔颜色序号
     */
    private int currentColorIndex = 0;

    /**
     * 页数
     */
    private int pageCount;
    /**
     * 初始计算起始点
     */
    private float startX;
    /**
     * 当前计算起始点
     */
    private float currentStartX;
    /**
     * X方向偏移量
     */
    private float offsetX;
    /**
     * 某次滑动是否停止
     */
    private boolean isIdle;
    /**
     * 当前页码
     */
    private int currentPageIndex;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(colors[currentColorIndex]);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasure(widthMeasureSpec, 360), getMeasure(heightMeasureSpec, 15));
    }

    private int getMeasure(int measure, int defaultSize) {
        int result = 0;
        int mode = MeasureSpec.getMode(measure);
        int size = MeasureSpec.getSize(measure);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.min(dp2px(defaultSize), size);
        }
        return result;
    }

    private int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pageCount <= 1) {
            throw new RuntimeException("ViewPagerIndicator_非法页数！");
        }
        if (isIdle) {
            offsetX = 0f;
            isIdle = false;
        } else {
            offsetX = mPercentX * getWidth() / pageCount;
        }
        canvas.drawCircle(currentStartX + offsetX, getHeight() / 2, getHeight() / 3, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        startX = currentStartX = getWidth() / (2f * pageCount);
    }

    private void changeColor() {
        //保证换一个颜色
        while (currentColorIndex == colorIndex) {
            colorIndex = new Random().nextInt(colors.length);
        }
        currentColorIndex = colorIndex;
        if (currentColorIndex <= colors.length - 1) {
            mPaint.setColor(colors[currentColorIndex]);
        }
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        mPaint.setColor(colors[currentColorIndex]);
        invalidate();
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setCurrentPageIndex(int index) {
        //只在滑到另一页时绘制
        if (currentPageIndex == index) return;
        currentPageIndex = index;

        currentStartX = startX + index * getWidth() / pageCount;
        isIdle = true;
        changeColor();
        invalidate();
    }

    public void drawPoint(int position, float percentX) {
        //实时更新计算基准
        //往回滑动时，计算基准减一个单位
        if (position < currentPageIndex) {
            currentStartX = startX + (currentPageIndex - 1) * getWidth() / pageCount;
        } else { //往后滑动时，计算基准不变
            currentStartX = startX + currentPageIndex* getWidth() / pageCount;
        }
        //防止滑动停止时出现绘制在起点的问题
        if (mPercentX > 0.9 && percentX == 0) {
            return;
        }
        //防止过度绘制，尤其是在边界处
        if (mPercentX == percentX) {
            return;
        }
        this.mPercentX = percentX;
        invalidate();
    }
}
