package com.bearya.robot.fairystory.ui.station;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bearya.robot.databinding.FragmentStationConfigSoundBinding;
import com.bearya.robot.fairystory.ui.adapter.SoundAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class StationSoundFragment extends Fragment {

    public static StationSoundFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        StationSoundFragment soundFragment = new StationSoundFragment();
        soundFragment.setArguments(bundle);
        return soundFragment;
    }
    private String type;
    private FragmentStationConfigSoundBinding bindView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = Objects.requireNonNull(getArguments()).getString("type", "station_");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindView = FragmentStationConfigSoundBinding.inflate(inflater, container, false);
        return bindView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        SoundAdapter adapter = new SoundAdapter(type);
        adapter.setOnItemClickListener((adapter1, view1, position) -> adapter.setSelectedIndex(position));
        bindView.recyclerView.setAdapter(adapter);

        StationLib lib = StationLib.getLibsFromAssets(requireContext());
        assert lib != null;
        for (Lib soundlib : lib.soundLibs.libList) {
            TabLayout.Tab tab = bindView.tabs.newTab().setText(soundlib.name);
            bindView.tabs.addTab(tab);
            if (tab.isSelected()) {
                adapter.setNewData(soundlib.items);
            }
        }
        bindView.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (Lib soundLib : lib.soundLibs.libList) {
                    if (TextUtils.equals(tab.getText(), soundLib.name)) {
                        adapter.setNewData(soundLib.items);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
