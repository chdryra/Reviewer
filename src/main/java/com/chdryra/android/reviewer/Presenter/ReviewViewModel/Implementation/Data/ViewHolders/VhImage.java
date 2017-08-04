/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .View.GvImageList.GvImage}. Shows
 * image bitmap.
 */
public class VhImage extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_image;
    private static final int IMAGE = R.id.image_view;

    public VhImage() {
        super(LAYOUT, new int[]{IMAGE});
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvImage image = (GvImage) data;
        setImage(IMAGE, image.getBitmap());
    }
}
