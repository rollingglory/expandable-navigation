package com.hidayatasep.expandablemenuandroid;

/**
 * Created by hidayatasep43 on 9/14/2017.
 */

public class MenuModel {

    public static final int TIPE_1 = 1;
    public static final int TIPE_2 = 2;
    public static final int TIPE_3 = 3;
    public static final int TIPE_4 = 4;
    public static final int TIPE_5 = 5;
    public static final int TIPE_6 = 6;

    public String nameMenu;
    public String imageUri;
    public int idDrawable;
    public int tipeMenu;
    public boolean isExpand= false;

    public MenuModel() {
    }

    public MenuModel(String nameMenu, int idDrawable) {
        this.nameMenu = nameMenu;
        this.idDrawable = idDrawable;
    }

    public MenuModel(String nameMenu, int idDrawable, int tipeMenu) {
        this.nameMenu = nameMenu;
        this.idDrawable = idDrawable;
        this.tipeMenu = tipeMenu;
        isExpand = false;
    }

    public MenuModel(String nameMenu, String imageUri, int tipeMenu) {
        this.nameMenu = nameMenu;
        this.imageUri = imageUri;
        this.tipeMenu = tipeMenu;
        isExpand = false;
    }

    public MenuModel(String nameMenu, int idDrawable, int tipeMenu, boolean isExpand) {
        this.nameMenu = nameMenu;
        this.idDrawable = idDrawable;
        this.tipeMenu = tipeMenu;
        this.isExpand = isExpand;
    }
}
