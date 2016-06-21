/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceWrapper;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;


import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;


/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewReference<T extends GvData> extends AdapterReviewNode<T> {
    private GvImageList mCovers;

    public AdapterReviewReference(ReferenceWrapper reference,
                                  final DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                                  GridDataWrapper<T> viewer) {
        super(reference, coversConverter);
        setWrapper(viewer);
        mCovers = new GvImageList(new GvReviewId(reference.getReviewId()));
        reference.bind(new ReferenceBinders.CoverBinder() {
            @Override
            public void onValue(DataImage value) {
                mCovers.clear();
                mCovers.add(coversConverter.convert(value));
                notifyDataObservers();
            }
        });
    }

    @Override
    public GvImageList getCovers() {
        return mCovers;
    }
}
