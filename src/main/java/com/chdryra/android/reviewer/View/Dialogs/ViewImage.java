/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.TextView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewImage extends DialogLayout<GvImageList.GvImage> {
    public static final int LAYOUT = R.layout.dialog_image_view;
    public static final int IMAGE = R.id.photo_image_view;
    public static final int CAPTION = R.id.caption_text_view;
    public static final int[] VIEWS = new int[]{IMAGE, CAPTION};

    //Constructors
    public ViewImage() {
        super(LAYOUT, VIEWS);
    }

    //Overridden
    @Override
    public void updateLayout(GvImageList.GvImage image) {
        android.widget.ImageView imageView = (android.widget.ImageView) getView(IMAGE);
        TextView imageCaption = (TextView) getView(CAPTION);

        imageView.setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        imageCaption.setText(caption);
    }
}
