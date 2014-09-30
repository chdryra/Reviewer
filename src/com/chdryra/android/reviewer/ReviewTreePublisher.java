/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

class ReviewTreePublisher {
    private final Author mAuthor;
    private       Date   mPublishDate;

    public ReviewTreePublisher(Author author) {
        mAuthor = author;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public Date getPublishDate() {
        return mPublishDate;
    }

    public ReviewNode publish(Review review) {
        mPublishDate = new Date();
        VisitorTreePublisher publisher = new VisitorTreePublisher(this);
        review.getReviewNode().acceptVisitor(publisher);

        return publisher.getPublishedTree();
    }
}
