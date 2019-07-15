package cn.carbs.bannerbanner.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.carbs.bannerbanner.App;
import cn.carbs.bannerbanner.R;
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.library.config.BannerConfig;
import cn.carbs.bannerbanner.loader.GlideImageLoader;


public class CustomBannerActivity extends AppCompatActivity {
    BannerBanner banner1, banner2, banner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_custom_banner);
        banner1 = (BannerBanner) findViewById(R.id.banner1);
        banner2 = (BannerBanner) findViewById(R.id.banner2);
        banner3 = (BannerBanner) findViewById(R.id.banner3);

        banner1.setImages(App.images)
                .setImageLoader(new GlideImageLoader())
                .start();

        banner2.setImages(App.images)
                .setImageLoader(new GlideImageLoader())
                .start();

        banner3.setImages(App.images)
                .setBannerTitles(App.titles)
                .setBannerStyle(BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .start();
    }
}
