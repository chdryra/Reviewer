/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.DialogFragmentLocation;
import com.chdryra.android.reviewer.GvDataPacker;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.LauncherUi;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentLocationTest extends ActivityInstrumentationTestCase2<ActivityFeed> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "DialogFragmentLocation";
    private static final String LISTENER_TAG = "FragmentListener";
    protected ControllerReview mController;
    private DialogFragmentLocation mDialog;
    private   Fragment         mListener;
    private   Activity         mActivity;
    private   Solo             mSolo;

    public DialogFragmentLocationTest() {
        super(ActivityFeed.class);
    }

    @SmallTest
    public void testLaunch() {
        launchDialogAndTestShowing();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mDialog = new DialogFragmentLocation();
        mListener = new FragmentListener();
        mActivity = getActivity();

        FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
        ft.add(mListener, LISTENER_TAG);
        ft.commit();

        mController = Administrator.get(getInstrumentation().getTargetContext())
                .createNewReviewInProgress();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    protected GvLocationList.GvLocation launchDialogAndTestShowing() {
        final GvLocationList.GvLocation datum = GvDataMocker.newLocation();
        Bundle args = getControllerBundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, datum, args);

        assertFalse(mDialog.isShowing());

        LauncherUi.launch(mDialog, mListener, REQUEST_CODE, DIALOG_TAG, args);

        final FragmentManager manager = mActivity.getFragmentManager();
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                manager.executePendingTransactions();

            }
        });

        mSolo.waitForDialogToOpen();

        assertTrue(mDialog.isShowing());

        return datum;
    }

    protected Bundle getControllerBundle() {
        return Administrator.get(getInstrumentation().getTargetContext()).pack(mController);
    }

    public static class FragmentListener extends Fragment {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
