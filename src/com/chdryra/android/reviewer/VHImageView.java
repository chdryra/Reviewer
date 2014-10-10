/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.ImageView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVImageList.GVImage;

/**
 * ViewHolder: GVImage. Shows image bitmap.
 *
 * @see com.chdryra.android.reviewer.GVImageList.GVImage
 */
class VHImageView extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_image;
    private static final int IMAGE  = R.id.image_view;

    private ImageView mImage;

    public VHImageView() {
        super(LAYOUT, new int[]{IMAGE});
    }

    @Override
    public void updateView(GVData data) {
        if (mImage == null) mImage = (ImageView) getView(IMAGE);

        GVImage image = (GVImage) data;
        if (image != null) mImage.setImageBitmap(image.getBitmap());
    }
}
