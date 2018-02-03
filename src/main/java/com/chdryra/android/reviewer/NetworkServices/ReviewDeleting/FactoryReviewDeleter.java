/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewDeleting;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public interface FactoryReviewDeleter {
    ReviewDeleter newDeleter(ReviewId id);
}
