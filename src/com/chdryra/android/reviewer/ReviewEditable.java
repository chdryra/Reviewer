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
 * the Review interface.
 * ReviewEditables are by definition not published themselves.
 * Use the publish method to return a published ReviewNode of its current data.
 *
 */
public abstract class ReviewEditable implements Review {

    //Core data
    public abstract void setSubject(String subject);

    public abstract void setRating(float rating);

    //Optional data
    void deleteComments() {
        setComments(new RDList<RDComment>());
    }

    public abstract void setComments(RDList<RDComment> comment);

    void deleteFacts() {
        setFacts(new RDList<RDFact>());
    }

    public abstract void setFacts(RDList<RDFact> facts);

    void deleteImages() {
        setImages(new RDList<RDImage>());
    }

    public abstract void setImages(RDList<RDImage> images);

    void deleteUrls() {
        setURLs(new RDList<RDUrl>());
    }

    public abstract void setURLs(RDList<RDUrl> url);

    void deleteLocations() {
        setLocations(new RDList<RDLocation>());
    }

    public abstract void setLocations(RDList<RDLocation> locations);

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
}
