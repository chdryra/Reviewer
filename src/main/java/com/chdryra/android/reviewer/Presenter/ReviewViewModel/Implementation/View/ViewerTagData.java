/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagRef;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTagData extends GridDataWrapperBasic<GvTagRef> implements ReviewListReference.ItemReferencesCallback<DataTag>{
    private ReviewListReference<DataTag> mReference;
    private GvTag mCache;

    protected ViewerTagData(ReviewListReference<DataTag> references, GvDataList<GvTagRef> initial) {
        mReference = references;
        mCache = initial;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvTagRef datum) {
        return null;
    }

    @Override
    public boolean isExpandable(GvTagRef datum) {
        return false;
    }

    @Override
    public GvDataList<GvTagRef> getGridData() {
        return mCache;
    }

    @Override
    public GvDataType<GvTagRef> getGvDataType() {
        return mCache.getGvDataType();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mReference.toItemReferences(this);
    }

    @Override
    public void onItemReferences(IdableList<ReviewItemReference<DataTag>> reviewItemReferences) {

        notifyDataObservers();
    }
}
