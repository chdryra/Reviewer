/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeDataComments<GvRef extends
        GvDataRef<GvRef, DataComment, ?>>
        extends ViewerReviewData.CommentList<GvRef> {
    private FactoryReviewViewAdapter mAdapterFactory;

    public ViewerTreeDataComments(com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList reference,
                                  GvConverterReferences<DataComment, GvRef, RefComment> converter,
                                  FactoryReviewViewAdapter adapterFactory) {
        super(reference, converter);
        mAdapterFactory = adapterFactory;
    }

    @Override
    public boolean isExpandable(GvRef datum) {
        return getGridData().contains(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mAdapterFactory.newReviewsListAdapter(getGridData());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvRef datum) {
        return isExpandable(datum) ? mAdapterFactory.newReviewsListAdapter(datum) : null;
    }
}
