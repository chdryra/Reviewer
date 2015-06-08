/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 29 October, 2014
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.UserId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 29/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorTest extends TestCase {
    private static final String AUTHOR_NAME = "Rizwan Choudrey";
    private static final UserId ID = UserId.generateId();

    @SmallTest
    public void testAuthor() {
        Author author = new Author(AUTHOR_NAME, ID);
        assertEquals(AUTHOR_NAME, author.getName());
        assertEquals(ID, author.getUserId());
    }
}
