package cn.carbs.bannerbanner.backup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class BannerViewPager extends ViewPager {

    private boolean mScrollableBanner = true;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollableBanner) {
            if (getCurrentItem() == 0 && getChildCount() == 0) {
                return false;
            }
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollableBanner) {
            if (getCurrentItem() == 0 && getChildCount() == 0) {
                return false;
            }
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    public void setScrollable(boolean scrollable) {
        mScrollableBanner = scrollable;
    }
}
