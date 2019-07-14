package cn.carbs.bannerbanner.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.carbs.bannerbanner.library.listener.OnBannerListener;
import cn.carbs.bannerbanner.library.loader.ImageLoaderInterface;
import cn.carbs.bannerbanner.library.view.BannerViewPager;


public class BannerBanner extends FrameLayout implements ViewPager.OnPageChangeListener {
    public static final String TAG = "banner";

    private Context mContext;
    private BannerViewPager mViewPager;
    private ImageView mBannerDefaultImage;
    private TextView mBannerTitle;
    private TextView mNumIndicatorInside;
    private TextView mNumIndicator;
    private LinearLayout mIndicator;
    private LinearLayout mIndicatorInside;
    private LinearLayout mTitleView;

    private ImageLoaderInterface mImageLoader;
    private BannerPagerAdapter mPagerAdapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private BannerScroller mScroller;
    private OnBannerListener mListener;
    private DisplayMetrics mDisplayMetrics;

    private List mImageUrls;
    private List<String> mTitles;
    private List<View> mImageViews;
    private List<ImageView> mIndicatorImages;

    private WeakHandler mHandler = new WeakHandler();

    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorSize;
    private int mBannerBackgroundImage;
    private int mBannerStyle = BannerConfig.CIRCLE_INDICATOR;
    private int mDelayTime = BannerConfig.TIME;
    private int mScrollTime = BannerConfig.DURATION;
    private int mIndicatorSelectedResId = R.drawable.banner_banner_gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.banner_banner_white_radius;
    private int mLayoutResId = R.layout.banner_banner_view;
    private int mTitleHeight;
    private int mTitleBackground;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private int mCount = 0;
    private int mCurrentItem;
    private int mGravity = -1;
    private int mLastPosition = 1;
    private int mScaleType = 1;
    private boolean mIsAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean mIsScroll = BannerConfig.IS_SCROLL;

    public BannerBanner(Context context) {
        this(context, null);
    }

    public BannerBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mTitles = new ArrayList<>();
        mImageUrls = new ArrayList<>();
        mImageViews = new ArrayList<>();
        mIndicatorImages = new ArrayList<>();
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mIndicatorSize = mDisplayMetrics.widthPixels / 80;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mImageViews.clear();
        initAttrs(context, attrs);
        LayoutInflater.from(context).inflate(mLayoutResId, this, true);
        mBannerDefaultImage = findViewById(R.id.bannerDefaultImage);
        mViewPager = findViewById(R.id.banner_banner_view_pager);
        mTitleView = findViewById(R.id.banner_banner_title_view);
        mIndicator = findViewById(R.id.banner_banner_circle_indicator);
        mIndicatorInside = findViewById(R.id.banner_banner_circle_indicator);
        mBannerTitle = findViewById(R.id.banner_banner_title);
        mNumIndicator = findViewById(R.id.banner_banner_number_indicator);
        mNumIndicatorInside = findViewById(R.id.banner_banner_num_indicator_inside);
        Log.d("wangwang", "banner --> bannerBackgroundImage : " + mBannerBackgroundImage);
        mBannerDefaultImage.setImageResource(mBannerBackgroundImage);
        initViewPagerScroll();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerBanner);
        mIndicatorWidth = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_indicator_width, mIndicatorSize);
        mIndicatorHeight = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_indicator_height, mIndicatorSize);
        mIndicatorMargin = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_indicator_margin, BannerConfig.PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_indicator_drawable_selected, R.drawable.banner_banner_gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_indicator_drawable_unselected, R.drawable.banner_banner_white_radius);
        mScaleType = typedArray.getInt(
                R.styleable.BannerBanner_bb_image_scale_type, mScaleType);
        mDelayTime = typedArray.getInt(
                R.styleable.BannerBanner_bb_delay_time, BannerConfig.TIME);
        mScrollTime = typedArray.getInt(
                R.styleable.BannerBanner_bb_scroll_time, BannerConfig.DURATION);
        mIsAutoPlay = typedArray.getBoolean(
                R.styleable.BannerBanner_bb_is_auto_play, BannerConfig.IS_AUTO_PLAY);
        mTitleBackground = typedArray.getColor(
                R.styleable.BannerBanner_bb_title_background, BannerConfig.TITLE_BACKGROUND);
        mTitleHeight = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_title_height, BannerConfig.TITLE_HEIGHT);
        mTitleTextColor = typedArray.getColor(
                R.styleable.BannerBanner_bb_title_text_color, BannerConfig.TITLE_TEXT_COLOR);
        mTitleTextSize = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_title_text_size, BannerConfig.TITLE_TEXT_SIZE);
        mLayoutResId = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_banner_layout, mLayoutResId);
        mBannerBackgroundImage = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_banner_default_image, 0);
        typedArray.recycle();
    }

    private void initViewPagerScroll() {
        try {
            mScroller = new BannerScroller(mViewPager.getContext());
            mScroller.setDuration(mScrollTime);
            Field fieldScroller = ViewPager.class.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            fieldScroller.set(mViewPager, mScroller);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public BannerBanner isAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
        return this;
    }

    public BannerBanner setImageLoader(ImageLoaderInterface imageLoader) {
        mImageLoader = imageLoader;
        return this;
    }

    public BannerBanner setDelayTime(int delayTime) {
        mDelayTime = delayTime;
        return this;
    }

    public BannerBanner setIndicatorGravity(int type) {
        switch (type) {
            case BannerConfig.LEFT:
                mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.CENTER:
                mGravity = Gravity.CENTER;
                break;
            case BannerConfig.RIGHT:
                mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }

    public BannerBanner setBannerAnimation(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(TAG, "Please set the PageTransformer class");
        }
        return this;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public BannerBanner setOffscreenPageLimit(int limit) {
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * Set a that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public BannerBanner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public BannerBanner setBannerTitles(List<String> titles) {
        mTitles = titles;
        return this;
    }

    public BannerBanner setBannerStyle(int bannerStyle) {
        mBannerStyle = bannerStyle;
        return this;
    }

    public BannerBanner setViewPagerIsScroll(boolean isScroll) {
        mIsScroll = isScroll;
        return this;
    }

    public BannerBanner setImages(List<?> imageUrls) {
        Log.d("wangwang", "setImages() ");
        mImageUrls = imageUrls;
        mCount = imageUrls.size();
        return this;
    }

    public void update(List<?> imageUrls, List<String> titles) {
        mTitles.clear();
        mTitles.addAll(titles);
        update(imageUrls);
    }

    public void update(List<?> imageUrls) {
        mImageUrls.clear();
        mImageViews.clear();
        mIndicatorImages.clear();
        mImageUrls.addAll(imageUrls);
        mCount = mImageUrls.size();
        start();
    }

    public void updateBannerStyle(int bannerStyle) {
        mIndicator.setVisibility(GONE);
        mNumIndicator.setVisibility(GONE);
        mNumIndicatorInside.setVisibility(GONE);
        mIndicatorInside.setVisibility(GONE);
        mBannerTitle.setVisibility(View.GONE);
        mTitleView.setVisibility(View.GONE);
        mBannerStyle = bannerStyle;
        start();
    }

    public BannerBanner start() {
        setBannerStyleUI();
        setImageList(mImageUrls);
        setData();
        return this;
    }

    private void setTitleStyleUI() {
        if (mTitles.size() != mImageUrls.size()) {
            throw new RuntimeException("[Banner] --> The number of titles and images is different");
        }
        if (mTitleBackground != -1) {
            mTitleView.setBackgroundColor(mTitleBackground);
        }
        if (mTitleHeight != -1) {
            mTitleView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTitleHeight));
        }
        if (mTitleTextColor != -1) {
            mBannerTitle.setTextColor(mTitleTextColor);
        }
        if (mTitleTextSize != -1) {
            mBannerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        }
        if (mTitles != null && mTitles.size() > 0) {
            mBannerTitle.setText(mTitles.get(0));
            mBannerTitle.setVisibility(View.VISIBLE);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    private void setBannerStyleUI() {
        int visibility = mCount > 1 ? View.VISIBLE : View.GONE;
        switch (mBannerStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                mIndicator.setVisibility(visibility);
                break;
            case BannerConfig.NUM_INDICATOR:
                mNumIndicator.setVisibility(visibility);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                mNumIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                mIndicator.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                mIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
        }
    }

    private void initImages() {
        mImageViews.clear();
        if (mBannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                mBannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE ||
                mBannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (mBannerStyle == BannerConfig.NUM_INDICATOR_TITLE) {
            mNumIndicatorInside.setText("1/" + mCount);
        } else if (mBannerStyle == BannerConfig.NUM_INDICATOR) {
            mNumIndicator.setText("1/" + mCount);
        }
    }

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            mBannerDefaultImage.setVisibility(VISIBLE);
            Log.d("wangwang", "The image data set is empty.");
            return;
        }
        mBannerDefaultImage.setVisibility(GONE);
        initImages();
        for (int i = 0; i <= mCount + 1; i++) {
            View imageView = null;
            if (mImageLoader != null) {
                imageView = mImageLoader.createImageView(mContext);
            }
            if (imageView == null) {
                imageView = new ImageView(mContext);
            }
            setScaleType(imageView);
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(mCount - 1);
            } else if (i == mCount + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            mImageViews.add(imageView);
            if (mImageLoader != null)
                mImageLoader.displayImage(mContext, url, imageView);
            else
                Log.e(TAG, "Please set images loader.");
        }
    }

    private void setScaleType(View imageView) {
        if (imageView instanceof ImageView) {
            ImageView view = ((ImageView) imageView);
            switch (mScaleType) {
                case 0:
                    view.setScaleType(ScaleType.CENTER);
                    break;
                case 1:
                    view.setScaleType(ScaleType.CENTER_CROP);
                    break;
                case 2:
                    view.setScaleType(ScaleType.CENTER_INSIDE);
                    break;
                case 3:
                    view.setScaleType(ScaleType.FIT_CENTER);
                    break;
                case 4:
                    view.setScaleType(ScaleType.FIT_END);
                    break;
                case 5:
                    view.setScaleType(ScaleType.FIT_START);
                    break;
                case 6:
                    view.setScaleType(ScaleType.FIT_XY);
                    break;
                case 7:
                    view.setScaleType(ScaleType.MATRIX);
                    break;
            }

        }
    }

    private void createIndicator() {
        mIndicatorImages.clear();
        mIndicator.removeAllViews();
        mIndicatorInside.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            mIndicatorImages.add(imageView);
            if (mBannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                    mBannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE)
                mIndicator.addView(imageView, params);
            else if (mBannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                mIndicatorInside.addView(imageView, params);
        }
    }


    private void setData() {
        mCurrentItem = 1;
        if (mPagerAdapter == null) {
            mPagerAdapter = new BannerPagerAdapter();
            mViewPager.addOnPageChangeListener(this);
        }
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setFocusable(true);
        mViewPager.setCurrentItem(1);
        if (mGravity != -1)
            mIndicator.setGravity(mGravity);
        if (mIsScroll && mCount > 1) {
            mViewPager.setScrollable(true);
        } else {
            mViewPager.setScrollable(false);
        }
        if (mIsAutoPlay) {
            startAutoPlay();
        }
    }


    public void startAutoPlay() {
        mHandler.removeCallbacks(task);
        mHandler.postDelayed(task, mDelayTime);
    }

    public void stopAutoPlay() {
        mHandler.removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (mCount > 1 && mIsAutoPlay) {
                mCurrentItem = mCurrentItem % (mCount + 1) + 1;
                if (mCurrentItem == 1) {
                    mViewPager.setCurrentItem(mCurrentItem, false);
                    mHandler.post(task);
                } else {
                    mViewPager.setCurrentItem(mCurrentItem);
                    mHandler.postDelayed(task, mDelayTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % mCount;
        if (realPosition < 0)
            realPosition += mCount;
        return realPosition;
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(mImageViews.get(position));
            View view = mImageViews.get(position);
            if (mListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onBannerClick(toRealPosition(position));
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        switch (state) {
            case 0:// No operation
                if (mCurrentItem == 0) {
                    mViewPager.setCurrentItem(mCount, false);
                } else if (mCurrentItem == mCount + 1) {
                    mViewPager.setCurrentItem(1, false);
                }
                break;
            case 1:// start Sliding
                if (mCurrentItem == mCount + 1) {
                    mViewPager.setCurrentItem(1, false);
                } else if (mCurrentItem == 0) {
                    mViewPager.setCurrentItem(mCount, false);
                }
                break;
            case 2:// end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(toRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentItem = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(toRealPosition(position));
        }
        if (mBannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                mBannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE ||
                mBannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            mIndicatorImages.get((mLastPosition - 1 + mCount) % mCount).setImageResource(mIndicatorUnselectedResId);
            mIndicatorImages.get((position - 1 + mCount) % mCount).setImageResource(mIndicatorSelectedResId);
            mLastPosition = position;
        }
        if (position == 0) position = mCount;
        if (position > mCount) position = 1;
        switch (mBannerStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                break;
            case BannerConfig.NUM_INDICATOR:
                mNumIndicator.setText(position + "/" + mCount);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                mNumIndicatorInside.setText(position + "/" + mCount);
                mBannerTitle.setText(mTitles.get(position - 1));
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                mBannerTitle.setText(mTitles.get(position - 1));
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                mBannerTitle.setText(mTitles.get(position - 1));
                break;
        }

    }

    /**
     * 废弃了旧版接口，新版的接口下标是从1开始，同时解决下标越界问题
     *
     * @param listener
     * @return
     */
    public BannerBanner setOnBannerListener(OnBannerListener listener) {
        mListener = listener;
        return this;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void releaseBanner() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public BannerViewPager getBannerViewPager() {
        return mViewPager;
    }
}