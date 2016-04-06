/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Fragment;
import android.app.FragmentManager;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditImagesTest extends ActivityEditScreenTest<GvImage> {

    //Constructors
    public ActivityEditImagesTest() {
        super(GvImage.TYPE);
    }

    @SmallTest
    public void testSetAsCover() {
        setUp(true);
        GvImageList images = (GvImageList) mData;
        for (GvImage image : images) {
            image.setIsCover(false);
        }
        images.getItem(0).setIsCover(true);

        GvImage oldCover = getGridItem(0);
        assertNotNull(oldCover);
        assertTrue(oldCover.isCover());
        for (int i = 0; i < images.size(); ++i) {
            if (i == 0) {
                assertTrue(images.getItem(i).isCover());
                assertEquals(images.getItem(i), oldCover);
            } else {
                assertFalse(images.getItem(i).isCover());
            }
        }

        String alert = getInstrumentation().getTargetContext().getResources().getString(R.string
                .alert_set_image_as_background);

        assertFalse(mSolo.searchText(alert));
        clickLongOnGridItem(1);
        mSolo.waitForDialogToOpen(TIMEOUT);
        assertTrue(mSolo.searchText(alert));

        runOnUiThread(new Runnable() {
            //Overridden
            @Override
            public void run() {
                mSignaler.reset();
                getAlertDialog().clickPositiveButton();
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();
        mSolo.waitForDialogToClose(TIMEOUT);
        assertFalse(mSolo.searchText(alert));

        GvImage newCover = getGridItem(0);
        assertNotNull(newCover);
        assertTrue(newCover.isCover());
        assertFalse(oldCover.isCover());
        assertFalse(oldCover.equals(newCover));
        images.sort();
        for (int i = 0; i < images.size(); ++i) {
            if (i == 0) {
                assertTrue(images.getItem(i).isCover());
                assertEquals(images.getItem(i), newCover);
            } else {
                assertFalse(images.getItem(i).isCover());
            }
        }


        clickOnGridItem(0);
        waitForLaunchableToLaunch();
        clickEditDelete();
        mSolo.waitForDialogToOpen(TIMEOUT);
        clickDeleteConfirm();
        waitForLaunchableToClose();

        GvImage newnewCover = getGridItem(0);
        assertNotNull(newnewCover);
        assertTrue(newnewCover.isCover());
        assertFalse(newCover.equals(newnewCover));
    }

    //private methods
    private DialogAlertFragment getAlertDialog() {
        FragmentManager manager = getEditActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogAlertFragment.ALERT_TAG);
        return (DialogAlertFragment) f;
    }

    //Overridden
    @SmallTest
    public void testBannerButtonAddDone() {
        setUp(false);

        mSolo.clickOnButton("Add " + GvImage.TYPE.getDatumName());
        getInstrumentation().waitForIdleSync();

        assertTrue(mSolo.searchText("Select Source"));
        assertTrue(mSolo.searchText("Camera"));
        assertTrue(mSolo.searchText("Gallery"));
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
    }

    @Override
    public void testBannerButtonAddCancel() {

    }

    @Override
    protected GvImage newEditDatum(GvImage current) {
        return new GvImage(current.getBitmap(), current.getDate(),
                current.getLatLng(), RandomString.nextSentence(), current.isCover());
    }
}

