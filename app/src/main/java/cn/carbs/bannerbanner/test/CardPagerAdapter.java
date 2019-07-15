package cn.carbs.bannerbanner.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.bannerbanner.R;

public class CardPagerAdapter extends PagerAdapter {

    private List<FrameLayout> mViews;
    private List<String> mData;
    private Context context;

    public CardPagerAdapter(Context context, List<String> list) {
        this.context = context;
        mViews = new ArrayList<FrameLayout>();
        this.mData = list;
        for (int i = 0; i < list.size(); i++) {
            mViews.add(null);
        }
    }

    @Override
    public int getCount() {
        return mData.size() * 100;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_card, container, false);
        container.addView(view);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.ll_container);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        TextView tv_select = (TextView) view.findViewById(R.id.tv_select);
        int select = position < mData.size() ? position : (position % mData.size());
        Log.i("info", "位置：" + select + "  " + position + "  " + (position / mData.size()) + "  " + (position % mData.size()));
        tv_select.setText("" + select);
        mViews.set(select, frameLayout);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        int select = position < mData.size() ? position : (position % mData.size());
        mViews.set(select, null);
    }

}