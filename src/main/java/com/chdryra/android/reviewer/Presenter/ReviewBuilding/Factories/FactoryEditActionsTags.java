/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditTags;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.TagAdjuster;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsTags extends FactoryEditActionsDefault<GvTag> {
    private static final GvDataType<GvTag> TYPE = GvTag.TYPE;
    private final TagAdjuster mTagAdjuster;

    public FactoryEditActionsTags(Context context, ConfigUi config,
                                  FactoryGvData dataFactory,
                                  ParcelablePacker<GvTag> packer) {
        super(context, TYPE, config, dataFactory, packer);
        mTagAdjuster = new TagAdjuster();
    }

    @Override
    protected SubjectAction<GvTag> newSubjectEdit() {
        return new SubjectEditTags(mTagAdjuster);
    }
}
