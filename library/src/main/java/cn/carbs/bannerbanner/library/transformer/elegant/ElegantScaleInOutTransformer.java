package cn.carbs.bannerbanner.library.transformer.elegant;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * 需要在代码外边对ViewPager添加如下代码：
 *      viewPager.setPageMargin(pageMargin);
 *      viewPager.setPadding(pagePaddingH, 0, pagePaddingH, 0);
 *      viewPager.setClipToPadding(false);
 */
public class ElegantScaleInOutTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float DELTA_SCALE = 0.15f;
    private static final float LEFT_POSITION = -1.15f;
    private static final float RIGHT_POSITION = 1.15f;
    private static final float MAX_ABS_POSITION = 1.15f;

    private float mRevisedDeltaPosition;
    private float mMaxTransX;
    private float mFactor;
    private int mBannerPagerCount = 5;

    public ElegantScaleInOutTransformer(float pagerWidth, float pageMargin, float pagerPaddingHorizontal, int bannerPagerCount) {
        mRevisedDeltaPosition = pagerPaddingHorizontal / (pagerWidth - pagerPaddingHorizontal * 2);
        mMaxTransX = pagerPaddingHorizontal - pageMargin;
        mBannerPagerCount = bannerPagerCount;
    }

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {
        if (position < 1 - mBannerPagerCount) {
            // 暂时规避，目前其它app的做法是，将 ViewPager 初始化定在第 0 page，不能往前翻页
            return;
        }

        position = position - mRevisedDeltaPosition;

        if ("5".equals(view.getContentDescription()) || "0".equals(view.getContentDescription())) {
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
