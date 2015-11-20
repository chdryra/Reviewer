package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.widget.Toast;

import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemEditTag extends GridItemEdit<GvTagList.GvTag> {
    private TagAdjuster mTagAdjuster;

    public GridItemEditTag(LaunchableConfig<GvTagList.GvTag> editorConfig,
                           FactoryLaunchableUi launchableFactory,
                           GvDataPacker<GvTagList.GvTag> dataPacker,
                           TagAdjuster tagAdjuster) {
        super(editorConfig, launchableFactory, dataPacker);
        mTagAdjuster = tagAdjuster;
    }

    @Override
    public void onGvDataDelete(GvTagList.GvTag data, int requestCode) {
        if (data.equals(mTagAdjuster.getCurrentSubjectTag())) {
            String toast = "Cannot delete subject tag...";
            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
        } else {
            super.onGvDataDelete(data, requestCode);
        }
    }
}
