/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 25/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface BatchReviewSharerListener {
    void onStatusUpdate(double percentage, PublishResults results);

    void onPublished(Collection<PublishResults> publishedOk, Collection<PublishResults> publishedNotOk);
}
