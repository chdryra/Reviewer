/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;
import android.widget.ImageView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutEditImage extends AddEditLayoutBasic<GvImage> {
    public static final int LAYOUT = R.layout.dialog_image_edit;
    public static final int IMAGE = R.id.photo_image_view;
    public static final int CAPTION = R.id.caption_edit_text;
    public static final int[] VIEWS = new int[]{IMAGE, CAPTION};

    private GvImage mCurrent;

    //Constructors
    public LayoutEditImage(GvDataEditor editor) {
        super(GvImage.class, LAYOUT, VIEWS, CAPTION, editor);
    }

    //Overridden
    @Override
    public GvImage createGvData() {
        String caption = ((EditText) getView(CAPTION)).getText().toString().trim();
        mCurrent = new GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), caption, mCurrent.isCover());

        return mCurrent;
    }

    @Override
    public void updateLayout(GvImage image) {
        ImageView imageView = (ImageView) getView(IMAGE);
        EditText imageCaption = (EditText) getView(CAPTION);

        imageView.setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        imageCaption.setText(caption);

        //For some reason setSelection(0) doesn't work unless I force set the span of the selection
        if (caption != null && caption.length() > 0) {
            imageCaption.setSelection(0, caption.length());
            imageCaption.setSelection(0);
        }

        mCurrent = image;
    }
}
