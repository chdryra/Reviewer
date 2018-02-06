/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Ui.RecyclerAdapterBasic;
import com.chdryra.android.mygenerallibrary.Ui.ViewHolderAbstract;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 31/07/2017
 * Email: rizwan.choudrey@gmail.com
 * <p>
 * Now that Android has a proper viewholder implementation, this adapter wraps my previous
 * implementation.
 */

class GvDataAdapter<T extends GvData> extends RecyclerAdapterBasic<T> {
    private final int mCellWidth;
    private final int mCellHeight;
    private final ViewHolderFactory<?> mVhFactory;

    //Cell height of <=0 will assume wrapped
    GvDataAdapter(GvDataList<T> data,
                  int cellWidth, int cellHeight,
                  @Nullable OnItemClickListener<T> clickListener) {
        this(data, cellWidth, cellHeight, clickListener, null);
    }

    GvDataAdapter(GvDataList<T> data,
                  int cellWidth, int cellHeight,
                  @Nullable OnItemClickListener<T> clickListener,
                  @Nullable ViewHolderFactory<?> vhFactory) {
        super(data.toArrayList(), clickListener);
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
        mVhFactory = vhFactory;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    protected View inflateView(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = createViewHolder(viewType);
        viewHolder.inflate(parent.getContext(), parent);
        View v = viewHolder.getView();
        v.setTag(viewHolder);

        if (mCellHeight > 0) v.getLayoutParams().height = mCellHeight;
        if (mCellWidth > 0) v.getLayoutParams().width = mCellWidth;


        return v;
    }

    @Override
    protected ViewHolderAbstract<T> newRecyclerViewHolder(View v, int viewType) {
        return new ViewHolderOptionable<>((ViewHolder) v.getTag());
    }

    private ViewHolder createViewHolder(int viewType) {
        return mVhFactory != null ? mVhFactory.newViewHolder() : getData().get(viewType)
                .getViewHolder();
    }

    static class ViewHolderOptionable<T extends GvData> extends ViewHolderAbstract<T>
            implements OptionSelectListener {
        private final ViewHolder mViewHolder;

        private ViewHolderOptionable(ViewHolder vh) {
            super(vh.getView());
            mViewHolder = vh;
        }

        @Override
        public boolean onOptionSelected(int requestCode, String option) {
            OptionSelectListener delegate = getDelegate();
            return delegate != null && delegate.onOptionSelected(requestCode, option);
        }

        @Override
        public boolean onOptionsCancelled(int requestCode) {
            OptionSelectListener delegate = getDelegate();
            return delegate != null && delegate.onOptionsCancelled(requestCode);
        }

        @Override
        public void updateData(T data) {
            mViewHolder.updateView(data);
        }

        @Nullable
        private OptionSelectListener getDelegate() {
            try {
                return (OptionSelectListener) mViewHolder;
            } catch (ClassCastException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
