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

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewLauncher {
    void setSessionAuthor(AuthorId authorId);

    void launchAsList(ReviewId reviewId);

    void launchSummary(ReviewId reviewId);

    void launchFormatted(ReviewId reviewId);

    void launchReviewsList(AuthorId authorId);
}
