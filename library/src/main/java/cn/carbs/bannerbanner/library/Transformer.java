package cn.carbs.bannerbanner.library;

import androidx.viewpager.widget.ViewPager;

import cn.carbs.bannerbanner.library.transformer.DefaultTransformer;
import cn.carbs.bannerbanner.library.transformer.AccordionTransformer;
import cn.carbs.bannerbanner.library.transformer.BackgroundToForegroundTransformer;
import cn.carbs.bannerbanner.library.transformer.CubeInTransformer;
import cn.carbs.bannerbanner.library.transformer.CubeOutTransformer;
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

public class Transformer {
    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
