/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;
import android.content.Intent;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformSharer {
    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;

    public SocialPlatformSharer(ReviewSummariser summariser, ReviewFormatter formatter) {
        mSummariser = summariser;
        mFormatter = formatter;
    }

    public void share(Review review, TagsManager tagsManager, Activity activity) {
        ReviewSummary summary = mSummariser.summarise(review, tagsManager);
        FormattedReview formatted = mFormatter.format(summary);

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, formatted.getTitle());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, formatted.getBody());
        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
