package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataSorting.GvDataSorters;

import junit.framework.TestCase;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentSortersTest extends TestCase {
    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvCommentList.GvComment> comparator = GvDataSorters.getDefaultComparator
                (GvCommentList.GvComment.TYPE);

    }
}
