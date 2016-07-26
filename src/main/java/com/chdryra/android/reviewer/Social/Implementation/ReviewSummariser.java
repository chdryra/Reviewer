/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .CommentFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSummariser {

    public ReviewSummary summarise(Review review, TagsManager tagsManager) {
        return new ReviewSummary(review.getSubject().getSubject(),
                review.getRating().getRating(), getHeadlines(review),
                tagsManager.getTags(review.getReviewId().toString()).toStringArray(),
                getLocationNames(review));
    }

    private ArrayList<String> getHeadlines(Review review) {
        ArrayList<String> headlines = new ArrayList<>();
        for(DataComment comment : review.getComments()) {
            if(comment.isHeadline()) {
                headlines.add(CommentFormatter.getHeadline(comment.getComment()));
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
