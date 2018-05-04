package com.hidayatasep.expandablemenuandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hidayatasep43 on 1/4/2018.
 * hidayatasep43@gmail.com
 */

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private HashMap<MenuModel, List<MenuModel>> mListDataChild;
    private List<MenuModel> mMenuModelList;
    private List<MenuModel> mMenuParentList;
    OnMenuListener mListener;

    public static interface OnMenuListener{
        public void onMenuClick(String menu);
    }

    public MenuAdapter(Context context, List<MenuModel> listDataHeader, HashMap<MenuModel, List<MenuModel>> listDataChild) {
        mContext = context;
        mListDataChild = listDataChild;
        mMenuModelList = new ArrayList<>();
        mMenuParentList = new ArrayList<>();
        mMenuModelList.addAll(listDataHeader);
        mMenuParentList.addAll(listDataHeader);
    }

    public void setMenuModelList(List<MenuModel> menuModelList) {
        mMenuModelList = menuModelList;
        mMenuParentList = menuModelList;
    }

    public void setListDataChild(HashMap<MenuModel, List<MenuModel>> listDataChild) {
        mListDataChild = listDataChild;
    }

    @Override
    public int getItemViewType(int position) {
        return mMenuModelList.get(position).tipeMenu;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == MenuModel.TIPE_1){
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_1, parent, false);
            return new ViewHolder1(viewItem);
        }else if(viewType == MenuModel.TIPE_2){
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_2, parent, false);
            return new ViewHolder1(viewItem);
        }else if(viewType == MenuModel.TIPE_3){
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_3, parent, false);
            return new ViewHolder2(viewItem);
        }else if(viewType == MenuModel.TIPE_4){
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_4, parent, false);
            return new ViewHolder3(viewItem);
        }else if(viewType == MenuModel.TIPE_5){
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_5, parent, false);
            return new ViewHolder4(viewItem);
        }else{
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_6, parent, false);
            return new ViewHolder4(viewItem);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        MenuModel menuModel = mMenuModelList.get(position);
        if(type == MenuModel.TIPE_1 || type == MenuModel.TIPE_2){
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            viewHolder1.mTextViewMenu.setText(menuModel.nameMenu);
            if(menuModel.imageUri == null || menuModel.imageUri.isEmpty()){
                viewHolder1.mImageViewMenu.setImageDrawable(mContext.getResources().getDrawable(menuModel.idDrawable));
            }else{
                /*GlideApp.with(mContext)
                        .load(menuModel.imageUri)
                        .into(viewHolder1.mImageViewMenu);*/
            }
        }else if(type == MenuModel.TIPE_4){
            ViewHolder3 viewHolder3 = (ViewHolder3) holder;
            viewHolder3.mTextViewMenu.setText(menuModel.nameMenu);
        }else if(type == MenuModel.TIPE_3){
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.mTextViewMenu.setText(menuModel.nameMenu);
        }else{
            ViewHolder4 viewHolder4 = (ViewHolder4) holder;
            viewHolder4.mTextViewMenu.setText(menuModel.nameMenu);
        }
    }

    @Override
    public int getItemCount() {
        return mMenuModelList.size();
    }

    public void setListener(OnMenuListener listener) {
        mListener = listener;
    }

    public void expandList(int position){
        MenuModel menuModel = mMenuModelList.get(position);
        List<MenuModel> childMenu = mListDataChild.get(menuModel);
        if(childMenu != null){
            int i = 1;
            for(MenuModel menuModel1: childMenu){
                mMenuModelList.add(position + i, menuModel1);
                i += 1;
            }
        }
        menuModel.tipeMenu = 3;
        notifyDataSetChanged();
    }

    public void collapseList(int position){
        MenuModel menuModel = mMenuModelList.get(position);
        List<MenuModel> childMenu = mListDataChild.get(menuModel);
        if(childMenu != null){
            int totalChild = childMenu.size();
            for(int i = 1; i <= totalChild; i++){
                mMenuModelList.remove(position + 1);
            }
        }
        menuModel.tipeMenu = 4;
        notifyDataSetChanged();
    }

    public boolean isMenuLogin(){
        boolean result = false;
        if(!mMenuModelList.isEmpty()){
            if(mMenuModelList.get(0).nameMenu.equals("Dasbor")){
                result = true;
            }
        }
        return result;
    }

    public int getSizeChildMenu(String menu){
        for(MenuModel menuModel: mMenuParentList){
            if(menuModel.nameMenu.equals(menu)){
                List<MenuModel> menuModels = mListDataChild.get(menuModel);
                return menuModels.size();
            }
        }
        return 0;
    }

    class ViewHolder1 extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.image_menu) ImageView mImageViewMenu;
        @BindView(R.id.tv_menu) TextView mTextViewMenu;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                String menu = mMenuModelList.get(getAdapterPosition()).nameMenu;
                mListener.onMenuClick(menu);
            }
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.tv_menu) TextView mTextViewMenu;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            collapseList(getAdapterPosition());
        }
    }

    class ViewHolder3 extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.tv_menu) TextView mTextViewMenu;

        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            expandList(getAdapterPosition());
        }
    }

    class ViewHolder4 extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.tv_menu) TextView mTextViewMenu;

        public ViewHolder4(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                String menu = mMenuModelList.get(getAdapterPosition()).nameMenu;
                mListener.onMenuClick(menu);
            }
        }
    }


}
