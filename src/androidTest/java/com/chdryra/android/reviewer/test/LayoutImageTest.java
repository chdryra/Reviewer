/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.graphics.drawable.BitmapDrawable;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.LayoutImage;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutImageTest extends AndroidTestCase {
    private LayoutImage mLayout;

    @Override
    public void setUp() throws Exception {
        mLayout = new LayoutImage(new ConfigGvDataAddEdit.EditImage());
    }

    @SmallTest
    public void testGetDialogTitleOnAdd() {
        GvImageList.GvImage image = GvDataMocker.newImage();

        String title = mLayout.getTitleOnAdd(image);
        assertNull(title);
    }

    @SmallTest
    public void testGetDeleteConfirmDialogTitle() {
        GvImageList.GvImage image = GvDataMocker.newImage();
        String deleteConfirm = mLayout.getDeleteConfirmDialogTitle(image);
        assertNotNull(deleteConfirm);
        assertTrue(deleteConfirm.contains(GvImageList.TYPE.getDatumName()));
    }

    @SmallTest
    public void testCreateGvDataFromViews() {
        View v = inflate();
        EditText captionET = (EditText) v.findViewById(LayoutImage.CAPTION);
        ImageView imageView = (ImageView) v.findViewById(LayoutImage.IMAGE);
        assertNotNull(captionET);
        assertNotNull(imageView);

        //unlike other Layouts, passes back what was used to update with new caption if necessary.
        GvImageList.GvImage imageIn = GvDataMocker.newImage();
        mLayout.updateViews(imageIn);

        GvImageList.GvImage imageOut = mLayout.createGvDataFromViews();
        assertNotNull(imageOut);
        assertEquals(imageIn.getBitmap(), imageOut.getBitmap());
        assertEquals(imageIn.getCaption(), imageOut.getCaption());
        assertEquals(imageIn.getLatLng(), imageOut.getLatLng());
    }

    @SmallTest
    public void testUpdateViews() {
        View v = inflate();
        EditText captionET = (EditText) v.findViewById(LayoutImage.CAPTION);
        ImageView imageView = (ImageView) v.findViewById(LayoutImage.IMAGE);
        assertNotNull(captionET);
        assertNotNull(imageView);

        GvImageList.GvImage imageIn = GvDataMocker.newImage();
        assertNull(imageView.getDrawable());
        assertFalse(captionET.getText().toString().trim().equals(imageIn.getCaption()));

        mLayout.updateViews(imageIn);
        assertNotNull(imageView.getDrawable());
        assertTrue(((BitmapDrawable) imageView.getDrawable()).getBitmap().sameAs(imageIn
                .getBitmap()));
        assertTrue(captionET.getText().toString().trim().equals(imageIn.getCaption()));
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mLayout.getViewHolder());
    }

    @SmallTest
    public void testGetEditTextForKeyboardAction() {
        View v = inflate();
        EditText captionET = (EditText) v.findViewById(LayoutImage.CAPTION);
        assertEquals(captionET, mLayout.getEditTextForKeyboardAction());
    }

    private View inflate() {
        mLayout.getViewHolder().inflate(getContext());
        return mLayout.getViewHolder().getView();
    }
}
