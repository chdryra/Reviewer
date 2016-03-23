/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 January, 2015
 */

package com.chdryra.android.reviewer.test.Model.UserData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.UserModel.AuthorId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 22/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UserIdTest extends TestCase {
    @SmallTest
    public void testUserId() {
        AuthorId id1 = AuthorId.generateId();
        assertNotNull(id1);

        AuthorId id2 = AuthorId.generateId();
        assertNotNull(id2);

        assertFalse(id1.equals(id2));
        assertTrue(id2.equals(id2));
    }
}
