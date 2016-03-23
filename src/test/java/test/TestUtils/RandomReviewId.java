/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.testutils.RandomString;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewId {
    private static final Random RAND = new Random();

    public static ReviewId nextReviewId() {
        return nextMdReviewId();
    }

    public static MdReviewId nextMdReviewId() {
        return new MdReviewId(RandomString.nextWord(), RAND.nextLong(), RAND.nextInt());
    }

    public static String nextIdString() {
        return nextMdReviewId().toString();
    }

    public static GvReviewId nextGvReviewId() {
        return new GvReviewId(nextIdString());
    }
}
