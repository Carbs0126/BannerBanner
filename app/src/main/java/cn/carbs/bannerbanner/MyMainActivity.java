package cn.carbs.bannerbanner;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.bannerbanner.library.transformer.elegant.ElegantScaleInOutTransformer;
import cn.carbs.bannerbanner.test.CardPagerAdapter;
import cn.carbs.bannerbanner.test.TransFormer;

public class MyMainActivity extends AppCompatActivity {


    private ViewPager vp;
    private CardPagerAdapter cardPagerAdapter;
    private List<String> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main_my);
        // good
//        initView();
        initView2();
    }

    /*private void initView() {
        vp = (ViewPager) this.findViewById(R.id.vp);
        lists = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            lists.add("");
        }
        cardPagerAdapter = new CardPagerAdapter(this, lists);
        vp.setOffscreenPageLimit(3);
        vp.setPageMargin(36);
//        vp.setPageTransformer(false, new TransFormer());
        vp.setPageTransformer(true, new TransFormer());
        vp.setAdapter(cardPagerAdapter);
//        vp.setCurrentItem(cardPagerAdapter.getCount() / 2);
        vp.setCurrentItem(0);
    }*/

    private void initView2() {
        vp = (ViewPager) this.findViewById(R.id.vp);
        lists = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            lists.add("");
        }
        cardPagerAdapter = new CardPagerAdapter(this, lists);
        vp.setOffscreenPageLimit(3);
        vp.setPageMargin(36);
//        vp.setPageTransformer(false, new TransFormer());
//        vp.setPageTransformer(true, new TransFormer());

        ElegantScaleInOutTransformer transformer = new ElegantScaleInOutTransformer(ViewUtil.getScreenWidth(this), 36, 96, App.images.size());
        vp.setPageTransformer(true, transformer);


        vp.setAdapter(cardPagerAdapter);
//        vp.setCurrentItem(cardPagerAdapter.getCount() / 2);
        vp.setCurrentItem(0);
    }

}
