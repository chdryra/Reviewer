/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.Model.UserModel.AuthorId;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomAuthor {
    //Static methods
    public static DatumAuthor nextAuthor() {
        return new DatumAuthor(RandomString.nextWord(), AuthorId.generateId());
    }
}
