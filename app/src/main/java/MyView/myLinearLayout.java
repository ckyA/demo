package MyView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Admin on 2017/10/25.
 */

public class myLinearLayout extends LinearLayout {


    public myLinearLayout(Context context) {
        super(context);
    }

    public myLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public myLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public myLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


}
