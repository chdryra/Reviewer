/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

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
 * {@link ReviewTreeNode} as the root review for a different
 * review structure that may be expanded.
 *
 * @see ReviewNode
 */

public interface Review extends ReviewId.RDIdAble {

    //Core data
    @Override
    ReviewId getId();

    MdSubject getSubject();

    MdRating getRating();

    //Core methods
    Author getAuthor();

    Date getPublishDate();

    //Optional data
    MdCommentList getComments();

    boolean hasComments();

    MdFactList getFacts();

    boolean hasFacts();

    MdImageList getImages();

    boolean hasImages();

    MdLocationList getLocations();

    boolean hasLocations();

    //For speed and comparison
    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
}
