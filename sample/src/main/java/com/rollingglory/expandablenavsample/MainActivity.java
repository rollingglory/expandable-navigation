package com.rollingglory.expandablenavsample;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rollingglory.expandablenavigation.ExpandableNavigation;
import com.rollingglory.expandablenavigation.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ExpandableNavigation navigationView;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    List<MenuModel> listDataHeader;
    HashMap<MenuModel, List<MenuModel>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.expandable_nav);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        //set up view
        setSupportActionBar(toolbar);
        navigationView.setDrawerLayout(drawerLayout);
        mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        initList();
        navigationView.setListMenu(listDataHeader, listDataChild);
    }

    private void initList() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add(new MenuModel(0, "Dashboard", R.mipmap.ic_launcher, MenuModel.TYPE_ITEM));

        for(int i = 0; i < 4; i++){
            MenuModel menuModel = new MenuModel(i, "Menu " + (i + 1), R.mipmap.ic_launcher_round, MenuModel.TYPE_GROUP);
            List<MenuModel> subMenuList = new ArrayList<>();
            for(int j = 0; j < 4; j++){
                MenuModel menuModelChildMenu = new MenuModel(j, "Submenu " + (i + 1), R.drawable.ic_launcher_background, MenuModel.TYPE_ITEM);
                subMenuList.add(menuModelChildMenu);
            }
            listDataHeader.add(menuModel);
            listDataChild.put(menuModel, subMenuList);
        }

        MenuModel menu5 = new MenuModel(2, "Settings", R.mipmap.ic_launcher, MenuModel.TYPE_ITEM);
        listDataHeader.add(menu5);
        MenuModel menu6 = new MenuModel(3, "Bantuan", R.mipmap.ic_launcher, MenuModel.TYPE_ITEM);
        listDataHeader.add(menu6);
    }

}
