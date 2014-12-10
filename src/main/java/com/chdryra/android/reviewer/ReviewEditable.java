/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.ArrayList;
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
    public abstract <T extends DataComment> void setComments(Iterable<T> comments);

    public abstract <T extends DataFact> void setFacts(Iterable<T> facts);

    public abstract <T extends DataImage> void setImages(Iterable<T> images);

    public abstract <T extends DataUrl> void setUrls(Iterable<T> urls);

    public abstract <T extends DataLocation> void setLocations(Iterable<T> locations);

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
        setComments(new ArrayList<DataComment>());
    }

    public void deleteFacts() {
        setFacts(new ArrayList<DataFact>());
    }

    public void deleteImages() {
        setImages(new ArrayList<DataImage>());
    }

    public void deleteUrls() {
        setUrls(new ArrayList<DataUrl>());
    }

    public void deleteLocations() {
        setLocations(new ArrayList<DataLocation>());
    }
}
