/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewRating<T extends DataReviewInfo> implements Comparator<T> {
    private final Comparator<DataRating> mRatingComparator;

    public ReviewRating(Comparator<DataRating> ratingComparator) {
        mRatingComparator = ratingComparator;
    }

    @Override
    public int compare(DataReviewInfo lhs, DataReviewInfo rhs) {
        return mRatingComparator.compare(lhs.getRating(), rhs.getRating());
    }
}