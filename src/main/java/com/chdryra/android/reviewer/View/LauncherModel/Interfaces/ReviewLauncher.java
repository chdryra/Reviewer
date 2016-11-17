/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewLauncher extends UiLauncherWrapper {
    void launchAsList(ReviewId reviewId);

    void launchAsList(ReviewNode node);

    void launchSummary(ReviewId reviewId);

    void launchReviewsList(AuthorId authorId);

    void launchFormatted(ReviewId reviewId);

    void launchFormatted(ReviewNode node, boolean isPublished);

    void launchMap(ReviewNode node, boolean isPublished);
}
