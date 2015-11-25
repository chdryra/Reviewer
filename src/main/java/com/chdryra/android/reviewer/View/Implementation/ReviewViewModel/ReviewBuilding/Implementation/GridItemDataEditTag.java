package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.widget.Toast;

import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDataEditTag extends GridItemDataEdit<GvTag> {
    private TagAdjuster mTagAdjuster;

    public GridItemDataEditTag(LaunchableConfig editorConfig,
                               FactoryLaunchableUi launchableFactory,
                               GvDataPacker<GvTag> dataPacker,
                               TagAdjuster tagAdjuster) {
        super(editorConfig, launchableFactory, dataPacker);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    public void onDelete(GvTag data, int requestCode) {
        if (data.equals(mTagAdjuster.getCurrentSubjectTag())) {
            String toast = "Cannot delete subject tag...";
            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
        } else {
            super.onDelete(data, requestCode);
        }
    }
}
