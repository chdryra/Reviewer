/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsFeed extends ReviewsRepository{
    DataAuthor getAuthor();
}
