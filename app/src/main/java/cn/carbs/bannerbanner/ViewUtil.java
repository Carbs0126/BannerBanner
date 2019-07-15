package cn.carbs.bannerbanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

public class ViewUtil {

    // 获取整个手机的屏幕高度
    public static int getFullScreenHeight(Activity activity) {
        if (activity == null
                || activity.getWindow() == null
                || activity.getWindow().getDecorView() == null
                || activity.getWindow().getDecorView().getRootView() == null) {
            return 0;
        }
        return activity.getWindow().getDecorView().getRootView().getHeight();
    }

    public static int getDecorViewHeight(Activity activity) {
        if (activity == null
                || activity.getWindow() == null
                || activity.getWindow().getDecorView() == null) {
            return 0;
        }
        return activity.getWindow().getDecorView().getHeight();
    }

    public static int getContentViewHeight(Activity activity) {
        if (activity == null
                || activity.getWindow() == null
                || activity.getWindow().getDecorView() == null) {
            return 0;
        }
        View decorView = activity.getWindow().getDecorView();
        View contentView = decorView.findViewById(android.R.id.content);
        if (contentView == null) {
            return 0;
        }
        return contentView.getHeight();
    }

    // 获取手机输入法上边缘与手机屏幕顶端的距离
    public static int getScreenHeightExcludeKeyboard(Activity activity) {
        if (activity == null
                || activity.getWindow() == null
                || activity.getWindow().getDecorView() == null) {
            return 0;
        }
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        return r.bottom;
    }

    // 获取view所在屏幕的Y位置
    public static int getViewYLocationInScreen(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    public static void hideStatusBar(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        fragment.getActivity().getWindow().setFlags(
                View.SYSTEM_UI_FLAG_VISIBLE,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void hideStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.getWindow().setFlags(
                View.SYSTEM_UI_FLAG_VISIBLE,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void showStatusBar(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        fragment.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void showStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
