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
 * <p/>
 * All reviews are primarily data holders that are expected to have 3 items of core data:
 * <ul>
 * <li>A unique identifier</li>
 * <li>A subject </li>
 * <li>A rating</li>
 * </ul>
 * <p/>
 * In addition, reviews may have some optional data:
 * <ul>
 * <li>Comments</li>
 * <li>Images</li>
 * <li>Locations</li>
 * <li>Facts</li>
 * <li>URLs</li>
 * </ul>
 * <p/>
 * Reviews can be nodes in a review tree with children sub-reviews and/or be a sub-review of a
 * parent review. They can be represented as a ReviewNode with zero or more children and/or a
 * parent if necessary.
 * <p/>
 * Reviews may or may not be published (have non-null Author and Publish Date). Published reviews
 * should not be editable reviews or expandable nodes themselves. They may, however, be passed to a
 * ReviewNodeExpandable as the root review for a different review structure that may be expanded.
 *
 * @see ReviewNode
 * @see ReviewNodeExpandable
 * @see ReviewEditable
 */

public interface Review extends ReviewId.RDIdAble {

    //Core data
    @Override
    ReviewId getId();

    RDSubject getSubject();

    RDRating getRating();

    //Core methods

    /**
     * Returns a tree representation of the review. Has the same {@link ReviewId} as the review it
     * represents.
     * //TODO work out a way of ensuring this without further complicating inheritance.
     */
    ReviewNode getReviewNode();

    /**
     * Stamps an unpublished review (and descendants if necessary) with an {@link Author} and Date.
     * Returns an uneditable published review.
     *
     * @param publisher: holds publishing data and publishes unpublished descendants if necessary.
     * @return Review: a new uneditable review stamped with {@link Author} and Date.
     */
    Review publish(PublisherReviewTree publisher);

    Author getAuthor();

    Date getPublishDate();

    public boolean isPublished();

    //Optional data
    MdCommentList getComments();

    boolean hasComments();

    MdFactList getFacts();

    boolean hasFacts();

    MdImageList getImages();

    boolean hasImages();

    MdUrlList getUrls();

    boolean hasUrls();

    MdLocationList getLocations();

    boolean hasLocations();

    //For speed and comparison
    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
}
