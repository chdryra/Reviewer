/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface MetaReviewData extends ReviewReferenceData {
    ReviewListReference<ReviewReference> getReviews();

    ReviewListReference<DataSubject> getSubjects();

    ReviewListReference<DataAuthorId> getAuthorIds();

    ReviewListReference<DataDate> getDates();

    ReviewItemReference<DataSize> getNumReviews();

    ReviewItemReference<DataSize> getNumSubjects();

    ReviewItemReference<DataSize> getNumAuthors();

    ReviewItemReference<DataSize> getNumDates();
}
