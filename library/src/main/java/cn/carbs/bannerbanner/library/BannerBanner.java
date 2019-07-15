package cn.carbs.bannerbanner.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
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

import java.util.ArrayList;
import java.util.List;

import cn.carbs.bannerbanner.library.listener.OnBannerListener;
import cn.carbs.bannerbanner.library.loader.ImageLoaderInterface;
import cn.carbs.bannerbanner.library.view.BannerViewPager;


public class BannerBanner extends FrameLayout implements ViewPager.OnPageChangeListener {

    public static final String TAG = "BannerBanner";
    public static final int HANDLE_MESSAGE_WHAT_TASK = 1;

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
    private OnBannerListener mListener;
    private DisplayMetrics mDisplayMetrics;

    private List mImageUrls;
    private List<String> mTitles;
    private List<View> mImageViews;
    private List<ImageView> mIndicatorImages;

    private Handler mInternalHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLE_MESSAGE_WHAT_TASK) {
                if (mCount > 1 && mIsAutoPlay) {
                    mCurrentItem = mCurrentItem % (mCount + 1) + 1;
                    if (mCurrentItem == 1) {
                        mViewPager.setCurrentItem(mCurrentItem, false);
                        postDelayToUpdateCurrentItem();
                    } else {
                        mViewPager.setCurrentItem(mCurrentItem);
                        postDelayToUpdateCurrentItem();
                    }
                }
            }
        }
    };

    private int mIndicatorMargin = BannerConfig.Banner.PADDING_SIZE;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorSize;
    private int mBannerBackgroundImage;
    private int mBannerStyle = BannerConfig.Style.CIRCLE_INDICATOR;
    private int mDelayTime = BannerConfig.Banner.TIME;
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
    // TODO 与imageview统一
    private int mScaleType = 1;
    private boolean mIsAutoPlay = BannerConfig.Banner.IS_AUTO_PLAY;
    private boolean mIsScroll = BannerConfig.Banner.IS_SCROLL;

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
        mBannerDefaultImage.setImageResource(mBannerBackgroundImage);
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
                R.styleable.BannerBanner_bb_indicator_margin, BannerConfig.Banner.PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_indicator_drawable_selected, R.drawable.banner_banner_gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_indicator_drawable_unselected, R.drawable.banner_banner_white_radius);
        mScaleType = typedArray.getInt(
                R.styleable.BannerBanner_bb_image_scale_type, mScaleType);
        mDelayTime = typedArray.getInt(
                R.styleable.BannerBanner_bb_delay_time, BannerConfig.Banner.TIME);
        mIsAutoPlay = typedArray.getBoolean(
                R.styleable.BannerBanner_bb_is_auto_play, BannerConfig.Banner.IS_AUTO_PLAY);
        mTitleBackground = typedArray.getColor(
                R.styleable.BannerBanner_bb_title_background, BannerConfig.Title.TITLE_BACKGROUND);
        mTitleHeight = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_title_height, BannerConfig.Title.TITLE_HEIGHT);
        mTitleTextColor = typedArray.getColor(
                R.styleable.BannerBanner_bb_title_text_color, BannerConfig.Title.TITLE_TEXT_COLOR);
        mTitleTextSize = typedArray.getDimensionPixelSize(
                R.styleable.BannerBanner_bb_title_text_size, BannerConfig.Title.TITLE_TEXT_SIZE);
        mLayoutResId = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_banner_layout, mLayoutResId);
        mBannerBackgroundImage = typedArray.getResourceId(
                R.styleable.BannerBanner_bb_banner_default_image, 0);
        typedArray.recycle();
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
            case BannerConfig.Gravity.LEFT:
                mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.Gravity.CENTER:
                mGravity = Gravity.CENTER;
                break;
            case BannerConfig.Gravity.RIGHT:
                mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }

    public BannerBanner setBannerTransformer(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(TAG, "Please set the PageTransformer class");
        }
        return this;
    }

    public BannerBanner setBannerTransformer(ViewPager.PageTransformer transformer) {
        setPageTransformer(true, transformer);
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

    public void stop() {
        if (mInternalHandler != null) {
            mInternalHandler.removeMessages(HANDLE_MESSAGE_WHAT_TASK);
        }
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
            case BannerConfig.Style.CIRCLE_INDICATOR:
                mIndicator.setVisibility(visibility);
                break;
            case BannerConfig.Style.NUM_INDICATOR:
                mNumIndicator.setVisibility(visibility);
                break;
            case BannerConfig.Style.NUM_INDICATOR_TITLE:
                mNumIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case BannerConfig.Style.CIRCLE_INDICATOR_TITLE:
                mIndicator.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE:
                mIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
        }
    }

    private void initImages() {
        mImageViews.clear();
        if (mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR ||
                mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR_TITLE ||
                mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (mBannerStyle == BannerConfig.Style.NUM_INDICATOR_TITLE) {
            mNumIndicatorInside.setText("1/" + mCount);
        } else if (mBannerStyle == BannerConfig.Style.NUM_INDICATOR) {
            mNumIndicator.setText("1/" + mCount);
        }
    }

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            mBannerDefaultImage.setVisibility(VISIBLE);
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
            if (mImageLoader != null) {
                mImageLoader.displayImage(mContext, url, imageView);
            } else {
                Log.e(TAG, "Please set images loader.");
            }
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
            if (mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR ||
                    mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR_TITLE) {
                mIndicator.addView(imageView, params);
            } else if (mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE) {
                mIndicatorInside.addView(imageView, params);
            }
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
        postDelayToUpdateCurrentItem();
    }

    private void postDelayToUpdateCurrentItem() {
        if (mInternalHandler == null) {
            return;
        }
        mInternalHandler.removeMessages(HANDLE_MESSAGE_WHAT_TASK);
        mInternalHandler.sendEmptyMessageDelayed(HANDLE_MESSAGE_WHAT_TASK, mDelayTime);
    }

    public void stopAutoPlay() {
        if (mInternalHandler == null) {
            return;
        }
        mInternalHandler.removeMessages(HANDLE_MESSAGE_WHAT_TASK);
    }

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
        if (realPosition < 0) {
            realPosition += mCount;
        }
        return realPosition;
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews == null ? 0 : mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            if (mImageViews == null) {
                return 0;
            }
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
        if (mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR ||
                mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR_TITLE ||
                mBannerStyle == BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE) {
            mIndicatorImages.get((mLastPosition - 1 + mCount) % mCount).setImageResource(mIndicatorUnselectedResId);
            mIndicatorImages.get((position - 1 + mCount) % mCount).setImageResource(mIndicatorSelectedResId);
            mLastPosition = position;
        }
        if (position == 0) {
            position = mCount;
        }
        if (position > mCount) {
            position = 1;
        }
        switch (mBannerStyle) {
            case BannerConfig.Style.CIRCLE_INDICATOR:
                break;
            case BannerConfig.Style.NUM_INDICATOR:
                mNumIndicator.setText(position + "/" + mCount);
                break;
            case BannerConfig.Style.NUM_INDICATOR_TITLE:
                mNumIndicatorInside.setText(position + "/" + mCount);
                mBannerTitle.setText(mTitles.get(position - 1));
                break;
            case BannerConfig.Style.CIRCLE_INDICATOR_TITLE:
                mBannerTitle.setText(mTitles.get(position - 1));
                break;
            case BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE:
                mBannerTitle.setText(mTitles.get(position - 1));
                break;
        }
    }

    public BannerBanner setOnBannerListener(OnBannerListener listener) {
        mListener = listener;
        return this;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void releaseBanner() {
        if (mInternalHandler == null) {
            return;
        }
        mInternalHandler.removeMessages(HANDLE_MESSAGE_WHAT_TASK);
    }

    public BannerViewPager getBannerViewPager() {
        return mViewPager;
    }

    // todo

    /**
     * 设置当page处于第0个时，是否可以向左滑动
     *
     * @param canSlideToLeftOfPage0
     */
    public void setCanSlideToLeftOfPage0(boolean canSlideToLeftOfPage0) {

    }
}
