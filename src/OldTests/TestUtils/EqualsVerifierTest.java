package com.chdryra.android.reviewer.test.TestUtils;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EqualsVerifierTest extends TestCase {
    @SmallTest
    public void testEqualsHash() {
        EqualsVerifier.forClass(Comment.class).verify();
    }

    private static class Comment {
        private String mComment;

        private Comment(String comment) {
            mComment = comment;
        }

        //Overridden
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
