/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class NodeDataLauncher<T extends GvData> extends PackingLauncherImpl<ReviewNode> {
    private static final String INDEX = TagKeyGenerator.getTag(NodeDataLauncher.class);
    private final GvDataType<T> mType;

    public NodeDataLauncher(LaunchableConfig ui, GvDataType<T> dataType) {
        super(ui);
        mType = dataType;
    }

    @Override
    public void launch(@Nullable ReviewNode item) {
        launch(item, 0);
    }

    public void launch(@Nullable ReviewNode item, int datumIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, datumIndex);
        launch(item, bundle);
    }

    public boolean isOfType(GvDataType<?> type) {
        return mType.equals(type);
    }

    public static int getIndex(Bundle args) {
        return args.getInt(INDEX);
    }
}
