package com.example.bm.werewolf.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.bm.werewolf.Adapter.AchievementELVAdapter;
import com.example.bm.werewolf.Model.AchievementItemModel;
import com.example.bm.werewolf.Model.AchievementModel;
import com.example.bm.werewolf.Database.DatabaseManager;
import com.example.bm.werewolf.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievedFragment extends Fragment {
    private static final String TAG = "AchievedFragment";

    Unbinder unbinder;
    @BindView(R.id.elv_achievement)
    ExpandableListView elvAchievement;
    Context context;

    public AchievedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_achieved, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();

        List<AchievementModel> achievementModels1 = DatabaseManager.getInstance(context).getListAchievement();
        List<AchievementModel> achievementModels = new ArrayList<>();
        for (AchievementModel a: achievementModels1) {
            if (a.progress == a.total)
                achievementModels.add(a);
        }

        List<AchievementItemModel> achievementItemModels1 = DatabaseManager.getInstance(context).getListAchievementItem();
        Log.d(TAG, "onCreateView: " + achievementItemModels1.size());
        List<AchievementItemModel> achievementItemModels = new ArrayList<>();
        for (AchievementItemModel a: achievementItemModels1) {
            if (achievementModels1.get(a.group).progress == achievementModels1.get(a.group).total)
                achievementItemModels.add(a);
        }

        AchievementELVAdapter achievementELVAdapter = new AchievementELVAdapter(achievementModels, achievementItemModels, context);

        elvAchievement.setAdapter(achievementELVAdapter);

        return view;
    }

}
