package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .GridItemEditComment;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.MenuEditComments;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsComments extends FactoryEditActionsDefault<GvCommentList.GvComment> {
    private static final GvDataType<GvCommentList.GvComment> TYPE = GvCommentList.GvComment.TYPE;
    public FactoryEditActionsComments(Context context, ConfigDataUi config,
                                      FactoryLaunchableUi launchableFactory,
                                      FactoryGvData dataFactory,
                                      GvDataPacker<GvCommentList.GvComment> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected GridItemAction<GvCommentList.GvComment> newGridItemEdit() {
        return new GridItemEditComment(getEditorConfig(), getLaunchableFactory(), getPacker());
    }

    @Override
    protected MenuAction<GvCommentList.GvComment> newMenuEdit() {
        return new MenuEditComments();
    }
}
