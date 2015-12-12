package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemDataEditComment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuDataEditComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ConfigDataUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsComments extends FactoryEditActionsDefault<GvComment> {
    private static final GvDataType<GvComment> TYPE = GvComment.TYPE;
    public FactoryEditActionsComments(Context context, ConfigDataUi config,
                                      LaunchableUiLauncher launchableFactory,
                                      FactoryGvData dataFactory,
                                      GvDataPacker<GvComment> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected GridItemAction<GvComment> newGridItemEdit() {
        return new GridItemDataEditComment(getEditorConfig(), getLaunchableFactory(), getPacker());
    }

    @Override
    protected MenuAction<GvComment> newMenuEdit() {
        return new MenuDataEditComments();
    }
}
