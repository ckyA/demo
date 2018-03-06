package MyView.TouchPullView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Admin on 2017/10/19.
 */

public class MyTouchPUllView extends View {

    private float mProgress;
    private int maxHeight = 200;

    public MyTouchPUllView(Context context) {
        this(context,null);
    }

    public MyTouchPUllView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTouchPUllView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(float mProgress){
        this.mProgress = mProgress ;
        requestLayout();//要求重绘
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int myIdealHeight = (int) (mProgress * maxHeight);

        if(heightMode == MeasureSpec.EXACTLY){//准确值
            setMeasuredDimension(widthSize , myIdealHeight);
        }else if(heightMode == MeasureSpec.EXACTLY){//最大值存在
            setMeasuredDimension(widthSize , myIdealHeight);
        }else if(heightMode == MeasureSpec.UNSPECIFIED){//未知值
            setMeasuredDimension(widthSize , myIdealHeight);
        }

    }


    private ValueAnimator valueAnimator;

    public void release() {

        if (valueAnimator == null) {

            ValueAnimator animator = ValueAnimator.ofFloat(mProgress, 0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object val = animation.getAnimatedValue();
                        setProgress((Float) val);
                }
            });
            valueAnimator = animator;
        } else {
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress, 0f);
        }
        valueAnimator.start();
    }

}
