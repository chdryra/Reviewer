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

public class NodeLauncher<T extends GvData> extends PackingLauncherImpl<ReviewNode> {
    private static final String PUBLISHED = TagKeyGenerator.getTag(NodeLauncher.class, "published");
    private static final String INDEX = TagKeyGenerator.getTag(NodeLauncher.class, "index");
    private final GvDataType<T> mType;

    public NodeLauncher(LaunchableConfig ui, GvDataType<T> dataType) {
        super(ui);
        mType = dataType;
    }

    public GvDataType<T> getDataType() {
        return mType;
    }

    @Override
    public void launch(@Nullable ReviewNode item) {
        launch(item, 0, false, new Bundle());
    }

    public void launch(@Nullable ReviewNode item, int datumIndex, boolean isPublished, Bundle bundle) {
        bundle.putInt(INDEX, datumIndex);
        bundle.putBoolean(PUBLISHED, isPublished);
        launch(item, bundle);
    }

    public static boolean isPublished(Bundle args) {
        return args.getBoolean(PUBLISHED);
    }

    public static int getIndex(Bundle args) {
        return args.getInt(INDEX);
    }
}
