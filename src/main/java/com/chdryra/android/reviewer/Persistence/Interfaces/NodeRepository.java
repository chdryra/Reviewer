/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface NodeRepository extends ReviewsSource {
    ReviewNode asReviewNode(ReviewId id);

    ReviewNode asMetaReview(ReviewId id);

    ReviewNode getMetaReview(AuthorId id);

    ReviewNode asMetaReview(VerboseDataReview datum, String subject);

    ReviewNode getMetaReview(IdableCollection<?> data, String subject);

    ReviewNode getMetaReview(ReviewsRepository repo, AuthorId owner, String subject);
}
