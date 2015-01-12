/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * UI Fragment: images. Each grid cell shows an image.
 * <p/>
 * <p>
 * Functionality that differs from base class:
 * <ul>
 * <li>Banner button: calls {@link com.chdryra.android.reviewer
 * .HelperReviewImage#getImageChooserIntents(android.app.Activity)}</li>
 * <li>Grid cell long click: launches {@link com.chdryra.android.reviewer
 * .DialogSetImageAsBackgroundFragment}</li>
 * </ul>
 * </p>
 */
public class FragmentReviewImages extends FragmentReviewGridAddEdit<GvImageList.GvImage>
        implements ImageChooser.ImageChooserListener, DialogAlertFragment.DialogAlertListener {
    private static final String POSITION = "com.chdryra.android.reviewer.image_position";

    private static final String DIALOG_TAG     = "DialogAlertTag";
    private static final int    IMAGE_AS_COVER = 200;

    private GvImageList  mImages;
    private ImageChooser mImageChooser;

    public FragmentReviewImages() {
        super(GvImageList.class);
        setAddResultCode(ActivityResultCode.OK);
        setGridCellDimension(CellDimension.HALF, CellDimension.HALF);
    }

    @Override
    public void onImageChosen(GvImageList.GvImage image) {
        doDatumAdd(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getRequestCodeAdd() && mImageChooser.chosenImageExists
                (ActivityResultCode.get(resultCode), data)) {
            mImageChooser.getChosenImage(this);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = (GvImageList) getGridData();
        mImageChooser = new ImageChooser(getController(), getActivity());
    }

    @Override
    void onBannerButtonClick() {
        Intent options = mImageChooser.getChooserIntents();
        startActivityForResult(options, getRequestCodeAdd());
    }

    @Override
    boolean doDatumAdd(final GvImageList.GvImage image) {
        boolean success = super.doDatumAdd(image);
        if (success && mImages.size() == 1) setCover(0);

        return success;
    }

    @Override
    void doDatumDelete(GvImageList.GvImage image) {
        super.doDatumDelete(image);
        if (image.isCover()) setCover(0);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == IMAGE_AS_COVER) {
            setCover(args.getInt(POSITION));
            updateUi();
        } else {
            super.onAlertPositive(requestCode, args);
        }
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        if (mImages.getItem(position).isCover()) return;

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        String alert = getResources().getString(R.string.dialog_set_image_as_background);
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
        DialogShower.show(dialog, FragmentReviewImages.this, IMAGE_AS_COVER, DIALOG_TAG);
    }

    @Override
    void updateCover() {
        updateCover(mImages);
    }

    private void setCover(int position) {
        if (mImages.size() > 0 && position < mImages.size()) {
            for (GvImageList.GvImage image : mImages) {
                image.setIsCover(false);
            }

            mImages.getItem(position).setIsCover(true);
        }

        updateCover();
    }
}
