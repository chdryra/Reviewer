/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonicalCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateToReviews<T extends GvData> extends ViewerDataToReviews<GvCanonical> {
    private final GvCanonicalCollection<T> mData;
    private final FactoryReviewViewAdapter mAdapterFactory;

    public ViewerAggregateToReviews(GvCanonicalCollection<T> data,
                                    FactoryReviewViewAdapter adapterFactory) {
        super(data, adapterFactory);
        mData = data;
        mAdapterFactory = adapterFactory;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mAdapterFactory.newFlattenedReviewsListAdapter(mData);
    }
}
