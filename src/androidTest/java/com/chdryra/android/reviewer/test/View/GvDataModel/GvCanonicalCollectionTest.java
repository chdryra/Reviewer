package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.DataAggregation.GvCanonical;
import com.chdryra.android.reviewer.View.DataAggregation.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
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
    private GvCanonicalCollection<GvComment> mList;

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(mList);
    }

    @SmallTest
    public void testComparator() {
        checkComparator(GvComment.TYPE);
        checkComparator(GvSubject.TYPE);
        checkComparator(GvAuthor.TYPE);
        checkComparator(GvTag.TYPE);
        checkComparator(GvLocation.TYPE);
        checkComparator(GvFact.TYPE);
        checkComparator(GvUrl.TYPE);
        checkComparator(GvCriterion.TYPE);
        checkComparator(GvReviewOverview.TYPE);
        checkComparator(GvImage.TYPE);
    }

    @SmallTest
    public void testEquals() {
        GvCanonicalCollection<GvComment> listNotEquals = new GvCanonicalCollection<>
                (GvComment.TYPE);
        GvComment comment1 = mList.getItem(0).getCanonical();
        GvComment comment2 = mList.getItem(1).getCanonical();
        GvComment comment3 = GvDataMocker.newComment(RandomReviewId.nextGvReviewId());
        GvDataListImpl<GvComment> similar1 = mList.getItem(0).toList();
        GvDataListImpl<GvComment> similar2 = mList.getItem(1).toList();
        GvCommentList similar3 = GvDataMocker.newCommentList(NUM, true);

        listNotEquals.addCanonnical(new GvCanonical<>(comment1, similar1));
        listNotEquals.addCanonnical(new GvCanonical<>(comment2, similar2));
        listNotEquals.addCanonnical(new GvCanonical<>(comment3, similar3));

        GvCanonicalCollection<GvComment> listEquals = FactoryGvData.copy(mList);
        GvCanonicalCollection<GvComment> listEquals2 = FactoryGvData.copy
                (listNotEquals);

        assertTrue(mList.equals(listEquals));
        assertFalse(mList.equals(listNotEquals));
        assertTrue(listEquals2.equals(listNotEquals));
    }

    private <T extends GvData> void checkComparator(GvDataType<T> type) {
        GvCanonicalCollection<T> list = newList(type);
        GvDataListImpl<T> canonicals = FactoryGvData.newDataList(list.getGvDataType());
        for (GvCanonical canonical : list) {
            canonicals.add((T) canonical.getCanonical());
        }
        list.sort();
        canonicals.sort();
        for (int i = 0; i < list.size(); ++i) {
            assertEquals(canonicals.getItem(i), list.getItem(i).getCanonical());
        }
    }

    private <T extends GvData> GvCanonicalCollection<T> newList(GvDataType<T> type) {
        GvCanonicalCollection<T> list = new GvCanonicalCollection<>(type);
        for (int i = 0; i < NUM; ++i) {
            T canonical = (T) GvDataMocker.getDatum(type);
            GvDataListImpl<T> data = GvDataMocker.getData(type, NUM);
            list.addCanonnical(new GvCanonical<>(canonical, data));
        }
        return list;
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mList = newList(GvComment.TYPE);
    }
}
