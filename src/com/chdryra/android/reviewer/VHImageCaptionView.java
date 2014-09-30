/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVImageList.GVImage;

class VHImageCaptionView extends ViewHolderBasic {
    private static final int LAYOUT  = R.layout.grid_cell_image_caption;
    private static final int IMAGE   = R.id.image_view;
    private static final int CAPTION = R.id.caption;

    private ImageView mImage;
    private TextView  mCaption;

    public VHImageCaptionView() {
        super(LAYOUT);
    }

    @Override
    public View updateView(GVData data) {
        GVImage image = (GVImage) data;
        if (image != null) {
            Bitmap bitmap = image.getBitmap();
            if (bitmap != null) {
                mImage.setImageBitmap(image.getBitmap());
                mCaption.setVisibility(View.GONE);
            } else {
                mImage.setVisibility(View.GONE);
                mCaption.setText(image.getCaption());
            }
        }

        return mInflated;
    }

    @Override
    protected void initViewsToUpdate() {
        mImage = (ImageView) getView(IMAGE);
        mCaption = (TextView) getView(CAPTION);
    }
}
