/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemEditComment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuEditComments;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .MaiSplitCommentVals;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsComments extends FactoryEditActionsDefault<GvComment> {
    private static final GvDataType<GvComment> TYPE = GvComment.TYPE;
    public FactoryEditActionsComments(Context context, ConfigUi config,
                                      FactoryGvData dataFactory,
                                      ParcelablePacker<GvComment> packer) {
        super(context, TYPE, config, dataFactory, packer);
    }

    @Override
    protected GridItemAction<GvComment> newGridItemEdit() {
        return new GridItemEditComment(getEditorConfig(), getPacker());
    }

    @Override
    protected MenuAction<GvComment> newMenuEdit() {
        return new MenuEditComments(new MaiSplitCommentVals());
    }
}
