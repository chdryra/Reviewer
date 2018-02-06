/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.startouch.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomAuthor {
    public static AuthorId nextAuthorId() {
        return AuthorIdGenerator.newId();
    }

    public static NamedAuthor nextAuthor() {
        return new DefaultNamedAuthor(RandomString.nextWord(), nextAuthorId());
    }

    public static DataAuthor nextDataAuthor() {
        return new DatumAuthor(RandomReviewId.nextReviewId(), RandomString.nextWord(),
                nextAuthorId());
    }
}
