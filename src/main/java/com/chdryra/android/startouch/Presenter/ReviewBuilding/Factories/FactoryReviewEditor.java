/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ReviewEditorDefault;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsBuildReview;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewEditor<GC extends GvDataList<? extends GvDataParcelable>> {
    private final UiConfig mConfig;
    private final FactoryReviewBuilderAdapter<GC> mAdapterFactory;
    private final FactoryReviewViewActions mActionsFactory;
    private final FactoryReviewViewParams mParamsFactory;
    private final FactoryReviewDataEditor mFactoryDataEditor;
    private final FactoryFileIncrementor mFactoryFileIncrementor;
    private final FactoryImageChooser mFactoryImageChooser;

    public FactoryReviewEditor(UiConfig config,
                               FactoryReviewBuilderAdapter<GC> adapterFactory,
                               FactoryReviewViewActions actionsFactory,
                               FactoryReviewViewParams paramsFactory,
                               FactoryReviewDataEditor factoryDataEditor,
                               FactoryFileIncrementor factoryFileIncrementor,
                               FactoryImageChooser factoryImageChooser) {
        mConfig = config;
        mAdapterFactory = adapterFactory;
        mActionsFactory = actionsFactory;
        mParamsFactory = paramsFactory;
        mFactoryDataEditor = factoryDataEditor;
        mFactoryFileIncrementor = factoryFileIncrementor;
        mFactoryImageChooser = factoryImageChooser;
    }

    public ReviewEditor<GC> newReviewCreator(ReviewEditor.EditMode defaultMode,
                                             LocationClient locationClient,
                                             @Nullable Review template) {
        ReviewBuilderAdapter<GC> adapter = mAdapterFactory.newCreateAdapter(template);
        ReviewEditor.EditMode editMode = defaultMode;
        if (template != null && (template.getCriteria().size() > 0 || template.getFacts().size()
                > 0)) {
            editMode = ReviewEditor.EditMode.FULL;
        }
        FactoryActionsBuildReview<GC> factory
                = mActionsFactory.newBuilderActions(adapter.getGvDataType(), editMode,
                locationClient);

        return newEditor(adapter, factory);
    }

    public ReviewEditor<GC> newReviewEditor(Review review, ReviewPublisher publisher,
                                            PublishAction.PublishCallback callback,
                                            LocationClient locationClient) {
        ReviewBuilderAdapter<GC> adapter = mAdapterFactory.newEditAdapter(review);

        FactoryActionsBuildReview<GC> factory
                = mActionsFactory.newEditorActions(adapter.getGvDataType(), ReviewEditor.EditMode
                        .FULL, locationClient,
                new PublishAction(publisher, callback));

        return newEditor(adapter, factory);
    }

    private ReviewEditor<GC> newEditor(ReviewBuilderAdapter<GC> adapter,
                                       FactoryActionsBuildReview<GC> factory) {
        ReviewViewActions<GC> actions = new ReviewViewActions<>(factory);
        ReviewViewParams params = mParamsFactory.newBuildReviewParams();

        return new ReviewEditorDefault<>(adapter, actions, params, mConfig.getUiLauncher(),
                mFactoryDataEditor, mFactoryFileIncrementor, mFactoryImageChooser);
    }
}
