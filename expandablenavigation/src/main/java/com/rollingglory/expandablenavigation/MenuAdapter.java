package com.rollingglory.expandablenavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hidayatasep43 on 1/4/2018.
 * hidayatasep43@gmail.com
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    private List<MenuModel> mList;
    private OnMenuListener mListener;

    public interface OnMenuListener{
        void onMenuClick(MenuModel menu);
    }

    MenuAdapter(Context context, List<MenuModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem;
        if (viewType == MenuModel.TYPE_ITEM) {
            viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        } else {
            viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        }
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuModel item = mList.get(position);
        int type = holder.getItemViewType();
        holder.mTextViewMenu.setText(item.name);
        if(type == MenuModel.TYPE_ITEM) {
            if(item.iconUrl == null || item.iconUrl.isEmpty()){
                holder.mImageViewMenu.setImageDrawable(mContext.getResources().getDrawable(item.icon));
            }else{
                /*GlideApp.with(mContext)
                        .load(menuModel.mIconUrl)
                        .into(viewHolder1.mImageViewMenu);*/
            }
        } else {
            if (item.isExpand) {
                holder.mImageViewArrow.setRotation(180);
            } else {
                holder.mImageViewArrow.setRotation(0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setListener(OnMenuListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        ImageView mImageViewMenu;
        TextView mTextViewMenu;
        ImageView mImageViewArrow;

        ViewHolder(View itemView) {
            super(itemView);

            mImageViewMenu = itemView.findViewById(R.id.iv_menu);
            mTextViewMenu = itemView.findViewById(R.id.tv_menu);
            mImageViewArrow = itemView.findViewById(R.id.iv_arrow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onMenuClick(mList.get(getAdapterPosition()));
            }
        }
    }

}
