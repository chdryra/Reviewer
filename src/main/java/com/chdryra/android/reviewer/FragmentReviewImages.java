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
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment.ActionType;

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
public class FragmentReviewImages extends FragmentReviewGridAddEdit<GVImageList.GvImage>
        implements ImageChooser.ImageChooserListener {
    private static final String POSITION = "com.chdryra.android.reviewer.image_position";

    private static final String IMAGE_BACKGROUND_TAG = "DataEditTag";
    private static final int    IMAGE_AS_BACKGROUND  = 20;

    private GVImageList  mImages;
    private ImageChooser mImageChooser;

    public FragmentReviewImages() {
        super(GVDataList.GvType.IMAGES, new InputHandlerImage());
        setAddResultCode(ActivityResultCode.OK);
        setGridCellDimension(CellDimension.HALF, CellDimension.HALF);
    }

    @Override
    public void onImageChosen(GVImageList.GvImage image) {
        doDatumAdd(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode resCode = ActivityResultCode.get(resultCode);
        if (requestCode == getRequestCodeAdd() &&
                mImageChooser.chosenImageExists(resCode, data)) {
            mImageChooser.getChosenImage(this);

        } else if (requestCode == IMAGE_AS_BACKGROUND && resCode.equals(ActionType.YES
                .getResultCode())) {
            setCover(data.getIntExtra(POSITION, 0));
            updateUI();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = (GVImageList) getInputHandler().getData();
        mImageChooser = new ImageChooser(getController(), getActivity());
    }

    @Override
    void onBannerButtonClick() {
        Intent options = mImageChooser.getChooserIntents();
        startActivityForResult(options, getRequestCodeAdd());
    }

    @Override
    boolean doDatumAdd(final GVImageList.GvImage image) {
        boolean success = getInputHandler().add(image, getActivity());
        if (success) {
            if (mImages.size() == 1) setCover(0);
            updateUI();
        }

        return success;
    }

    @Override
    void doDatumDelete(GVImageList.GvImage image) {
        super.doDatumDelete(image);
        if (image.isCover()) setCover(0);
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        if (mImages.getItem(position).isCover()) return;

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        DialogShower.show(new DialogSetImageAsBackgroundFragment(), FragmentReviewImages.this,
                IMAGE_AS_BACKGROUND, IMAGE_BACKGROUND_TAG, args);
    }

    @Override
    void updateCover() {
        updateCover(mImages);
    }

    private void setCover(int position) {
        if (mImages.size() > 0 && position < mImages.size()) {
            for (GVImageList.GvImage image : mImages) {
                image.setIsCover(false);
            }

            mImages.getItem(position).setIsCover(true);
        }

        updateCover();
    }
}
