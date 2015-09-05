package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataSorting.AuthorSorters;

import junit.framework.TestCase;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorSortersTest extends TestCase {
    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvAuthorList.GvAuthor> comparator = AuthorSorters.getSorters().getDefault();
        GvAuthorList.GvAuthor A = new GvAuthorList.GvAuthor("A", UserId.generateId().toString());
        GvAuthorList.GvAuthor a = new GvAuthorList.GvAuthor("a", UserId.generateId().toString());
        GvAuthorList.GvAuthor B = new GvAuthorList.GvAuthor("B", UserId.generateId().toString());
        GvAuthorList.GvAuthor C = new GvAuthorList.GvAuthor("C", UserId.generateId().toString());

        assertEquals(0, comparator.compare(A, A));
        assertEquals(0, comparator.compare(A, a));
        assertEquals(0, comparator.compare(B, B));
        assertEquals(0, comparator.compare(C, C));
        assertTrue(comparator.compare(A, B) < 0);
        assertEquals(comparator.compare(A, B), -comparator.compare(B, A));
        assertTrue(comparator.compare(B, C) < 0);
        assertTrue(comparator.compare(A, C) < 0);
    }
}
