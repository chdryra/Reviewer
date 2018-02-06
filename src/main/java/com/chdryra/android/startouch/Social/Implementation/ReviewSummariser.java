/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSummariser {

    public ReviewSummary summarise(Review review) {
        return new ReviewSummary(review.getSubject().getSubject(),
                review.getRating().getRating(), getHeadlines(review),
                getTags(review),
                getLocationNames(review));
    }

    private ArrayList<String> getTags(Review review) {
        ArrayList<String> tags = new ArrayList<>();
        for(DataTag tag : review.getTags()) {
            tags.add(tag.getTag());
        }
        return tags;
    }

    private ArrayList<String> getHeadlines(Review review) {
        ArrayList<String> headlines = new ArrayList<>();
        for(DataComment comment : review.getComments()) {
            if(comment.isHeadline()) {
                headlines.add(DataFormatter.getFirstSentence(comment));
            }
        }

        return headlines;
    }

    private ArrayList<String> getLocationNames(Review review) {
        ArrayList<String> mNames = new ArrayList<>();
        for(DataLocation location : review.getLocations()) {
            mNames.add(location.getName());
        }

        return mNames;
    }
}
