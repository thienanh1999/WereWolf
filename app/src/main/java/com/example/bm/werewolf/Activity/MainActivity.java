package com.example.bm.werewolf.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.bm.werewolf.Fragment.AchievementFragment;
import com.example.bm.werewolf.Fragment.FriendsFragment;
import com.example.bm.werewolf.Fragment.LobbyFragment;
import com.example.bm.werewolf.Fragment.UserFragment;
import com.example.bm.werewolf.R;
import com.example.bm.werewolf.Service.EventHandler;
import com.example.bm.werewolf.Service.OnClearFromRecentService;
import com.example.bm.werewolf.Service.VoiceCallService;
import com.example.bm.werewolf.Utils.UserDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.agora.rtc.RtcEngine;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            VoiceCallService.rtcEngine = RtcEngine.create(getBaseContext(), "aed16bb36aa0410ba115391fb945692c", new EventHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }

        bottomNavigation.getMenu().getItem(0).getIcon().setAlpha(100);
        bottomNavigation.getMenu().getItem(1).getIcon().setAlpha(255);
        bottomNavigation.getMenu().getItem(2).getIcon().setAlpha(100);
        bottomNavigation.getMenu().getItem(3).getIcon().setAlpha(100);
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setSelectedItemId(R.id.item_play);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new LobbyFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigation.getMenu().getItem(0).getIcon().setAlpha(100);
        bottomNavigation.getMenu().getItem(1).getIcon().setAlpha(100);
        bottomNavigation.getMenu().getItem(2).getIcon().setAlpha(100);
        bottomNavigation.getMenu().getItem(3).getIcon().setAlpha(100);
        switch (item.getItemId()) {
            case R.id.item_friends:
                bottomNavigation.getMenu().getItem(0).getIcon().setAlpha(255);
                loadFragment(new FriendsFragment());
                break;
            case R.id.item_play:
                bottomNavigation.getMenu().getItem(1).getIcon().setAlpha(255);
                loadFragment(new LobbyFragment());
                break;
            case R.id.item_achievement:
                bottomNavigation.getMenu().getItem(2).getIcon().setAlpha(255);
                loadFragment(new AchievementFragment());
                break;
            case R.id.item_user:
                bottomNavigation.getMenu().getItem(3).getIcon().setAlpha(255);
                loadFragment(new UserFragment());
                break;
        }
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        OnClearFromRecentService.activity = this;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        RtcEngine.destroy();
        UserDatabase.getInstance().offlineStatus();
        super.onDestroy();
    }
}
