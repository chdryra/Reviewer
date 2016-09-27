/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewEditorDefault;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsBuild;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewEditor<GC extends GvDataList<? extends GvDataParcelable>> {
    private final FactoryReviewBuilderAdapter<GC> mFactoryReviewBuilderAdapter;
    private final FactoryReviewDataEditor mFactoryDataEditor;
    private final FactoryReviewViewParams mParamsFactory;
    private final LaunchableConfig mShareUi;


    public FactoryReviewEditor(FactoryReviewBuilderAdapter<GC> factoryReviewBuilderAdapter,
                               FactoryReviewDataEditor factoryDataEditor, FactoryReviewViewParams
                                       paramsFactory, LaunchableConfig shareUi) {
        mFactoryReviewBuilderAdapter = factoryReviewBuilderAdapter;
        mFactoryDataEditor = factoryDataEditor;
        mParamsFactory = paramsFactory;
        mShareUi = shareUi;
    }

    public ReviewEditor<GC>
    newEditor(@Nullable Review template) {
        ReviewBuilderAdapter<GC> adapter = mFactoryReviewBuilderAdapter.newAdapter(template);
        ReviewViewParams params = mParamsFactory.newBuildReviewParams();
        ReviewViewActions<GC> actions
                = new ReviewViewActions<>(new FactoryActionsBuild<>(adapter.getGvDataType(), mShareUi));
        return new ReviewEditorDefault<>(adapter, actions, params, mFactoryDataEditor);
    }
}
