package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemDataEditTag;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuDataEditTags;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectDataEditTags;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.TagAdjuster;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsTags extends FactoryEditActionsDefault<GvTag> {
    private static final GvDataType<GvTag> TYPE = GvTag.TYPE;
    private TagAdjuster mTagAdjuster;

    public FactoryEditActionsTags(Context context, ConfigUi config,
                                  LaunchableUiLauncher launchableFactory,
                                  FactoryGvData dataFactory,
                                  GvDataPacker<GvTag> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
        mTagAdjuster = new TagAdjuster();
    }

    @Override
    protected SubjectAction<GvTag> newSubjectEdit() {
        return new SubjectDataEditTags(mTagAdjuster);
    }

    @Override
    protected GridItemAction<GvTag> newGridItemEdit() {
        return new GridItemDataEditTag(getEditorConfig(), getLaunchableFactory(), getPacker(), mTagAdjuster);
    }

    @Override
    protected MenuAction<GvTag> newMenuEdit() {
        return new MenuDataEditTags(mTagAdjuster);
    }
}
