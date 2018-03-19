/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Utils.RatingFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFormatterFacebook implements ReviewFormatter {
    private static final String APP = ApplicationInstance.APP_NAME;
    private static final String SITE = ApplicationInstance.APP_SITE_SHORT;

    @Override
    public FormattedReview format(ReviewSummary summary) {
        String title = summary.getSubject() + " " +
                RatingFormatter.outOfFive(summary.getRating()) + " stars";

        String body = "";
        ArrayList<String> headlines = summary.getHeadlines();
        if (headlines.size() > 0) body += headlines.get(0);
        body += " #" + APP;
        for (String tag : summary.getTags()) {
            body += " " + "#" + tag;
        }

        String location = getLocationString(summary.getLocations());
        body += location.length() > 0 ? " @" + location : "";

        body += " #" + APP + " " + SITE;

        return new FormattedReview(title, body);
    }

    private String getLocationString(ArrayList<String> locationNames) {
        String location = "";
        int locs = locationNames.size();
        if (locs > 0) {
            location = locationNames.get(0);
            if (locs > 1) {
                String loc = locs == 2 ? " location" : " locations";
                location += " +" + String.valueOf(locationNames.size() - 1) + loc;
            }
        }

        return location;
    }
}
