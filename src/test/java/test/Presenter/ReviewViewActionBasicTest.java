/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Presenter;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.UnattachedReviewViewException;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActionBasic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

/**
 * Created by: Rizwan Choudrey
 * On: 02/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewViewActionBasicTest {
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();
    @Mock
    private ReviewView<GvData> mReviewView;
    private ReviewViewActionBasic<GvData> mReviewViewAction;
    private ArrayList<Integer> mOrder = new ArrayList<>();
    private static final int ON_UNATTACH = 0;
    private static final int ON_ATTACH = 1;

    @Before
    public void setUp() {
        mReviewViewAction = new TestReviewViewAction();
    }

    @Test
    public void getReviewViewThrowsExceptionIfNoReviewViewAttached() {
        mExpectedException.expect(UnattachedReviewViewException.class);
        mReviewViewAction.getReviewView();
    }

    @Test
    public void getAdapterThrowsExceptionIfNoReviewViewAttached() {
        mExpectedException.expect(UnattachedReviewViewException.class);
        mReviewViewAction.getAdapter();
    }

    @Test
    public void getAppThrowsExceptionIfNoReviewViewAttached() {
        mExpectedException.expect(UnattachedReviewViewException.class);
        mReviewViewAction.getApp();
    }

    @Test
    public void attacheReviewViewCallsOnAttachOnlyIfNoReviewViewCurrentlyAttached() {
        assertThat(mOrder.size(), is(0));
        mReviewViewAction.attachReviewView(mReviewView);
        assertThat(mOrder.size(), is(1));
        assertThat(mOrder.get(0), is(ON_ATTACH));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void attacheReviewViewCallsOnUnattachThenOnAttachIfReviewViewCurrentlyAttached() {
        mReviewViewAction.attachReviewView(mReviewView);
        mOrder = new ArrayList<>();
        mReviewViewAction.attachReviewView(mock(ReviewView.class));
        assertThat(mOrder.size(), is(2));
        assertThat(mOrder.get(0), is(ON_UNATTACH));
        assertThat(mOrder.get(1), is(ON_ATTACH));
    }

    private class TestReviewViewAction extends ReviewViewActionBasic<GvData> {
        @Override
        public void onDetachReviewView() {
            mOrder.add(ON_UNATTACH);
        }

        @Override
        public void onAttachReviewView() {
            mOrder.add(ON_ATTACH);
        }
    }
}
