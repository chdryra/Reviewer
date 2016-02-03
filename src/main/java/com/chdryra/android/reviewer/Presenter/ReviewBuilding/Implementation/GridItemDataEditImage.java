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

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditImage extends GridItemDataEdit<GvImage> {
    private static final int IMAGE_AS_COVER = RequestCodeGenerator.getCode("ImageAsCover");

    public GridItemDataEditImage(LaunchableConfig editorConfig,
                                 LaunchableUiLauncher launchableFactory,
                                 GvDataPacker<GvImage> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
    }

    @Override
    public void onGridItemLongClick(GvImage item, int position, View v) {
        if (item.isCover()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_set_image_as_background),
                    IMAGE_AS_COVER, packItem(item));
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
        editor.notifyObservers();
    }
}
