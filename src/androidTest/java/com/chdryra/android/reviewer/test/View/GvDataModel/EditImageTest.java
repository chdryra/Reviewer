/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.graphics.drawable.BitmapDrawable;
import android.widget.EditText;
import android.widget.ImageView;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.Dialogs.EditImage;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class EditImageTest extends AddEditLayoutTest<GvImageList.GvImage> {
    private ImageView mImageView;

    public EditImageTest() {
        super(GvImageList.TYPE, new EditImage(new ConfigGvDataAddEditView.EditImage()));
    }

    @Override
    protected void enterData(GvImageList.GvImage datum) {
        mLayout.updateLayout(datum);
    }

    @Override
    protected void checkViewAndDataEquivalence(GvImageList.GvImage datum, boolean result) {
        assertEquals(result, ((BitmapDrawable) mImageView.getDrawable()).getBitmap().sameAs
                (datum.getBitmap()));
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getCaption()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(EditImage.CAPTION);
        mImageView = (ImageView) getView(EditImage.IMAGE);
        assertNotNull(mEditText);
        assertNotNull(mImageView);
    }
}