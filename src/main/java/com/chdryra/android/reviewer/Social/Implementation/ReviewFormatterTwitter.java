/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFormatterTwitter implements ReviewFormatter {
    private static final String APP = ApplicationInstance.APP_NAME;

    @Override
    public FormattedReview format(ReviewSummary summary) {
        String subjectTag = TextUtils.toCamelCase(summary.getSubject());
        String title = "#" + subjectTag + " " +
                RatingFormatter.twoSignificantDigits(summary.getRating()) + "*" + ": ";

        String body = title + " " + summary.getHeadlines().get(0);
        body += " #" + APP;
        for(String tag : summary.getTags()) {
            if (tag.equalsIgnoreCase(subjectTag)) continue;
            body += " " + "#" + tag;
        }

        return new FormattedReview(title, body);
    }
}
