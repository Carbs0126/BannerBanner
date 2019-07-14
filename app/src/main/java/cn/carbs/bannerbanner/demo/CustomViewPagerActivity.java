package cn.carbs.bannerbanner.demo;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import cn.carbs.bannerbanner.App;
import cn.carbs.bannerbanner.R;
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.library.listener.OnBannerListener;
import cn.carbs.bannerbanner.loader.GlideImageLoader;


public class CustomViewPagerActivity extends AppCompatActivity implements OnBannerListener {
    BannerBanner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_custom_view_pager);

        banner = (BannerBanner) findViewById(R.id.banner);
        banner.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));
        //简单使用
        banner.setImages(App.images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void onBannerClick(int position) {

    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
