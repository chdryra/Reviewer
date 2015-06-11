/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.EditText;
import android.widget.ImageView;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataEditLayout;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutImage extends GvDataEditLayout<GvImageList.GvImage> {
    public static final int   LAYOUT  = R.layout.dialog_image;
    public static final int   IMAGE   = R.id.dialog_image_image_view;
    public static final int   CAPTION = R.id.dialog_image_caption_edit_text;
    public static final int[] VIEWS   = new int[]{IMAGE, CAPTION};

    private GvImageList.GvImage mCurrent;

    public LayoutImage(GvDataEditor editor) {
        super(GvImageList.GvImage.class, LAYOUT, VIEWS, CAPTION, editor);
    }

    @Override
    public GvImageList.GvImage createGvData() {
        String caption = ((EditText) getView(CAPTION)).getText().toString().trim();
        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), caption, mCurrent.isCover());

        return mCurrent;
    }

    @Override
    public void updateLayout(GvImageList.GvImage image) {
        ImageView imageView = (ImageView) getView(IMAGE);
        EditText imageCaption = (EditText) getView(CAPTION);

        imageView.setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        imageCaption.setText(caption);

        //For some reason setSelection(0) doesn't work unless I force set the span of the selection
        if (DataValidator.validateString(caption)) {
            imageCaption.setSelection(0, caption.length());
            imageCaption.setSelection(0);
        }

        mCurrent = image;
    }
}
