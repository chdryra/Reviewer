/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFormatterDefault implements ReviewFormatter {
    private static final String RATES = "rates";
    private static final String APP = "on " + ApplicationInstance.APP_NAME;

    @Override
    public FormattedReview format(ReviewSummary summary) {
        String title = summary.getAuthor() + " " + RATES + " " + summary.getSubject() + " " +
                RatingFormatter.outOfFive(summary.getRating()) + " " + APP;

        String body = summary.getHeadlines().get(0);
        for(String tag : summary.getTags()) {
            body += " " + "#" + tag;
        }

        return new FormattedReview(title, body);
    }
}
