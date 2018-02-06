/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhCanonical implements ViewHolder {
    private final ViewHolder mViewHolder;

    public VhCanonical(ViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mViewHolder.inflate(context, parent);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvCanonical canonical = (GvCanonical) data;
        mViewHolder.updateView(canonical.getCanonical());
    }

    @Override
    public View getView() {
        return mViewHolder.getView();
    }
}
