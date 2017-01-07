/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference extends ReviewReferenceData, ReviewItemReference<Review> {
    interface ReviewReferenceObserver {
        void onSubjectChanged(DataSubject newSubject);

        void onRatingChanged(DataRating newRating);
    }

    void registerObserver(ReviewReferenceObserver observer);

    void unregisterObserver(ReviewReferenceObserver observer);
}
