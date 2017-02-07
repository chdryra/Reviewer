/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .CacheVhNode;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface VhNode extends ViewHolder {
    boolean isBoundTo(ReviewNode node);

    void unbindFromNode();

    void refresh(ReviewId reviewId);

    CacheVhNode getCache();
}
