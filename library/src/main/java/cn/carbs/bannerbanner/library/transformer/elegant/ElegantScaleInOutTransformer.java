package cn.carbs.bannerbanner.library.transformer.elegant;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ElegantScaleInOutTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float DELTA_SCALE = 0.15f;
    private static final float LEFT_POSITION = -1.15f;
    private static final float RIGHT_POSITION = 1.15f;
    private static final float MAX_ABS_POSITION = 1.15f;

    private float mRevisedDeltaPosition;
    private float mMaxTransX;
    private float mFactor;

    public ElegantScaleInOutTransformer() {
    }

    public ElegantScaleInOutTransformer(float revisedDeltaPosition, float maxTransX) {
        mRevisedDeltaPosition = revisedDeltaPosition;
        mMaxTransX = maxTransX;
    }

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {
        position = position - mRevisedDeltaPosition;

        if ("2".equals(view.getContentDescription())) {
            Log.d("wangwang", "transformPage view : " + view.getContentDescription() + " revised position : " + position);
        }

        if (position < LEFT_POSITION) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setTranslationX(0);
        } else if (position > RIGHT_POSITION) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setTranslationX(0);
        } else {
            mFactor = getFactorByPositionInCertainRange(position);
            view.setScaleX(mFactor);
            view.setScaleY(mFactor);
            view.setTranslationX(-mMaxTransX * position);
        }
    }

    private float getFactorByPositionInCertainRange(float position) {
        return MIN_SCALE + (1 - Math.abs(position) / MAX_ABS_POSITION) * DELTA_SCALE;
    }

}
