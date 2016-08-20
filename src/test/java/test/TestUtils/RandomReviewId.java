/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewId {
    public static ReviewId nextReviewId() {
        return nextMdReviewId();
    }

    public static MdReviewId nextMdReviewId() {
        ReviewStamp stamp = ReviewStamp.newStamp(RandomAuthor.nextAuthor(), RandomDataDate.nextDate());
        return new MdReviewId(stamp);
    }

    public static String nextIdString() {
        return nextMdReviewId().toString();
    }

    public static GvReviewId nextGvReviewId() {
        return new GvReviewId(nextIdString());
    }
}
