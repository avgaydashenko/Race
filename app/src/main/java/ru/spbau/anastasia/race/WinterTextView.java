package ru.spbau.anastasia.race;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by alex on 10.12.2015.
 */
public class WinterTextView extends TextView {
    private boolean isWiterInfo = false;

    public WinterTextView(Context context) {
        super(context);
    }

    public WinterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIsWinterInfo(boolean flag){
        isWiterInfo = flag;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int myWidth = (int) (parentWidth);
        if (isWiterInfo){
            myWidth *= 0.6;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(myWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec);
    }
}
