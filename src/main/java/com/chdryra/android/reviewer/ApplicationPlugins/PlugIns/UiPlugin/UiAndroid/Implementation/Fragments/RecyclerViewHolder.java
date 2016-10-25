/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.support.v7.widget.RecyclerView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder mViewHolder;

    public RecyclerViewHolder(ViewHolder viewHolder) {
        super(viewHolder.getView());
        mViewHolder = viewHolder;
    }

    public void updateView(ViewHolderData data) {
        mViewHolder.updateView(data);
    }
}
