package com.chdryra.android.startouch.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.ParcelableTester;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewId;

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

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mList = newList(GvComment.TYPE);
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
}
