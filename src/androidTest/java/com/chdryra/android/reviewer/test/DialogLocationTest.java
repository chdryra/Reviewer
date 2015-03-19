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
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;
import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.DialogLocation;
import com.chdryra.android.reviewer.EditScreen;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.LauncherUi;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogLocationTest extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int    REQUEST_CODE   = 1976;
    private static final String DIALOG_TAG     = "DialogFragmentLocation";
    private static final String LISTENER_TAG   = "FragmentListener";
    private final static LatLng LATLNG         = new LatLng(51.5072, -0.1275);
    private final static String ACQUERY        = "Ch";
    private final static String NAME           = "Charing Cross";
    private final static String SEARCHING      = "searching";
    private final static String SEARCHING_OVER = "name";
    private static final int    NAME_NEARBY    = com.chdryra.android.reviewer.R.string
            .edit_text_add_a_location;
    private static final int    NAME_IMAGE     = com.chdryra.android.reviewer.R.string
            .edit_text_name_image_location_hint;

    private DialogLocation   mDialog;
    private FragmentListener mListener;
    private Activity         mActivity;
    private Solo             mSolo;

    public DialogLocationTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testSuggestionsPopulateNotFromImage() {
        launchDialogAndTestPopulated();
    }

    @SmallTest
    public void testSuggestionsPopulateFromImage() {
        launchDialogAndTestShowing(true);
        testSuggestionsPopulate();
    }

    @SmallTest
    public void testSuggestionsPopulateNoLatLngProvided() {
        launchDialogAndTestShowing(null, false);
        testSuggestionsPopulate();
    }

    @SmallTest
    public void testMaxSuggestions() {
        ListView list = launchDialogAndTestPopulated();
        assertEquals(10, list.getCount());
    }

    @SmallTest
    public void testAutoComplete() {
        ListView list = launchDialogAndTestPopulated();
        String entry = (String) list.getAdapter().getItem(0);
        assertFalse(entry.startsWith(ACQUERY)); //should be Trafalgar Square
        mSolo.enterText(mSolo.getEditText(0), ACQUERY);
        mSolo.waitForText(ACQUERY, 2, 5000);
        entry = (String) list.getAdapter().getItem(0);
        assertTrue(entry.startsWith(ACQUERY));
    }

    @SmallTest
    public void testCancelButtonNoName() {
        testCancelOrDoneNoName(true);
    }

    @SmallTest
    public void testCancelButtonWithName() {
        testCancelOrDone(true, true);
    }

    @SmallTest
    public void testDoneButtonNoName() {
        testCancelOrDoneNoName(false);
    }

    @SmallTest
    public void testDoneButtonEnterName() {
        testEditTextPassedToListener(false);
    }

    @SmallTest
    public void testMapButtonNoName() {
        launchDialogAndTestShowing();
        assertFalse(mListener.mapRequested());
        assertFalse(mListener.locationChosen());
        assertNull(mListener.getLocation());

        final FragmentListener listener = mListener;
        final DialogCancelActionDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickActionButton();

                assertTrue(listener.mapRequested());
                assertFalse(listener.locationChosen());
                GvLocationList.GvLocation location = listener.getLocation();
                assertNotNull(location);
                assertEquals(LATLNG, location.getLatLng());
                assertFalse(location.isValidForDisplay());
            }
        });
    }

    @SmallTest
    public void testMapButtonEnterName() {
        testEditTextPassedToListener(true);
    }

    @SmallTest
    public void testClickSuggestionGoesToEditText() {
        ListView list = launchDialogAndTestPopulated();

        String editTextString = mSolo.getEditText(0).getText().toString();
        assertEquals(0, editTextString.length());

        //Next 2 lines because Robotium not otherwise clicking on item...
        mSolo.scrollListToLine(list, list.getCount()); //Need to use this line for click to work...
        ArrayList<TextView> tvs = mSolo.clickInList(2, 0); //Even then can't click on 1...

        assertEquals(1, tvs.size());
        String clicked = tvs.get(0).getText().toString();
        mSolo.waitForText(clicked, 2, 5000);
        assertTrue(mSolo.searchEditText(clicked));

        editTextString = mSolo.getEditText(0).getText().toString();
        assertTrue(clicked.length() > 0);
        assertEquals(clicked, editTextString);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mDialog = new DialogLocation();
        mListener = new FragmentListener();

        Intent i = new Intent();
        Context context = getInstrumentation().getTargetContext();
        Administrator admin = Administrator.get(context);
        admin.packView(EditScreen.newScreen(context, GvLocationList.TYPE), i);
        setActivityIntent(i);

        mActivity = getActivity();

        FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
        ft.add(mListener, LISTENER_TAG);
        ft.commit();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    private void testEditTextPassedToListener(final boolean mapButton) {
        launchDialogAndTestShowing();
        assertFalse(mListener.mapRequested());
        assertFalse(mListener.locationChosen());
        assertNull(mListener.getLocation());

        final String name = RandomString.nextWord();
        mSolo.enterText(mSolo.getEditText(0), name);
        assertTrue(mSolo.searchText(name));

        final FragmentListener listener = mListener;
        final DialogCancelActionDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (mapButton) {
                    dialog.clickActionButton();
                } else {
                    dialog.clickDoneButton();
                }

                assertEquals(mapButton, listener.mapRequested());
                assertEquals(!mapButton, listener.locationChosen());
                GvLocationList.GvLocation location = listener.getLocation();
                assertNotNull(location);
                assertEquals(LATLNG, location.getLatLng());
                assertEquals(name, location.getName());
            }
        });
    }

    private void testCancelOrDoneNoName(final boolean cancelButton) {
        testCancelOrDone(cancelButton, false);
    }

    private void testCancelOrDone(final boolean cancelButton, boolean editName) {
        launchDialogAndTestShowing();
        assertFalse(mListener.mapRequested());
        assertFalse(mListener.locationChosen());
        assertNull(mListener.getLocation());

        if (cancelButton && editName) {
            final String name = RandomString.nextWord();
            mSolo.enterText(mSolo.getEditText(0), name);
            assertTrue(mSolo.searchText(name));
        }

        final FragmentListener listener = mListener;
        final DialogCancelActionDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (cancelButton) {
                    dialog.clickCancelButton();
                } else {
                    dialog.clickDoneButton();
                }

                assertFalse(listener.mapRequested());
                assertFalse(listener.locationChosen());
                assertNull(listener.getLocation());
            }
        });
    }

    private ListView launchDialogAndTestPopulated() {
        launchDialogAndTestShowing();
        return testSuggestionsPopulate();
    }

    private ListView testSuggestionsPopulate() {
        mSolo.waitForText(SEARCHING_OVER);
        assertTrue(mSolo.searchText(SEARCHING_OVER));
        assertFalse(mSolo.searchText(SEARCHING));

        ArrayList<ListView> listViews = mSolo.getCurrentViews(ListView.class);
        assertEquals(1, listViews.size());
        ListView list = listViews.get(0);
        assertTrue(list.getCount() > 0);
        String entry = (String) list.getAdapter().getItem(0);
        assertFalse(entry.contains(SEARCHING));

        return list;
    }

    private void launchDialogAndTestShowing() {
        launchDialogAndTestShowing(false);
    }

    private void launchDialogAndTestShowing(boolean fromImage) {
        launchDialogAndTestShowing(LATLNG, fromImage);
        Resources r = getInstrumentation().getTargetContext().getResources();
        mSolo.waitForText(r.getString(fromImage ? NAME_IMAGE : NAME_NEARBY));
        assertTrue(mSolo.searchText(r.getString(fromImage ? NAME_IMAGE : NAME_NEARBY)));
    }

    private void launchDialogAndTestShowing(LatLng latLng, boolean fromImage) {
        Bundle args = new Bundle();
        args.putParcelable(DialogLocation.LATLNG, latLng);
        args.putBoolean(DialogLocation.FROM_IMAGE, fromImage);

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
    }

    public static class FragmentListener extends Fragment
            implements DialogLocation.DialogFragmentLocationListener {
        private boolean mMapRequested   = false;
        private boolean mLocationChosen = false;
        private GvLocationList.GvLocation mLocation;

        @Override
        public void onLocationChosen(GvLocationList.GvLocation location) {
            mLocationChosen = true;
            mLocation = location;
        }

        @Override
        public void onMapRequested(GvLocationList.GvLocation location) {
            mMapRequested = true;
            mLocation = location;
        }

        boolean mapRequested() {
            return mMapRequested;
        }

        boolean locationChosen() {
            return mLocationChosen;
        }

        GvLocationList.GvLocation getLocation() {
            return mLocation;
        }
    }
}
