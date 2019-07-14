package cn.carbs.bannerbanner.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import cn.carbs.bannerbanner.App;
import cn.carbs.bannerbanner.R;
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.library.BannerConfig;
import cn.carbs.bannerbanner.loader.GlideImageLoader;


public class IndicatorPositionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    BannerBanner banner;
    Spinner spinnerPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_indicator_position);
        banner = (BannerBanner) findViewById(R.id.banner);
        spinnerPosition= (Spinner) findViewById(R.id.spinnerPosition);
        spinnerPosition.setOnItemSelectedListener(this);

        banner.setImages(App.images)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                banner.setIndicatorGravity(BannerConfig.Gravity.LEFT);
                break;
            case 1:
                banner.setIndicatorGravity(BannerConfig.Gravity.CENTER);
                break;
            case 2:
                banner.setIndicatorGravity(BannerConfig.Gravity.RIGHT);
                break;
        }
        banner.start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
