package com.rollingglory.expandablenavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mhasby on 5/6/2018.
 * mhmmd.hsby@gmail.com
 */
public class ExpandableNavigation extends FrameLayout implements MenuAdapter.OnMenuListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerViewMenu;
    private MenuAdapter mMenuAdapter;

    private List<MenuModel> mListDataHeader;
    private HashMap<MenuModel, List<MenuModel>> mListDataChild;

    public ExpandableNavigation(Context context) {
        super(context, null);

        initView();
        initAdapter();
    }

    public ExpandableNavigation(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        initView();
        initAdapter();
    }

    public ExpandableNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        initAdapter();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.navigation_expandable, this);

        mNavigationView = findViewById(R.id.nav_view);
        mRecyclerViewMenu = findViewById(R.id.navigation_menu);

        if (mDrawerLayout != null) {
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }
    }

    public void setListMenu(List<MenuModel> listDataHeader, HashMap<MenuModel, List<MenuModel>> listDataChild) {
        if (mListDataHeader == null) mListDataHeader = new ArrayList<>();
        if (mListDataChild == null) mListDataChild = new HashMap<>();
        mListDataHeader.clear();
        mListDataChild.clear();

        mListDataHeader.addAll(listDataHeader);
        mListDataChild.putAll(listDataChild);

        if (mMenuAdapter != null) {
            mMenuAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        if (mListDataHeader == null) mListDataHeader = new ArrayList<>();
        if (mListDataChild == null) mListDataChild = new HashMap<>();

        mMenuAdapter = new MenuAdapter(getContext(), mListDataHeader);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewMenu.setLayoutManager(layoutManager);
        mRecyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewMenu.setAdapter(mMenuAdapter);
        mMenuAdapter.setListener(this);
    }

    public void expandList(MenuModel selectedMenu){
        int position = mListDataHeader.indexOf(selectedMenu);
        if (position < 0) return;
        MenuModel menuModel = mListDataHeader.get(position);
        List<MenuModel> childMenu = mListDataChild.get(menuModel);
        if(childMenu != null){
            int i = 1;
            for(MenuModel menuModel1: childMenu){
                mListDataHeader.add(position + i, menuModel1);
                i += 1;
            }
        }

        menuModel.isExpand = true;
        mMenuAdapter.notifyDataSetChanged();
    }

    public void collapseList(MenuModel selectedMenu){
        int position = mListDataHeader.indexOf(selectedMenu);
        if (position < 0) return;
        MenuModel menuModel = mListDataHeader.get(position);
        List<MenuModel> childMenu = mListDataChild.get(menuModel);
        if(childMenu != null){
            int totalChild = childMenu.size();
            for(int i = 1; i <= totalChild; i++){
                mListDataHeader.remove(position + 1);
            }
        }
        menuModel.isExpand = false;
        mMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMenuClick(MenuModel menuModel) {
        if (menuModel.type == MenuModel.TYPE_ITEM) {

        } else if (menuModel.type == MenuModel.TYPE_GROUP) {
            if (menuModel.isExpand) {
                collapseList(menuModel);
            } else {
                expandList(menuModel);
            }
        }
    }
}
