package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataComparators;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataComparatorsTest extends TestCase {
    @SmallTest
    public void testGetDefaultComparator() {
        assertNotNull(GvDataComparators.getDefaultComparator(GvReviewOverview.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvAuthor.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvSubject.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvDate.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvTag.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvCriterion.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvComment.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvImage.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvLocation.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvFact.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvUrl.TYPE));
        assertNotNull(GvDataComparators.getDefaultComparator(GvSocialPlatform.TYPE));
    }

}
