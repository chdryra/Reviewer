/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.ImageView;

import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .View.GvImageList.GvImage}. Shows
 * image bitmap.
 */
public class VhImage extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_image;
    private static final int IMAGE = R.id.image_view;

    private ImageView mImage;

    //Constructors
    public VhImage() {
        super(LAYOUT, new int[]{IMAGE});
    }

    //Overridden
    @Override
    public void updateView(ViewHolderData data) {
        if (mImage == null) mImage = (ImageView) getView(IMAGE);

        GvImage image = (GvImage) data;
        if (image != null && image.isValidForDisplay()) mImage.setImageBitmap(image.getBitmap());
    }
}
