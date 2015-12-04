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

import com.chdryra.android.reviewer.View.LauncherModel.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Implementation.LayoutEditImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutEditImageTest extends AddEditLayoutTest<GvImage> {
    private ImageView mImageView;

    //Constructors
    public LayoutEditImageTest() {
        super(GvImage.TYPE, new LayoutEditImage(new DefaultLaunchables.EditImage()));
    }

    //Overridden
    @Override
    protected void enterData(GvImage datum) {
        mLayout.updateLayout(datum);
    }

    @Override
    protected void checkViewAndDataEquivalence(GvImage datum, boolean result) {
        assertEquals(result, ((BitmapDrawable) mImageView.getDrawable()).getBitmap().sameAs
                (datum.getBitmap()));
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getCaption()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(LayoutEditImage.CAPTION);
        mImageView = (ImageView) getView(LayoutEditImage.IMAGE);
        assertNotNull(mEditText);
        assertNotNull(mImageView);
    }
}
