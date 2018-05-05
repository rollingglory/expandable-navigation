package com.rollingglory.expandablemenuandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hidayatasep43 on 1/4/2018.
 * hidayatasep43@gmail.com
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_GROUP_EXPANDED = 1;
    private static final int TYPE_GROUP_COLLAPSED = 2;

    private Context mContext;
    private HashMap<MenuModel, List<MenuModel>> mListDataChild;
    private List<MenuModel> mMenuModelList;
    private List<MenuModel> mMenuParentList;
    private OnMenuListener mListener;

    public interface OnMenuListener{
        void onMenuClick(String menu);
    }

    MenuAdapter(Context context, List<MenuModel> listDataHeader, HashMap<MenuModel, List<MenuModel>> listDataChild) {
        mContext = context;
        mListDataChild = listDataChild;
        mMenuModelList = new ArrayList<>();
        mMenuParentList = new ArrayList<>();
        mMenuModelList.addAll(listDataHeader);
        mMenuParentList.addAll(listDataHeader);
    }

    void setMenuModelList(List<MenuModel> menuModelList) {
        mMenuModelList = menuModelList;
        mMenuParentList = menuModelList;
    }

    void setListDataChild(HashMap<MenuModel, List<MenuModel>> listDataChild) {
        mListDataChild = listDataChild;
    }

    @Override
    public int getItemViewType(int position) {
        MenuModel menuModel = mMenuModelList.get(position);
        if (menuModel.type == MenuModel.TYPE_ITEM) {
            return TYPE_ITEM;
        } else {
            if (menuModel.isExpand) {
                return TYPE_GROUP_EXPANDED;
            } else {
                return TYPE_GROUP_COLLAPSED;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem;
        if (viewType == TYPE_GROUP_EXPANDED) {
            viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_3, parent, false);
        } else if (viewType == TYPE_GROUP_COLLAPSED) {
            viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_4, parent, false);
        } else {
            viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu_2, parent, false);
        }
        return new ViewHolder(viewItem, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        MenuModel menuModel = mMenuModelList.get(position);
        if(type == TYPE_ITEM) {
            holder.mTextViewMenu.setText(menuModel.name);
            if(menuModel.iconUrl == null || menuModel.iconUrl.isEmpty()){
                holder.mImageViewMenu.setImageDrawable(mContext.getResources().getDrawable(menuModel.icon));
            }else{
                /*GlideApp.with(mContext)
                        .load(menuModel.mIconUrl)
                        .into(viewHolder1.mImageViewMenu);*/
            }
        } else if(type == TYPE_GROUP_COLLAPSED) {
            holder.mTextViewMenu.setText(menuModel.name);
        } else if(type == TYPE_GROUP_EXPANDED) {
            holder.mTextViewMenu.setText(menuModel.name);
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
        menuModel.type = TYPE_GROUP_EXPANDED;
        menuModel.isExpand = true;
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
        menuModel.type = TYPE_GROUP_COLLAPSED;
        menuModel.isExpand = false;
        notifyDataSetChanged();
    }

    public int getSizeChildMenu(String menu){
        for(MenuModel menuModel: mMenuParentList){
            if(menuModel.name.equals(menu)){
                List<MenuModel> menuModels = mListDataChild.get(menuModel);
                return menuModels.size();
            }
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        ImageView mImageViewMenu;
        TextView mTextViewMenu;
        int viewType;

        ViewHolder(View itemView, int viewType) {
            super(itemView);

            this.viewType = viewType;
            mImageViewMenu = itemView.findViewById(R.id.image_menu);
            mTextViewMenu = itemView.findViewById(R.id.tv_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (viewType == TYPE_GROUP_COLLAPSED) {
                expandList(getAdapterPosition());
            } else if (viewType == TYPE_GROUP_EXPANDED) {
                collapseList(getAdapterPosition());
            } else {
                if (mListener != null) {
                    String menu = mMenuModelList.get(getAdapterPosition()).name;
                    mListener.onMenuClick(menu);
                }
            }
        }
    }

}
