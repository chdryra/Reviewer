/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Layouts.Implementation;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutImage extends DatumLayoutBasic<GvImage> {
    private static final int LAYOUT = R.layout.dialog_image_view;
    private static final int IMAGE = R.id.photo_image_view;
    private static final int CAPTION = R.id.caption_text_view;

    private final int mImageView;
    private final int mImageCaption;
    private final boolean mHideEmptyCaption;

    public ViewLayoutImage() {
        this(LAYOUT, IMAGE, CAPTION, false);
    }

    public ViewLayoutImage(int layout, int imageView, int imageCaption, boolean hideEmptyCaption) {
        super(new LayoutHolder(layout, imageView, imageCaption));
        mImageView = imageView;
        mImageCaption = imageCaption;
        mHideEmptyCaption = hideEmptyCaption;
    }

    @Override
    public void updateView(GvImage image) {
        ((ImageView) getView(mImageView)).setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        TextView view = (TextView) getView(mImageCaption);
        if (caption.length() == 0 && mHideEmptyCaption) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(caption);
        }
    }
}
