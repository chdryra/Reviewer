/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BatchReviewSharer implements SocialPublisherListener {
    private BatchReviewSharerListener mListener;
    private Collection<SocialPlatform<?>> mPlatforms;
    private ArrayList<PublishResults> mResults;

    public interface BatchReviewSharerListener {
        void onStatusUpdate(double percentage, PublishResults results);

        void onPublished(Collection<PublishResults> publishedOk, Collection<PublishResults>
                publishedNotOk);
    }

    public BatchReviewSharer(Collection<SocialPlatform<?>> platforms,
                             BatchReviewSharerListener listener) {
        mListener = listener;
        mPlatforms = platforms;
    }

    public void shareReview(Review review, TagsManager tagsManager) {
        if (mPlatforms.size() == 0) return;
        mResults = new ArrayList<>();
        for (SocialPlatform<?> platform : mPlatforms) {
            platform.publish(review, tagsManager, this);
        }
    }

    @Override
    public void onPublished(PublishResults results) {
        mResults.add(results);
        mListener.onStatusUpdate(percentageCompleted(), results);
        if (mResults.size() == mPlatforms.size()) onPublishingComplete(mResults);
    }

    private double percentageCompleted() {
        return (double) mResults.size() / (double) mPlatforms.size();
    }

    private void onPublishingComplete(Collection<PublishResults> results) {
        ArrayList<PublishResults> platformsOk = new ArrayList<>();
        ArrayList<PublishResults> platformsNotOk = new ArrayList<>();
        for (PublishResults result : results) {
            if (result.wasSuccessful()) {
                platformsOk.add(result);
            } else {
                platformsNotOk.add(result);
            }
        }

        mListener.onPublished(platformsOk, platformsNotOk);
    }
}
