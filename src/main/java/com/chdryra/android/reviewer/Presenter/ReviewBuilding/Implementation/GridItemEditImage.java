/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditImage extends GridItemEdit<GvImage> {
    private static final int IMAGE_AS_COVER = RequestCodeGenerator.getCode("ImageAsCover");

    public GridItemEditImage(LaunchableConfig editorConfig,
                             ParcelablePacker<GvImage> dataPacker) {
        super(editorConfig, dataPacker);
    }

    @Override
    public void onGridItemLongClick(GvImage item, int position, View v) {
        if (item.isCover()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlert(Strings.Alerts.SET_IMAGE_AS_BACKGROUND, IMAGE_AS_COVER, packItem(item));
        }
    }

    @Override
    protected void doAlertPositive(Bundle args) {
        GvImage newCover = unpackItem(args);
        ReviewDataEditor<GvImage> editor = getEditor();
        if (editor.getParams().manageCover()) {
            editor.getCover().setIsCover(false);
            newCover.setIsCover(true);
        }
        editor.notifyDataObservers();
    }
}
