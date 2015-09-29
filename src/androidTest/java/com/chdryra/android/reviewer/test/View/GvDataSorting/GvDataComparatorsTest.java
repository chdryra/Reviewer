package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.GvDataSorting.GvDataComparators;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataComparatorsTest extends TestCase {
    @SmallTest
    public void testGetDefaultComparator() {
        assertNotNull(GvDataComparators.getDefaultComparator(GvReviewOverviewList
                .GvReviewOverview.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvAuthorList.GvAuthor.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvSubjectList.GvSubject.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvDateList.GvDate.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvTagList.GvTag.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvCriterionList.GvCriterion.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvCommentList.GvComment.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvImageList.GvImage.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvLocationList.GvLocation.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvFactList.GvFact.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvUrlList.GvUrl.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvSocialPlatformList
                .GvSocialPlatform.TYPE));
    }

}
