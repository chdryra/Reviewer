/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 June, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Created by: Rizwan Choudrey
 * On: 09/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EqualityTesterTest extends TestCase {

    @SmallTest
    public void testEqualsHash() {
        EqualsVerifier.forClass(Comment.class).verify();
    }

    private static class Comment {
        private String mComment;

        private Comment(String comment) {
            mComment = comment;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Comment)) return false;

            Comment comment = (Comment) o;

            return !(mComment != null ? !mComment.equals(comment.mComment) : comment.mComment !=
                    null);

        }

        @Override
        public int hashCode() {
            return mComment != null ? mComment.hashCode() : 0;
        }
    }
}
