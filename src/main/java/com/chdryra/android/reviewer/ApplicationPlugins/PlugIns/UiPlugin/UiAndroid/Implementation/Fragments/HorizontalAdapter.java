/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalAdapter<T extends GvData, V extends ViewHolder>
        extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final ViewHolderFactory<V> mFactory;
    private GvDataList<T> mData;

    public HorizontalAdapter(GvDataList<T> data, ViewHolderFactory<V> factory) {
        mData = data;
        mFactory = factory;
    }

    public void setData(GvDataList<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        V viewHolder = mFactory.newViewHolder();
        viewHolder.inflate(parent.getContext(), parent);
        return new RecyclerViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.updateView(mData.getItem(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
