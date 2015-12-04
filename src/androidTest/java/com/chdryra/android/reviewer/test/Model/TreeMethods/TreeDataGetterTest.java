package com.chdryra.android.reviewer.test.Model.TreeMethods;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.TreeDataGetter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.TreeDataGetter;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 20/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataGetterTest extends TestCase {

    @SmallTest
    public void testGetData() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        TreeDataGetter getter = new TreeDataGetter(node.getRoot());
        int num = getter.getComments().size();
    }
}
