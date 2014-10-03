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
 * The fundamental interface for all review classes.
 *
 * All reviews are primarily data holders that are expected to have 3 items of core data:
 * <ul>
 *     <li>A unique identifier</li>
 *     <li>A subject </li>
 *     <li>A rating</li>
 * </ul>
 *
 * In addition, reviews may have some optional data:
 * <ul>
 *     <li>Comments</li>
 *     <li>Images</li>
 *     <li>Locations</li>
 *     <li>Facts</li>
 *     <li>URLs</li>
 * </ul>
 *
 * Reviews can be nodes in a review tree with children sub-reviews and/or be a sub-review of a
 * parent review. They can be represented as a ReviewNode with zero or more children and/or a
 * parent if necessary.
 *
 * Reviews may or may not be published (have non-null @see Author and Publish Date)
 * @see ReviewNode
 * @see ReviewEditable
 * @see ReviewUser
 */

public interface Review extends RDId.RDIdAble {
    //Core data
    @Override
    public RDId getId();

    public RDSubject getSubject();

    public RDRating getRating();

    //Core methods
    /**
     *
     * @return ReviewNode: tree representation of the review
     */
    public ReviewNode getReviewNode();

    /**
     *
     * @param publisher: ReviewTreePublisher stamps an unpublished review (and descendants if
     *                 necessary) with an Author and Date.
     * @return ReviewNode: a new uneditable review tree representation stamped with Author and Date.
     */
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
