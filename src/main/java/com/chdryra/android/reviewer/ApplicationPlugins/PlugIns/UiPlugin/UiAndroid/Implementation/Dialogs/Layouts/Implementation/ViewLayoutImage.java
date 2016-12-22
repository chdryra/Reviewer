/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;


import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutImage extends DialogLayoutBasic<GvImage> {
    public static final int LAYOUT = R.layout.dialog_image_view;
    public static final int IMAGE = R.id.photo_image_view;
    public static final int CAPTION = R.id.caption_text_view;

    public ViewLayoutImage() {
        super(new LayoutHolder(LAYOUT, IMAGE, CAPTION));
    }

    //Overridden
    @Override
    public void updateView(GvImage image) {
        ImageView imageView = (android.widget.ImageView) getView(IMAGE);
        TextView imageCaption = (TextView) getView(CAPTION);

        imageView.setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        imageCaption.setText(caption);
    }
}
