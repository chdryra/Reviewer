/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 14/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendInfoConverter {
    private DataSubject convertSubject(String reviewId, String subject) {
        return new DatumSubject(convert(reviewId), subject);
    }

    private DataRating convertRating(String reviewId, Rating rating) {
        return new DatumRating(convert(reviewId), (float) rating.getRating(), (int) rating.getRatingWeight());
    }

    private DataAuthorId convertAuthorId(String reviewId, String authorId) {
        return new DatumAuthorId(convert(reviewId), authorId);
    }

    private DataDate convertDate(String reviewId, long time) {
        return new DatumDate(convert(reviewId), time);
    }

    private ReviewId convert(String reviewId) {
        return new DatumReviewId(reviewId);
    }

    public DataReviewInfo convert(ReviewListEntry entry) {
        String id = entry.getReviewId();
        return new ReviewInfo(convert(id), convertSubject(id, entry.getSubject()),
                convertRating(id, entry.getRating()), convertAuthorId(id, entry.getAuthorId()),
                convertDate(id, entry.getPublishDate()));
    }

    public ReviewListEntry convert(DataReviewInfo info) {
        return new ReviewListEntry(info);
    }
}
