package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditImage extends GridItemDataEdit<GvImage> {
    private static final int IMAGE_AS_COVER = RequestCodeGenerator.getCode("ImageAsCover");

    //Constructors
    public GridItemDataEditImage(LaunchableConfig editorConfig,
                                 FactoryLaunchableUi launchableFactory,
                                 GvDataPacker<GvImage> dataPacker) {
        super(editorConfig, launchableFactory, dataPacker);
    }

    //Overridden
    @Override
    public void onGridItemLongClick(GvImage item, int position, View v) {
        if (item.isCover()) {
            super.onGridItemLongClick(item, position, v);
        } else {
            showAlertDialog(getActivity().getString(R.string.alert_set_image_as_background),
                    IMAGE_AS_COVER, item);
        }
    }

    @Override
    protected void deleteData(GvImage datum) {
        super.deleteData(datum);
        if (datum.isCover()) getReviewView().updateCover();
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == IMAGE_AS_COVER) {
            GvImage cover = unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
            setCover(cover);
        }
    }

    private void setCover(GvImage image) {
        ReviewDataEditor<GvImage> editor = getEditor();
        if (editor.getParams().manageCover()) {
            GvImage cover = editor.getCover();
            cover.setIsCover(false);
            image.setIsCover(true);
        }
        editor.notifyObservers();
    }
}
