/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;


/**
 * This clearly is not very general...
 */

public class AdapterReviewsList extends AdapterReviewNode<GvNode> {
    public AdapterReviewsList(ReviewNode node,
                              DataReference<ProfileImage> profileImage,
                              DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                              GridDataWrapper<GvNode> viewer) {
        super(node, profileImage, coversConverter, viewer);
    }
}
