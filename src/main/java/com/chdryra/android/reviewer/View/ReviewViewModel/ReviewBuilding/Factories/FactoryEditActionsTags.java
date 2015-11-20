package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GridItemEditTag;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.MenuEditTags;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.SubjectEditTags;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.TagAdjuster;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsTags extends FactoryEditActionsDefault<GvTagList.GvTag> {
    private static final GvDataType<GvTagList.GvTag> TYPE = GvTagList.GvTag.TYPE;
    private TagAdjuster mTagAdjuster;

    public FactoryEditActionsTags(Context context, ConfigDataUi config,
                                  FactoryLaunchableUi launchableFactory,
                                  FactoryGvData dataFactory,
                                  GvDataPacker<GvTagList.GvTag> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
        mTagAdjuster = new TagAdjuster();
    }

    @Override
    protected SubjectAction<GvTagList.GvTag> newSubjectEdit() {
        return new SubjectEditTags(mTagAdjuster);
    }

    @Override
    protected GridItemAction<GvTagList.GvTag> newGridItemEdit() {
        return new GridItemEditTag(getEditorConfig(), getLaunchableFactory(), getPacker(), mTagAdjuster);
    }

    @Override
    protected MenuAction<GvTagList.GvTag> newMenuEdit() {
        return new MenuEditTags(mTagAdjuster);
    }
}
