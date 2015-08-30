package com.farundorl.android.botchat;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.farundorl.android.botchat.Fragment.AccountFragment;
import com.farundorl.android.botchat.Fragment.TimeLineFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.left_drawer)
    NavigationView leftDrawer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer)
    DrawerLayout drawer;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initDrawer();

        if(savedInstanceState == null) {
            bindTimeline();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawer() {
        leftDrawer.setNavigationItemSelectedListener(menuItem -> {
            selectItem(menuItem.getItemId());
            return true;
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };

        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void selectItem(int id) {
        switch(id) {
            case R.id.timeline:
                changeFragment(TimeLineFragment.newInstance(), TimeLineFragment.TAG);
                break;
            case R.id.account:
                changeFragment(AccountFragment.newInstance(), AccountFragment.TAG);
                break;
            case R.id.about:
                break;
        }
        drawer.closeDrawers();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void bindTimeline() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, TimeLineFragment.newInstance(), TimeLineFragment.TAG)
                .commit();
    }

    public void changeFragment(Fragment fragment, String tag) {
        if(getFragmentManager().findFragmentByTag(tag) == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .commit();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
