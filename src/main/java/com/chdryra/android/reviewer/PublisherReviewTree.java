/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Holds publishing data and manages the publication of review trees given a root {@link
 * ReviewNode}.
 *
 * @see com.chdryra.android.reviewer.VisitorTreePublisher
 */
class PublisherReviewTree {
    private final Author mAuthor;
    private       Date   mPublishDate;

    PublisherReviewTree(Author author) {
        mAuthor = author;
    }

    Author getAuthor() {
        return mAuthor;
    }

    Date getPublishDate() {
        if (mPublishDate == null) {
            mPublishDate = new Date();
        }

        return mPublishDate;
    }

    ReviewNode publish(ReviewNode reviewTreeRoot) {
        VisitorTreePublisher publisher = new VisitorTreePublisher(this);
        reviewTreeRoot.acceptVisitor(publisher);

        return publisher.getPublishedTree();
    }
}
