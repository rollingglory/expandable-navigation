package com.rollingglory.expandablemenuandroid;

/**
 * Created by hidayatasep43 on 9/14/2017.
 */

public class MenuModel {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_GROUP = 1;

    public String name;
    public String iconUrl;
    public int icon;
    public int type;
    public boolean isExpand = false;

    public MenuModel() { }

    public MenuModel(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public MenuModel(String name, int icon, int type) {
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public MenuModel(String name, String iconUrl, int type) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.type = type;
    }

    public MenuModel(String name, int icon, int type, boolean isExpand) {
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.isExpand = isExpand;
    }
}
