package com.rollingglory.expandablemenuandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MenuAdapter.OnMenuListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_menu) RecyclerView mRecyclerViewMenu;

    MenuAdapter mMenuAdapter;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    List<MenuModel> listDataHeader;
    HashMap<MenuModel, List<MenuModel>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //set up view
        setSupportActionBar(toolbar);
        initNavigationDrawer();
    }

    //init menu drawer
    public void initNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //set up slider menu
        setUpSliderMenu();

        View header = navigationView.getHeaderView(0);

        mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    private void setUpSliderMenu() {
        listDataHeader = new ArrayList<MenuModel>();
        listDataChild = new HashMap<MenuModel, List<MenuModel>>();

        listDataHeader.add(new MenuModel("Dashboard", R.mipmap.ic_launcher, 1));

        for(int i = 0; i < 4; i++){
            MenuModel menuModel = new MenuModel("Menu " + (i + 1), R.mipmap.ic_launcher_round, 4);
            listDataHeader.add(menuModel);
            List<MenuModel> subMenuList = new ArrayList<>();
            for(int j = 0; j < 4; j++){
                MenuModel menuModelChildMenu;
                if(j == 3){
                    menuModelChildMenu = new MenuModel("Submenu " + (i + 1), R.drawable.ic_launcher_background, 2);
                }else{
                    menuModelChildMenu = new MenuModel("Submenu " + (i + 1), R.drawable.ic_launcher_background, 1);
                }
                subMenuList.add(menuModelChildMenu);
            }
            listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), subMenuList);
        }

        MenuModel menu5 = new MenuModel("Settings", R.mipmap.ic_launcher, 1);
        listDataHeader.add(menu5);
        MenuModel menu6 = new MenuModel("Bantuan", R.mipmap.ic_launcher, 1);
        listDataHeader.add(menu6);

        mMenuAdapter = new MenuAdapter(this, listDataHeader, listDataChild);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewMenu.setLayoutManager(layoutManager);
        mRecyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewMenu.setAdapter(mMenuAdapter);
        mMenuAdapter.setListener(this);

        mMenuAdapter.expandList(1);
    }

    @Override
    public void onMenuClick(String menu) {

    }
}
