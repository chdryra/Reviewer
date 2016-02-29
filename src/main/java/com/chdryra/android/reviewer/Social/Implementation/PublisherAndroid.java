/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherAndroid extends SocialPublisherBasic {
    private static final String NAME = "Android";
    private static final PublishResults SUCCESS = new PublishResults(NAME, 0);

    private Context mContext;

    public PublisherAndroid(Context context, ReviewSummariser summariser, ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
        mContext = context;
    }

    @Override
    protected PublishResults publish(FormattedReview review) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, review.getTitle());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, review.getBody());
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));

        return SUCCESS;
    }
}
