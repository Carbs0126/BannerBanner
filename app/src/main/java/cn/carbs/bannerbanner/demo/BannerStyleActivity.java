package cn.carbs.bannerbanner.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import cn.carbs.bannerbanner.App;
import cn.carbs.bannerbanner.R;
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.library.config.BannerConfig;
import cn.carbs.bannerbanner.loader.GlideImageLoader;


public class BannerStyleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    BannerBanner banner;
    Spinner spinnerStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_banner_style);
        banner = (BannerBanner) findViewById(R.id.banner);
        spinnerStyle = (Spinner) findViewById(R.id.spinnerStyle);
        spinnerStyle.setOnItemSelectedListener(this);

        //默认是CIRCLE_INDICATOR
        banner.setImages(App.images)
                .setBannerTitles(App.titles)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                banner.updateBannerStyle(BannerConfig.Style.NOT_INDICATOR);
                break;
            case 1:
                banner.updateBannerStyle(BannerConfig.Style.CIRCLE_INDICATOR);
                break;
            case 2:
                banner.updateBannerStyle(BannerConfig.Style.NUM_INDICATOR);
                break;
            case 3:
                banner.updateBannerStyle(BannerConfig.Style.NUM_INDICATOR_TITLE);
                break;
            case 4:
                banner.updateBannerStyle(BannerConfig.Style.CIRCLE_INDICATOR_TITLE);
                break;
            case 5:
                banner.updateBannerStyle(BannerConfig.Style.CIRCLE_INDICATOR_TITLE_INSIDE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
