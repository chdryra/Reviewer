/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.reviewer.Algorithms.DataSorting.Bucket;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class VhRatingBucket extends VhBucket<Float, DataRating> {
    private static final int LAYOUT = 0;
    private static final int VIEW = 0;

    public VhRatingBucket() {
        super(LAYOUT, new int[]{VIEW});
    }

    @Override
    public void updateView(GvBucket<Float, DataRating> data) {
        Bucket<Float, DataRating> bucket = data.getBucket();
    }
}
