/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

public abstract class ReviewEditable implements Review {
	
	//Core data
	public abstract void setSubject(String subject);	
	public abstract void setRating(float rating);

	//Optional data
	public abstract void setComments(RDList<RDComment> comment);
    public abstract void setFacts(RDList<RDFact> facts);
    public abstract void setImages(RDList<RDImage> images);
    public abstract void setURLs(RDList<RDUrl> url);
    public abstract void setLocations(RDList<RDLocation> locations);

    void deleteComments() {
        setComments(new RDList<RDComment>());
    }
	void deleteFacts() {
        setFacts(new RDList<RDFact>());
    }
	void deleteImages() {
        setImages(new RDList<RDImage>());
    }
	void deleteURLs() {
        setURLs(new RDList<RDUrl>());
    }
	void deleteLocations() {
        setLocations(new RDList<RDLocation>());
    }
	
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
	
	//For speed and comparison
	@Override
	public abstract boolean equals(Object o);
	@Override
	public abstract int hashCode();
}
