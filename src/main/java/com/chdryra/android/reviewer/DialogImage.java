/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogImage extends DialogGvDataBasic<GvImageList.GvImage> {
    private static final int   LAYOUT  = R.layout.dialog_image;
    private static final int   IMAGE   = R.id.dialog_image_image_view;
    private static final int   CAPTION = R.id.dialog_image_caption_edit_text;
    private static final int[] VIEWS   = new int[]{IMAGE, CAPTION};

    private final DialogGvDataEditFragment<GvImageList.GvImage> mDialogEdit;
    private       GvImageList.GvImage                           mCurrent;

    DialogImage(DialogGvDataEditFragment<GvImageList.GvImage> dialogEdit) {
        super(LAYOUT, VIEWS, CAPTION, dialogEdit);
        mDialogEdit = dialogEdit;
        mDialogEdit.setDialogTitle(null);
        mDialogEdit.hideKeyboardOnLaunch();
    }

    @Override
    public String getDialogTitleOnAdd(GvImageList.GvImage data) {
        //Standard add dialog not used for images
        return null;
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvImageList.GvImage data) {
        return GvDataList.GvType.IMAGES.getDatumString();
    }

    @Override
    public GvImageList.GvImage createGvDataFromViews() {
        GvImageList.GvImage currentDatum = mCurrent;//mDialogEdit.getCurrentGvData();
        String caption = ((EditText) mViewHolder.getView(CAPTION)).getText().toString().trim();
        currentDatum.setCaption(caption);
        return currentDatum;
    }

    @Override
    public void updateViews(GvImageList.GvImage image) {
        ImageView imageView = (ImageView) mViewHolder.getView(IMAGE);
        EditText imageCaption = (EditText) mViewHolder.getView(CAPTION);

        imageView.setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        imageCaption.setText(image.getCaption());
        imageCaption.setHint(mDialogEdit.getActivity().getResources().getString(R.string
                .edit_text_image_caption_hint));

        //For some reason setSelection(0) doesn't work unless I force set the span of the selection
        if (caption != null && caption.length() > 0) {
            imageCaption.setSelection(0, caption.length());
            imageCaption.setSelection(0);
        }

        mCurrent = image;
    }
}
