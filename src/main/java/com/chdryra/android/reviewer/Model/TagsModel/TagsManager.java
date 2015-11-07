/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer.Model.TagsModel;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

import java.util.ArrayList;

//import org.apache.commons.lang3.text.WordUtils;

/**
 * The singleton that manages the tagging and untagging of Reviews.
 */
public class TagsManager {
    private final ReviewTagCollection mTags;

    //Constructors
    public TagsManager() {
        mTags = new ReviewTagCollection();
    }

    //public methods
    public ReviewTagCollection getTags() {
        return new ReviewTagCollection(mTags);
    }

    public ReviewTagCollection getTags(ReviewId id) {
        ReviewTagCollection tags = new ReviewTagCollection();
        for (ReviewTag tag : mTags) {
            if (tag.tagsReview(id)) tags.add(tag);
        }

        return tags;
    }

    public void tagReview(ReviewId id, ArrayList<String> tags) {
        for (String tag : tags) {
            tagReview(id, tag);
        }
    }

    public void tagReview(ReviewId id, String tag) {
        ReviewTag reviewTag = mTags.get(tag);
        if (reviewTag == null) {
            mTags.add(new ReviewTag(tag, id));
        } else {
            reviewTag.addReview(id);
        }
    }

    public boolean untagReview(ReviewId id, ReviewTag tag) {
        if (tag.tagsReview(id)) {
            tag.removeReview(id);
            if (tag.getReviews().size() == 0) {
                mTags.remove(tag);
                return true;
            }
        }

        return false;
    }
}
