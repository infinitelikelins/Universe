package com.bearya.robot.fairystory.ui.station;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bearya.actionlib.utils.KVManager;
import com.bearya.robot.R;
import com.bearya.robot.base.play.TimeAction;
import com.bearya.robot.databinding.FragmentStationConfigActionBinding;

import java.util.Map;
import java.util.Objects;

public class StationActionFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {


    private static final Map<Integer, Integer> ACTION_IMAGE_MAP = new ArrayMap<>();

    static {
        ACTION_IMAGE_MAP.put(ActionSetDialog.ACTION_DOUBLE_HAND, R.mipmap.ic_shake_hand_select);
        ACTION_IMAGE_MAP.put(ActionSetDialog.ACTION_LEFT_HAND, R.mipmap.ic_shake_left_hand_select);
        ACTION_IMAGE_MAP.put(ActionSetDialog.ACTION_RIGHT_HAND, R.mipmap.ic_shake_right_hand_select);
        ACTION_IMAGE_MAP.put(ActionSetDialog.ACTION_SHAKE_HEADER, R.mipmap.ic_shake_head_select);
        ACTION_IMAGE_MAP.put(ActionSetDialog.ACTION_SHAKE_HEADER_TO_LEFT, R.mipmap.ic_shake_head_to_left_select);
        ACTION_IMAGE_MAP.put(ActionSetDialog.ACTION_SHAKE_HEADER_TO_RIGHT, R.mipmap.ic_shake_head_to_right_select);
    }

    public static StationActionFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        StationActionFragment actionFragment = new StationActionFragment();
        actionFragment.setArguments(bundle);
        return actionFragment;
    }

    private String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = Objects.requireNonNull(getArguments()).getString("type", "station_");
    }

    private FragmentStationConfigActionBinding bindView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindView = FragmentStationConfigActionBinding.inflate(inflater, container, false);
        return bindView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindView.box1.setOnClickListener(this);
        bindView.box2.setOnClickListener(this);
        bindView.box3.setOnClickListener(this);
        bindView.box1.setOnLongClickListener(this);
        bindView.box2.setOnLongClickListener(this);
        bindView.box3.setOnLongClickListener(this);

        int action1 = KVManager.getInstance().getInt("action_" + type + "1", 0);
        int action2 = KVManager.getInstance().getInt("action_" + type + "2", 0);
        int action3 = KVManager.getInstance().getInt("action_" + type + "3", 0);
        setActionToView(new TimeAction(action1, 3), bindView.box1, bindView.tvSecond1);
        setActionToView(new TimeAction(action2, 3), bindView.box2, bindView.tvSecond2);
        setActionToView(new TimeAction(action3, 3), bindView.box3, bindView.tvSecond3);
    }

    @Override
    public void onClick(View view) {
        ActionSetDialog dialog = new ActionSetDialog(requireActivity());
        dialog.setListener(action -> {
            TimeAction timeAction = new TimeAction(action, 3);
            if (view.getId() == bindView.box1.getId()) {
                KVManager.getInstance().put("action_" + type + "1", action);
                setActionToView(timeAction, bindView.box1, bindView.tvSecond1);
            } else if (view.getId() == bindView.box2.getId()) {
                KVManager.getInstance().put("action_" + type + "2", action);
                setActionToView(timeAction, bindView.box2, bindView.tvSecond2);
            } else if (view.getId() == bindView.box3.getId()) {
                KVManager.getInstance().put("action_" + type + "3", action);
                setActionToView(timeAction, bindView.box3, bindView.tvSecond3);
            }
        });
        dialog.show();
    }

    private void setActionToView(TimeAction action, ImageView view, TextView textView) {
        if (action.getAction() == 0) return;
        view.setImageResource(ACTION_IMAGE_MAP.get(action.getAction()));
        textView.setVisibility(View.VISIBLE);
        textView.setText(String.format(getString(R.string.some_second), action.getTime()));
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == bindView.box1.getId()) {
            KVManager.getInstance().remove("action_" + type + "1");
            bindView.box1.setImageResource(R.mipmap.ic_add_action);
            bindView.tvSecond1.setVisibility(View.INVISIBLE);
        } else if (view.getId() == bindView.box2.getId()) {
            KVManager.getInstance().remove("action_" + type + "2");
            bindView.box2.setImageResource(R.mipmap.ic_add_action);
            bindView.tvSecond2.setVisibility(View.INVISIBLE);
        } else if (view.getId() == bindView.box3.getId()) {
            KVManager.getInstance().remove("action_" + type + "3");
            bindView.box3.setImageResource(R.mipmap.ic_add_action);
            bindView.tvSecond3.setVisibility(View.INVISIBLE);
        }
        return true;
    }

}