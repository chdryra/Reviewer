/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;

/**
 * Created by: Rizwan Choudrey
 * On: 07/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public interface CacheVhNode {
    void registerObserver(VhNode node);

    void unregisterObserver(VhNode node);

    void deleteCache(ReviewId reviewId);
}
