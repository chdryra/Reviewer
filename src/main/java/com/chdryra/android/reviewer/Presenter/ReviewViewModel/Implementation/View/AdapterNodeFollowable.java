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

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;


/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterNodeFollowable extends AdapterReviewNode<GvNode> {
    private final AuthorId mFollowAuthorId;

    public AdapterNodeFollowable(ReviewNode node,
                                 DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                                 GridDataWrapper<GvNode> viewer,
                                 @Nullable AuthorId followAuthorId) {
        super(node, coversConverter, viewer);
        mFollowAuthorId = followAuthorId;
    }

    @Nullable
    public AuthorId getFollowAuthorId() {
        return mFollowAuthorId;
    }
}
