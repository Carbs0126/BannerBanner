package cn.carbs.bannerbanner.library;

public class BannerConfig {

    /**
     * indicator style
     */
    public static class Style {
        public static final int NOT_INDICATOR = 0;
        public static final int CIRCLE_INDICATOR = 1;
        public static final int NUM_INDICATOR = 2;
        public static final int NUM_INDICATOR_TITLE = 3;
        public static final int CIRCLE_INDICATOR_TITLE = 4;
        public static final int CIRCLE_INDICATOR_TITLE_INSIDE = 5;
    }

    /**
     * indicator gravity
     */
    public static class Gravity {
        public static final int LEFT = 5;
        public static final int CENTER = 6;
        public static final int RIGHT = 7;
    }

    /**
     * banner
     */
    public static class Banner {
        public static final int PADDING_SIZE = 5;
        public static final int TIME = 3000;
        public static final boolean IS_AUTO_PLAY = true;
        public static final boolean IS_SCROLL = true;
    }

    /**
     * title style
     */
    public static class Title {
        public static final int TITLE_BACKGROUND = -1;
        public static final int TITLE_HEIGHT = -1;
        public static final int TITLE_TEXT_COLOR = -1;
        public static final int TITLE_TEXT_SIZE = -1;
    }

}
