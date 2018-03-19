/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewId {
    public static ReviewId nextReviewId() {
        return ReviewStamp.newStamp(RandomAuthor.nextAuthorId(), RandomDataDate.nextDateTime())
                .getReviewId();
    }

    public static String nextIdString() {
        return nextReviewId().toString();
    }
}
