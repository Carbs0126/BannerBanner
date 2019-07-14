package cn.carbs.bannerbanner.demo;

import android.os.Bundle;
import android.util.Log;
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
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.library.listener.OnBannerListener;
import cn.carbs.bannerbanner.library.transformer.AccordionTransformer;
import cn.carbs.bannerbanner.library.transformer.BackgroundToForegroundTransformer;
import cn.carbs.bannerbanner.library.transformer.CubeInTransformer;
import cn.carbs.bannerbanner.library.transformer.CubeOutTransformer;
import cn.carbs.bannerbanner.library.transformer.DefaultTransformer;
import cn.carbs.bannerbanner.library.transformer.DepthPageTransformer;
import cn.carbs.bannerbanner.library.transformer.FlipHorizontalTransformer;
import cn.carbs.bannerbanner.library.transformer.FlipVerticalTransformer;
import cn.carbs.bannerbanner.library.transformer.ForegroundToBackgroundTransformer;
import cn.carbs.bannerbanner.library.transformer.RotateDownTransformer;
import cn.carbs.bannerbanner.library.transformer.RotateUpTransformer;
import cn.carbs.bannerbanner.library.transformer.ScaleInOutTransformer;
import cn.carbs.bannerbanner.library.transformer.StackTransformer;
import cn.carbs.bannerbanner.library.transformer.TabletTransformer;
import cn.carbs.bannerbanner.library.transformer.ZoomInTransformer;
import cn.carbs.bannerbanner.library.transformer.ZoomOutSlideTransformer;
import cn.carbs.bannerbanner.library.transformer.ZoomOutTransformer;
import cn.carbs.bannerbanner.library.view.BannerViewPager;
import cn.carbs.bannerbanner.loader.GlideImageLoader;


public class BannerAnimationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnBannerListener {

    BannerBanner banner;
    List<Class<? extends ViewPager.PageTransformer>> transformers=new ArrayList<>();
    public void initData(){
        transformers.add(DefaultTransformer.class);
        transformers.add(AccordionTransformer.class);
        transformers.add(BackgroundToForegroundTransformer.class);
        transformers.add(ForegroundToBackgroundTransformer.class);
        transformers.add(CubeInTransformer.class);//兼容问题，慎用
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
        banner = (BannerBanner) findViewById(R.id.banner_1);

        BannerViewPager bannerViewPager = banner.getBannerViewPager();
        bannerViewPager.setPageMargin(20);
        bannerViewPager.setPadding(60, 0 , 60, 0);
        bannerViewPager.setClipToPadding(false);

        ListView listView = (ListView) findViewById(R.id.list);
        String[] data = getResources().getStringArray(R.array.anim);
        listView.setAdapter(new SampleAdapter(this, data));
        listView.setOnItemClickListener(this);

        if (App.images == null) {
            Log.d("wangwang", "app.images == null");
            return;
        }
        for (Object image : App.images) {
            Log.d("wangwang", "image : " + image);
        }
        banner.setImages(App.images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        banner.setBannerAnimation(transformers.get(position));
    }

    @Override
    public void onBannerClick(int position) {
        Log.d("wangwang", "click");
        Toast.makeText(getApplicationContext(),"你点击了："+position,Toast.LENGTH_SHORT).show();
    }
}
