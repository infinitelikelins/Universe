package com.bearya.robot.fairystory.ui.res;

/**
 * 主题标志配置
 */
public class ThemeConfig {

    /**
     * 当前主题
     */
    public static String CURRENT_THEME = "";

    /**
     * 行星的秘密
     */
    public static final String THEME_QHXB = "qhxb";

    /**
     * 有趣的空间站
     */
    public static final String THEME_MHWH = "mhwh";

    /**
     * 了不起的航天员
     */
    public static final String  THEME_YXWH = "yxwh";

    /**
     * 飞天神州
     */
    public static final String THEME_CXTD = "cxtd";

    /**
     * 创想模块
     */
    public static final String THEME_FREE = "free";

    /**
     * 小贝行走的时候 ，播放的背景音乐
     */
    public static String travelBgm() {
        switch (CURRENT_THEME) {
            case THEME_MHWH: return "music/zh/ball_walk.mp3";
            case THEME_QHXB: return "music/zh/treasure_walk.mp3";
            case THEME_YXWH: return "music/zh/hero_walk.mp3";
            default: return "music/zh/travel_bg.mp3";
        }
    }

}