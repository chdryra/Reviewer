/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewDataEditorImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDataEditor {
    private final FactoryReviewViewParams mParamsFactory;
    private final FactoryEditActions mActionsFactory;

    public FactoryReviewDataEditor(FactoryReviewViewParams paramsFactory,
                                   FactoryEditActions actionsFactory) {
        mParamsFactory = paramsFactory;
        mActionsFactory = actionsFactory;
    }

    public <T extends GvDataParcelable> ReviewDataEditor<T> newEditor(DataBuilderAdapter<T> adapter) {
        GvDataType<T> type = adapter.getGvDataType();
        return new ReviewDataEditorImpl<>(adapter,
                mActionsFactory.newActions(type), mParamsFactory.newEditorParams(type));
    }
}
