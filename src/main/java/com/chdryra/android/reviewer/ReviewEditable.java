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
 * Base class for reviews that allow editing of the data they hold. Adds setters and deleters to
 * the {@link Review} interface.
 * <p/>
 * <p>
 * ReviewEditables are - by definition - not published themselves. Use the publish method to
 * return a published uneditable {@link Review} encapsulating its current data.
 * </p>
 */
public abstract class ReviewEditable implements Review {

    //Core data
    public abstract void setSubject(String subject);

    public abstract void setRating(float rating);

    //Optional data
    public abstract void setComments(RDCommentList comments);

    public abstract void setFacts(RDFactList facts);

    public abstract void setImages(RDImageList images);

    public abstract void setURLs(RDUrlList urls);

    public abstract void setLocations(RDLocationList locations);

    //Unpublished
    @Override
    public final Author getAuthor() {
        return Author.NULL_AUTHOR;
    }

    @Override
    public final Date getPublishDate() {
        return null;
    }

    @Override
    public final boolean isPublished() {
        return false;
    }

    //Deleters
    public void deleteComments() {
        setComments(new RDCommentList());
    }

    public void deleteFacts() {
        setFacts(new RDFactList());
    }

    public void deleteImages() {
        setImages(new RDImageList());
    }

    public void deleteUrls() {
        setURLs(new RDUrlList());
    }

    public void deleteLocations() {
        setLocations(new RDLocationList());
    }
}
