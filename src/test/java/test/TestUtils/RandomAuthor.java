/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomAuthor {
    public static NamedAuthor nextAuthor() {
        return new DatumAuthor(RandomString.nextWord(), AuthorIdGenerator.newId());
    }

    public static DataAuthor nextAuthorReview() {
        return new DatumAuthor(RandomReviewId.nextReviewId(), RandomString.nextWord(),
                AuthorIdGenerator.newId());
    }
}
