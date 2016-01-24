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
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterBasic<T> {
    private ReviewNode mNode;
    private DataConverter<DataImage, GvImage, GvImageList> mCoversConverter;

    public AdapterReviewNode(ReviewNode node,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                             GridDataViewer<T> viewer) {
        this(node, coversConverter);
        setViewer(viewer);
    }

    public AdapterReviewNode(ReviewNode node,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter) {
        mNode = node;
        mCoversConverter = coversConverter;
    }

    //Overridden
    @Override
    public GvDataType<T> getGvDataType() {
        return getGridData().getGvDataType();
    }

    @Override
    public String getSubject() {
        return mNode.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mNode.getRating().getRating();
    }

    @Override
    public GvImageList getCovers() {
        return mCoversConverter.convert(mNode.getCovers());
    }
}
