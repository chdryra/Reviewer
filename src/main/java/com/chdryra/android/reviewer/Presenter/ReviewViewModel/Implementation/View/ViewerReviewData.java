/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRefList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewData<ValueType extends HasReviewId, GvRef extends GvDataRef<GvRef, ValueType, ?>>
        extends GridDataWrapperBasic<GvRef> implements ReviewListReference.ItemReferencesCallback<ValueType> {

    private ReviewListReference<ValueType> mReference;
    private GvConverterReferences<ValueType, GvRef> mConverter;
    private GvDataRefList<GvRef> mCache;

    public ViewerReviewData(ReviewListReference<ValueType> reference,
                            GvConverterReferences<ValueType, GvRef> converter) {
        mReference = reference;
        mConverter = converter;
        mCache = newDataList();
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvRef datum) {
        return null;
    }

    @Override
    public boolean isExpandable(GvRef datum) {
        return false;
    }

    @Override
    public GvDataList<GvRef> getGridData() {
        return mCache;
    }

    @Override
    public GvDataType<GvRef> getGvDataType() {
        return mCache.getGvDataType();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mReference.toItemReferences(this);
    }

    @Override
    public void onItemReferences(IdableList<ReviewItemReference<ValueType>> references) {
        makeGridData(references);
        notifyDataObservers();
    }

    @NonNull
    private GvDataRefList<GvRef> newDataList() {
        return new GvDataRefList<>(mConverter.getOutputType(),
                new GvReviewId(mReference.getReviewId()));
    }

    private void makeGridData(IdableList<ReviewItemReference<ValueType>> references) {
        mCache = newDataList();
        for (ReviewItemReference<ValueType> reference : references) {
            mCache.add(mConverter.convert(reference));
        }
    }
}
