package com.bearya.robot.fairystory.ui.adapter;

import android.text.TextUtils;

import com.bearya.actionlib.utils.KVManager;
import com.bearya.robot.R;
import com.bearya.robot.base.ui.view.NiceImageView;
import com.bearya.robot.base.util.DebugUtil;
import com.bearya.robot.base.util.ResourceUtil;
import com.bearya.robot.fairystory.ui.station.LibItem;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Objects;

public class SoundAdapter extends BaseQuickAdapter<LibItem, BaseViewHolder> {

    private final String type;

    public SoundAdapter(String type) {
        super(R.layout.lib_item_view);
        this.type = type;
    }

    public void setSelectedIndex(int index) {
        String mp3 = Objects.requireNonNull(getItem(index)).getMp3();
        String key = "sound_" + type;

        DebugUtil.debug("key = " + key + " , mp3 = " + mp3);
        KVManager.getInstance().put(key, mp3);

        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    protected void convert(BaseViewHolder helper, LibItem item) {
        NiceImageView view = helper.getView(R.id.iconView);
        Glide.with(mContext).load(ResourceUtil.getMipmapId(item.getImage()))
                .thumbnail(0.1f)
                .into(view);

        boolean isSelected = TextUtils.equals(KVManager.getInstance().getString("sound_" + type), item.getMp3());
        view.setBorderColor(view.getContext().getResources().getColor(R.color.colorRed));
        view.setBorderWidth(isSelected ? 6 : 0);

        helper.setText(R.id.nameView, item.getName());
    }

}
