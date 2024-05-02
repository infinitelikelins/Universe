package com.bearya.robot.fairystory.ui.res;

/**
 * 开始介绍动画/游戏结束动画 播放的帧动画时间间隔
 * 数值越小 ， 动画的播放速度越快
 */
public interface IntroduceTime {

    /**
     * 帧动画时间间隔 -- 英雄舞会 -- 结束 -- 成功
     */
    int heroEndTime = 10;

    /**
     * 帧动画时间间隔 -- 英雄舞会 -- 结束 -- 失败
     */
    int heroFailEndTime = 10;

    /**
     * 帧动画时间间隔 -- 奇幻寻宝 -- 结束 -- 成功 -- 海底世界
     */
    int seafloorEndTime = 15;

    /**
     * 帧动画时间间隔 -- 奇幻寻宝 -- 结束 -- 失败 -- 海底世界
     */
    int seafloorFailEndTime = 5;

    /**
     * 帧动画时间间隔 -- 梦幻舞会 -- 结束 -- 成功
     */
    int ballEndTime = 20;

    /**
     * 帧动画时间间隔 -- 梦幻舞会 -- 结束 -- 失败
     */
    int ballFailEndTime = 10;

}