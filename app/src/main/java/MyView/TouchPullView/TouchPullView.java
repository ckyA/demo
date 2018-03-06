package MyView.TouchPullView;

import android.animation.ValueAnimator;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.example.admin.i_video.R;

/**
 * Created by Admin on 2017/10/18.
 */

public class TouchPullView extends View {

    private Context mContext;
    //圆的画笔
    private Paint mCirclePaint;
    //圆的半径
    private int mCircleRadius = 90;
    private float mCirclePointX;
    private float mCirclePointY;
    private float mProgress;
    //可拖拽高度
    private int mDragHeigh = 600;
    //目标宽度
    private int mTargetWidth = 400;
    //贝塞尔曲线
    private Path mPath = new Path();
    private Paint mPathPaint;
    //重心点最终高度，决定控制点的Y坐标
    private int mTargetGravityHeight = 10;
    //角度变换 0-135
    private int mTangentAngle = 110;
    private Interpolator mProgressInterpolator = new DecelerateInterpolator();
    private Interpolator mTanentAngleInterpolator;


    public TouchPullView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    protected void init() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        p.setAntiAlias(true);
        //防抖动
        p.setDither(true);
        //填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(mContext.getColor(R.color.colorPrimary));//#0xd1ff6eb7
        mCirclePaint = p;


        //初始化路径部分画笔
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        p.setAntiAlias(true);
        //防抖动
        p.setDither(true);
        //填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(mContext.getColor(R.color.colorPrimary));
        mPathPaint = p;



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heigh = MeasureSpec.getSize(heightMeasureSpec);

        int iHeigh = (int) ((mDragHeigh * mProgress + 0.5)  );
        int iWidth = 2 * mCircleRadius + getPaddingLeft() + getPaddingRight();
        int measureWidth, measureHeigh;

        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = width;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measureWidth = Math.min(iWidth, width);
        } else {
            measureWidth = iWidth;
        }

        if (heighMode == MeasureSpec.EXACTLY) {
            measureHeigh = iHeigh;
        } else if (heighMode == MeasureSpec.AT_MOST) {
            measureHeigh = Math.min(iHeigh, heigh);
        } else {
            measureHeigh = iHeigh;
        }

        setMeasuredDimension(measureWidth, measureHeigh);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = canvas.save();
        float tranX = (getWidth() - getValueByLine(getWidth(), mTargetWidth, mProgress)) / 2;
        canvas.translate(tranX, 0);
        canvas.drawPath(mPath, mPathPaint);
        //画圆
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);
        mCirclePaint.setColor(Color.WHITE);
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius-20, mCirclePaint);
        mCirclePaint.setColor(mContext.getColor(R.color.colorPrimary));
        canvas.restoreToCount(count);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("PULL","11111");
        updatePathLayout();
    }


    public void setProgress(float progress) {

        Log.e("TAG","progress="+progress);
        mProgress=progress;
        //重新请求测量
        requestLayout();
    }


    private ValueAnimator valueAnimator;
    public void release() {

        if (valueAnimator == null) {

            ValueAnimator animator = ValueAnimator.ofFloat(mProgress, 0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object val = animation.getAnimatedValue();
                    if (val instanceof Float) {
                        setProgress((Float) val);
                    }

                }
            });
            valueAnimator = animator;
        } else {
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress, 0f);
        }
        valueAnimator.start();
    }


    private float getValueByLine(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    private void updatePathLayout(){

        final float progress=mProgressInterpolator.getInterpolation(mProgress);
        //获取可绘制区域高度宽度
        final float w=getValueByLine(getWidth(),mTargetWidth,mProgress);
        final float h=getValueByLine(0,mDragHeigh,mProgress);

        //X对称轴的参数，圆的圆心X
        final float cPointX=w/2;
        //圆的半径
        final  float cRadius=mCircleRadius;
        //圆的圆心Y坐标
        final float cPointY =h-cRadius;
        //控制点结束Y坐标
        final float endControlY=mTargetGravityHeight;

        mCirclePointX=cPointX;
        mCirclePointY= cPointY;

        final Path path=mPath;
        //重置
        path.reset();
        path.moveTo(0,0);

        //左边部分的结束点和控制点
        float lEndPointX,lEndPointY;
        float lControlPointX,lControlPointY;
        //角度转弧度


        double radian=Math.toRadians(mTangentAngle);
        float x=(float) (Math.sin(radian)*cRadius);
        float y=(float) (Math.cos(radian)*cRadius);

        lEndPointX=cPointX-x;
        lEndPointY= cPointY +y;

        //控制点y坐标变化
        lControlPointY=getValueByLine(0,endControlY,progress);
        //控制点与结束定之前的高度
        float tHeigh=lEndPointY-lControlPointY;
        //控制点与x坐标的距离
        float tWidth= (float) (tHeigh/Math.tan(radian));
        lControlPointX=lEndPointX-tWidth;
        //左边贝塞尔曲线
        path.quadTo(lControlPointX,lControlPointY,lEndPointX,lEndPointY);
        //连接到右边
        path.lineTo(cPointX+(cPointX-lEndPointX),lEndPointY);
        //右边贝塞尔曲线
        path.quadTo(cPointX+cPointX-lControlPointX,lControlPointY,w,0);

    }

}
