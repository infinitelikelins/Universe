package com.bearya.robot.fairystory.ui.res;

import androidx.annotation.DrawableRes;

import com.bearya.robot.R;
import com.bearya.robot.base.card.BoatPropCard;
import com.bearya.robot.base.card.BulletPropCard;
import com.bearya.robot.base.card.FlutePropCard;
import com.bearya.robot.base.card.MagicWandPropCard;
import com.bearya.robot.base.card.NeedlesPropCard;
import com.bearya.robot.base.card.PropCard;
import com.bearya.robot.base.card.SoldierPropCard;
import com.bearya.robot.base.card.StickPropCard;
import com.bearya.robot.base.card.WaterPropCard;

/**
 * 卡片的数据资源
 */
public class CardResource {

    /**
     * 指令卡片的配音文件
     */
    public static String cardVoice(int cardType) {
        switch (cardType) {
            case CardType.ACTION_FORWARD: return "card/zh/p_forward.mp3";
            case CardType.ACTION_BACKWARD: return "card/zh/p_turn_back.mp3";
            case CardType.ACTION_LEFT: return "card/zh/p_turn_left.mp3";
            case CardType.ACTION_RIGHT: return "card/zh/p_turn_right.mp3";
            case CardType.ACTION_LOOP: return "card/zh/p_loop.mp3";
            case CardType.ACTION_CLOSURE: return "card/zh/p_closure.mp3";
            case CardType.ACTION_INSERT_LEFT:
            case CardType.ACTION_INSERT_RIGHT: return "card/zh/p_insert.mp3";
            default: return "";
        }
    }

    /**
     * 主指令卡片图片
     */
    @DrawableRes
    public static int parentImage(int cardType) {
        switch (cardType) {
            case CardType.ACTION_FORWARD: return R.mipmap.ic_forward;
            case CardType.ACTION_BACKWARD: return R.mipmap.ic_backward;
            case CardType.ACTION_LEFT: return R.mipmap.ic_left;
            case CardType.ACTION_RIGHT: return R.mipmap.ic_right;
            case CardType.ACTION_LOOP: return R.drawable.ic_loop;
            case CardType.ACTION_CLOSURE: return R.drawable.ic_closure;
            default: return R.mipmap.ic_add_no_focus_2;
        }
    }

}
