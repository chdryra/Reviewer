/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Utils.RatingFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFormatterTwitter implements ReviewFormatter {
    private static final String APP = ApplicationInstance.APP_NAME;
    private static final String SITE = ApplicationInstance.APP_SITE_SHORT;
    private static final int MAX_TAGS = 3;

    @Override
    public FormattedReview format(ReviewSummary summary) {
        String subjectTag = TextUtils.toCamelCase(summary.getSubject());
        String title = subjectTag + " = " +
                RatingFormatter.upToTwoSignificantDigits(summary.getRating()) + "*";

        String body = "#" + title + ": ";
        ArrayList<String> headlines = summary.getHeadlines();
        if(headlines.size() > 0) body += headlines.get(0);
        int i = 0;
        for(String tag : summary.getTags()) {
            if (tag.equalsIgnoreCase(subjectTag)) continue;
            body += " " + "#" + tag;
            if(++i == MAX_TAGS) break;
        }

        body += " #" + APP + " " + SITE;
        return new FormattedReview(title, body);
    }
}
