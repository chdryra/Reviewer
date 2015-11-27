package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GridItemDataEditComment;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.MenuDataEditComments;

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
