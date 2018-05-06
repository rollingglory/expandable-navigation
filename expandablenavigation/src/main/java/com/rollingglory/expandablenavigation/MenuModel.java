package com.rollingglory.expandablenavigation;

/**
 * Created by hidayatasep43 on 9/14/2017.
 */

public class MenuModel {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_GROUP = 1;

    public int id;
    public String name;
    public String iconUrl;
    public int icon;
    public int type;
    public boolean isExpand = false;

    public MenuModel() { }

    public MenuModel(int id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public MenuModel(int id, String name, int icon, int type) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public MenuModel(int id, String name, String iconUrl, int type) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.type = type;
    }

    public MenuModel(int id, String name, int icon, int type, boolean isExpand) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.isExpand = isExpand;
    }
}
