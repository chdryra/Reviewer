/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

public interface Review {

    //Core data
    public RDId getId();

    public RDSubject getSubject();

    public RDRating getRating();

    public ReviewTagCollection getTags();

    public ReviewNode getReviewNode();

    public ReviewNode publish(ReviewTreePublisher publisher);

    public Author getAuthor();

    public Date getPublishDate();

    public boolean isPublished();

    //Optional data
    public RDList<RDComment> getComments();

    public boolean hasComments();

    public RDList<RDFact> getFacts();

    public boolean hasFacts();

    public RDList<RDImage> getImages();

    public boolean hasImages();

    public RDList<RDUrl> getURLs();

    public boolean hasURLs();

    public RDList<RDLocation> getLocations();

    public boolean hasLocations();

    //For speed and comparison
    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
}
