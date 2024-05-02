package com.bearya.robot.fairystory.ui.res;

/**
 * 这是卡片指令的资源
 */
public interface CardType {

    /**
     * 默认卡
     */
    int ACTION_DEFAULT = -1;
    /**
     * 左插入卡
     */
    int ACTION_INSERT_LEFT = -2;
    /**
     * 左插入卡
     */
    int ACTION_INSERT_RIGHT = -3;
    /**
     * 前进卡
     */
    int ACTION_FORWARD = 12703;
    /**
     * 后退卡
     */
    int ACTION_BACKWARD = 12704;
    /**
     * 左转卡
     */
    int ACTION_LEFT = 12705;
    /**
     * 右转卡
     */
    int ACTION_RIGHT = 12706;
    /**
     * 并列卡
     */
    int ACTION_PARALLEL = 12707;
    /**
     * 道具卡片 - 无敌号铁皮船
     */
    int ACTION_BOAT = 12708;
    /**
     * 道具卡片 - 顶呱呱魔法棒
     */
    int ACTION_MAGIC = 12709;
    /**
     * 道具卡片 - 葵花战士
     */
    int ACTION_SOLDIER = 12710;
    /**
     * 道具卡片 - 妈妈牌毛衣针
     */
    int ACTION_NEEDLES = 12711;

    /**
     * 道具卡片 - 神奇变身水
     */
    int ACTION_WATER = 12712;

    /**
     * 道具卡片 - 逍遥跳舞笛
     */
    int ACTION_FLUTE = 12713;
    /**
     * 道具卡片 - 强力粘粘弹
     */
    int ACTION_BULLET = 12714;
    /**
     * 道具卡片 - 萌萌哒逗猫棒
     */
    int ACTION_STICK = 12715;
    /**
     * 语法指令 - 循环开始
     */
    int ACTION_LOOP = 60164;
    /**
     * 语法指令 - 循环结束
     */
    int ACTION_CLOSURE = 60165;

}
