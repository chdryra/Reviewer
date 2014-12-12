/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * {@link DialogHolderAddEdit}: images
 */
class DHImageEdit extends DialogHolderAddEdit<GvImageList.GvImage> {
    private static final int LAYOUT  = R.layout.dialog_image;
    private static final int IMAGE   = R.id.dialog_image_image_view;
    private static final int CAPTION = R.id.dialog_image_caption_edit_text;
    private final DialogGvDataEditFragment<GvImageList.GvImage> mDialogEdit;

    DHImageEdit(DialogGvDataEditFragment<GvImageList.GvImage> dialogEdit) {
        super(LAYOUT, new int[]{IMAGE, CAPTION}, dialogEdit);
        mDialogEdit = dialogEdit;
        mDialogEdit.setDialogTitle(null);
        mDialogEdit.hideKeyboardOnLaunch();
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(CAPTION);
    }

    @Override
    protected String getDialogOnAddTitle(GvImageList.GvImage data) {
        //Standard add dialog not used for images
        return null;
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GvImageList.GvImage data) {
        return GvDataList.GvType.IMAGES.getDatumString();
    }

    @Override
    protected GvImageList.GvImage createGvData() {
        GvImageList.GvImage currentDatum = mDialogEdit.getGvData();
        String caption = ((EditText) getView(CAPTION)).getText().toString().trim();
        currentDatum.setCaption(caption);
        return currentDatum;
    }

    @Override
    protected void updateWithGvData(GvImageList.GvImage image) {
        ImageView imageView = (ImageView) getView(IMAGE);
        EditText imageCaption = (EditText) getView(CAPTION);

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
    }
}
