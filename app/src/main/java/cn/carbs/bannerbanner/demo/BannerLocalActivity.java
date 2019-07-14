package cn.carbs.bannerbanner.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.bannerbanner.R;
import cn.carbs.bannerbanner.library.BannerBanner;
import cn.carbs.bannerbanner.loader.GlideImageLoader;

public class BannerLocalActivity extends AppCompatActivity {

    BannerBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_banner_local);
        initView();
    }

    private void initView() {
        banner = (BannerBanner) findViewById(R.id.banner);
        //本地图片数据（资源文件）
        List<Integer> list=new ArrayList<>();
        list.add(R.drawable.b1);
        list.add(R.drawable.b2);
        list.add(R.drawable.b3);



        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
    }
}
