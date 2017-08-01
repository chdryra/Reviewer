/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Ui.RecyclerAdapterBasic;
import com.chdryra.android.mygenerallibrary.Ui.ViewHolderAbstract;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 31/07/2017
 * Email: rizwan.choudrey@gmail.com
 *
 * Now that Android has a proper viewholder implementation, this adapter wraps my previous
 * implementation.
 */

public class GvDataAdapter<T extends GvData> extends RecyclerAdapterBasic<T> {

    private final CellDimensionsCalculator.Dimensions mGridCellDims;

    public GvDataAdapter(GvDataList<T> data,
                         CellDimensionsCalculator.Dimensions gridCellDims,
                         @Nullable OnItemClickListener<T> clickListener) {
        super(data.toArrayList(), clickListener);
        mGridCellDims = gridCellDims;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    protected View inflateView(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = getData().get(viewType).getViewHolder();
        viewHolder.inflate(parent.getContext(), parent);
        View v = viewHolder.getView();
        v.setTag(viewHolder);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) v.getLayoutParams();
        lp.width = mGridCellDims.getCellWidth();
        lp.height = mGridCellDims.getCellHeight();
        v.setLayoutParams(lp);

        return v;
    }

    @Override
    protected ViewHolderAbstract<T> newViewHolder(View v, int viewType) {
        return new RecyclerVh<>(v);
    }

    private static class RecyclerVh<T extends GvData> extends ViewHolderAbstract<T> {
        private final ViewHolder mViewHolder;

        private RecyclerVh(View itemView) {
            super(itemView);
            mViewHolder = (ViewHolder) itemView.getTag();
        }

        @Override
        public void updateData(T data) {
            mViewHolder.updateView(data);
        }
    }
}
