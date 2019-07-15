package cn.carbs.bannerbanner.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.bannerbanner.App;
import cn.carbs.bannerbanner.R;
import cn.carbs.bannerbanner.SampleAdapter;
import cn.carbs.bannerbanner.ViewUtil;
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.library.listener.OnBannerListener;
import cn.carbs.bannerbanner.library.transformer.base.AccordionTransformer;
import cn.carbs.bannerbanner.library.transformer.base.BackgroundToForegroundTransformer;
import cn.carbs.bannerbanner.library.transformer.base.CubeInTransformer;
import cn.carbs.bannerbanner.library.transformer.base.CubeOutTransformer;
import cn.carbs.bannerbanner.library.transformer.base.DefaultTransformer;
import cn.carbs.bannerbanner.library.transformer.base.DepthPageTransformer;
import cn.carbs.bannerbanner.library.transformer.base.FlipHorizontalTransformer;
import cn.carbs.bannerbanner.library.transformer.base.FlipVerticalTransformer;
import cn.carbs.bannerbanner.library.transformer.base.ForegroundToBackgroundTransformer;
import cn.carbs.bannerbanner.library.transformer.base.RotateDownTransformer;
import cn.carbs.bannerbanner.library.transformer.base.RotateUpTransformer;
import cn.carbs.bannerbanner.library.transformer.base.ScaleInOutTransformer;
import cn.carbs.bannerbanner.library.transformer.base.StackTransformer;
import cn.carbs.bannerbanner.library.transformer.base.TabletTransformer;
import cn.carbs.bannerbanner.library.transformer.base.ZoomInTransformer;
import cn.carbs.bannerbanner.library.transformer.base.ZoomOutSlideTransformer;
import cn.carbs.bannerbanner.library.transformer.base.ZoomOutTransformer;
import cn.carbs.bannerbanner.library.transformer.elegant.ElegantScaleInOutTransformer;
import cn.carbs.bannerbanner.library.view.BannerViewPager;
import cn.carbs.bannerbanner.loader.GlideImageLoader;


public class BannerAnimationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnBannerListener {

    BannerBanner banner;
    List<Class<? extends ViewPager.PageTransformer>> transformers = new ArrayList<>();

    public void initData() {
        transformers.add(DefaultTransformer.class);
        transformers.add(AccordionTransformer.class);
        transformers.add(BackgroundToForegroundTransformer.class);
        transformers.add(ForegroundToBackgroundTransformer.class);
        transformers.add(CubeInTransformer.class);// 兼容问题，慎用
        transformers.add(CubeOutTransformer.class);
        transformers.add(DepthPageTransformer.class);
        transformers.add(FlipHorizontalTransformer.class);
        transformers.add(FlipVerticalTransformer.class);
        transformers.add(RotateDownTransformer.class);
        transformers.add(RotateUpTransformer.class);
        transformers.add(ScaleInOutTransformer.class);
        transformers.add(StackTransformer.class);
        transformers.add(TabletTransformer.class);
        transformers.add(ZoomInTransformer.class);
        transformers.add(ZoomOutTransformer.class);
        transformers.add(ZoomOutSlideTransformer.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_banner_animation);
        initData();
        banner = findViewById(R.id.banner_1);

        BannerViewPager bannerViewPager = banner.getBannerViewPager();
        int pageMargin = ViewUtil.dp2px(this, 12);
        bannerViewPager.setPageMargin(pageMargin);
        int pagePaddingH = ViewUtil.dp2px(this, 32);
        bannerViewPager.setPadding(pagePaddingH, 0, pagePaddingH, 0);
        bannerViewPager.setClipToPadding(false);

        ListView listView = findViewById(R.id.list);
        String[] data = getResources().getStringArray(R.array.anim);
        listView.setAdapter(new SampleAdapter(this, data));
        listView.setOnItemClickListener(this);

        ElegantScaleInOutTransformer transformer = new ElegantScaleInOutTransformer(ViewUtil.getScreenWidth(this), pageMargin, pagePaddingH, App.images.size());
        banner.setImages(App.images)
                .setBannerTransformer(transformer)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stop();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        banner.setBannerTransformer(transformers.get(position));
    }

    @Override
    public void onBannerClick(int position) {
        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }
}
