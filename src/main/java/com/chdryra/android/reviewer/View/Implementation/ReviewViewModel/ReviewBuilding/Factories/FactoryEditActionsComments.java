package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
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
                                      FactoryLaunchableUi launchableFactory,
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
