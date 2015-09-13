package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonicalCollectionTest extends TestCase {
    private static final int NUM = 5;
    private GvCanonicalCollection<GvCommentList.GvComment> mList;

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(mList);
    }

    @SmallTest
    public void testComparator() {
        checkComparator(GvCommentList.GvComment.TYPE);
        checkComparator(GvSubjectList.GvSubject.TYPE);
        checkComparator(GvAuthorList.GvAuthor.TYPE);
        checkComparator(GvTagList.GvTag.TYPE);
        checkComparator(GvLocationList.GvLocation.TYPE);
        checkComparator(GvFactList.GvFact.TYPE);
        checkComparator(GvUrlList.GvUrl.TYPE);
        checkComparator(GvChildReviewList.GvChildReview.TYPE);
        checkComparator(GvReviewOverviewList.GvReviewOverview.TYPE);
        checkComparator(GvImageList.GvImage.TYPE);
    }

    private <T extends GvData> void checkComparator(GvDataType<T> type) {
        GvCanonicalCollection<T> list = newList(type);
        GvDataList<T> canonicals = FactoryGvData.newDataList(list.getGvDataType());
        for (GvCanonical canonical : list) {
            canonicals.add((T) canonical.getCanonical());
        }
        list.sort();
        canonicals.sort();
        for (int i = 0; i < list.size(); ++i) {
            assertEquals(canonicals.getItem(i), list.getItem(i).getCanonical());
        }
    }

    @SmallTest
    public void testEquals() {
        GvCanonicalCollection<GvCommentList.GvComment> listNotEquals = new GvCanonicalCollection<>
                (GvCommentList.GvComment.TYPE);
        GvCommentList.GvComment comment1 = mList.getItem(0).getCanonical();
        GvCommentList.GvComment comment2 = mList.getItem(1).getCanonical();
        GvCommentList.GvComment comment3 = GvDataMocker.newComment(RandomReviewId.nextGvReviewId());
        GvDataList<GvCommentList.GvComment> similar1 = mList.getItem(0).toList();
        GvDataList<GvCommentList.GvComment> similar2 = mList.getItem(1).toList();
        GvCommentList similar3 = GvDataMocker.newCommentList(NUM, true);

        listNotEquals.add(new GvCanonical<>(comment1, similar1));
        listNotEquals.add(new GvCanonical<>(comment2, similar2));
        listNotEquals.add(new GvCanonical<>(comment3, similar3));

        GvCanonicalCollection<GvCommentList.GvComment> listEquals = FactoryGvData.copy(mList);
        GvCanonicalCollection<GvCommentList.GvComment> listEquals2 = FactoryGvData.copy
                (listNotEquals);

        assertTrue(mList.equals(listEquals));
        assertFalse(mList.equals(listNotEquals));
        assertTrue(listEquals2.equals(listNotEquals));
    }

    @Override
    protected void setUp() throws Exception {
        mList = newList(GvCommentList.GvComment.TYPE);
    }

    private <T extends GvData> GvCanonicalCollection<T> newList(GvDataType<T> type) {
        GvCanonicalCollection<T> list = new GvCanonicalCollection<>(type);
        for (int i = 0; i < NUM; ++i) {
            T canonical = (T) GvDataMocker.getDatum(type);
            GvDataList<T> data = GvDataMocker.getData(type, NUM);
            list.add(new GvCanonical<>(canonical, data));
        }
        return list;
    }
}
