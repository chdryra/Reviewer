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
abstract class ReviewEditable implements Review {

    //Core data
    abstract void setSubject(String subject);

    abstract void setRating(float rating);

    //Optional data
    abstract void setComments(RDList<RDComment> comment);

    abstract void setFacts(RDList<RDFact> facts);

    abstract void setImages(RDList<RDImage> images);

    abstract void setURLs(RDList<RDUrl> url);

    abstract void setLocations(RDList<RDLocation> locations);

    //Unpublished
    @Override
    public final Author getAuthor() {
        return null;
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
    void deleteComments() {
        setComments(new RDList<RDComment>());
    }

    void deleteFacts() {
        setFacts(new RDList<RDFact>());
    }

    void deleteImages() {
        setImages(new RDList<RDImage>());
    }

    void deleteUrls() {
        setURLs(new RDList<RDUrl>());
    }

    void deleteLocations() {
        setLocations(new RDList<RDLocation>());
    }
}
