/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.TagsModel.ItemTagImpl;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 25/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewData {
    public static DataComment nextComment() {
        return nextComment(RandomReviewId.nextReviewId());
    }

    public static DataComment nextComment(ReviewId reviewId) {
        return new DatumComment(reviewId, RandomString.nextSentence(),
                RandomBoolean.nextBoolean());
    }

    public static DataFact nextFact() {
        return nextFact(RandomReviewId.nextReviewId());
    }

    public static DataFact nextFact(ReviewId reviewId) {
        return new DatumFact(reviewId, RandomString.nextWord(),
                RandomString.nextWord());
    }

    public static DataCriterionReview nextCriterionReview() {
        return nextCriterionReview(RandomReviewId.nextReviewId());
    }

    public static DataCriterionReview nextCriterionReview(ReviewId reviewId) {
        return new DatumCriterionReview(reviewId, RandomReview.nextReview());
    }

    public static DataLocation nextLocation() {
        return nextLocation(RandomReviewId.nextReviewId());
    }

    public static DataLocation nextLocation(ReviewId reviewId) {
        return new DatumLocation(reviewId, RandomLatLng.nextLatLng(),
                RandomString.nextWord());
    }

    public static ItemTag nextItemTag() {
        return new ItemTagImpl(RandomString.nextWord(), RandomReviewId.nextIdString());
    }

    public static DataImage nextImage() {
        return nextImage(RandomReviewId.nextReviewId());
    }

    public static DataImage nextImage(ReviewId reviewId) {
        return new DatumImage(reviewId, BitmapMocker.nextBitmap(),
                RandomDataDate.nextDate(), RandomString.nextSentence(), RandomBoolean.nextBoolean());
    }
}
