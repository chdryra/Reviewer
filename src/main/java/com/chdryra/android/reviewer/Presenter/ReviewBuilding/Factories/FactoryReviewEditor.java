/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewEditorDefault;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsBuild;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsEditReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewEditor<GC extends GvDataList<? extends GvDataParcelable>> {
    private final FactoryReviewBuilderAdapter<GC> mFactoryAdapter;
    private final FactoryReviewViewParams mParamsFactory;
    private final FactoryReviewDataEditor mFactoryDataEditor;
    private final FactoryFileIncrementor mFactoryFileIncrementor;
    private final FactoryImageChooser mFactoryImageChooser;
    private final FactoryCommands mFactoryCommands;

    public FactoryReviewEditor(FactoryReviewBuilderAdapter<GC> factoryAdapter,
                               FactoryReviewViewParams paramsFactory,
                               FactoryReviewDataEditor factoryDataEditor,
                               FactoryFileIncrementor factoryFileIncrementor,
                               FactoryImageChooser factoryImageChooser,
                               FactoryCommands factoryCommands) {
        mFactoryAdapter = factoryAdapter;
        mParamsFactory = paramsFactory;
        mFactoryDataEditor = factoryDataEditor;
        mFactoryFileIncrementor = factoryFileIncrementor;
        mFactoryImageChooser = factoryImageChooser;
        mFactoryCommands = factoryCommands;
    }

    public ReviewEditor<GC> newReviewCreator(UiConfig config,
                                             UiLauncher launcher,
                                             ReviewEditor.EditMode defaultMode,
                                             LocationClient locationClient,
                                             @Nullable Review template) {
        ReviewBuilderAdapter<GC> adapter = mFactoryAdapter.newCreateAdapter(template);
        ReviewEditor.EditMode editMode = defaultMode;
        if(template != null && (template.getCriteria().size() > 0 || template.getFacts().size() > 0)) {
            editMode = ReviewEditor.EditMode.FULL;
        }
        FactoryActionsBuild<GC> factory
                = new FactoryActionsBuild<>(adapter.getGvDataType(), config, launcher,
                mFactoryCommands, editMode, locationClient);

        return newEditor(launcher, adapter, factory);
    }

    private ReviewEditor<GC> newEditor(UiLauncher launcher,
                                       ReviewBuilderAdapter<GC> adapter,
                                       FactoryActionsBuild<GC> factory) {
        ReviewViewActions<GC> actions = new ReviewViewActions<>(factory);
        ReviewViewParams params = mParamsFactory.newBuildReviewParams();

        return new ReviewEditorDefault<>(adapter, actions, params, launcher, mFactoryDataEditor,
                mFactoryFileIncrementor, mFactoryImageChooser);
    }

    public ReviewEditor<GC> newReviewEditor(UiConfig config,
                                            UiLauncher launcher,
                                            LocationClient locationClient,
                                            Review review,
                                            ReviewPublisher publisher,
                                            PublishAction.PublishCallback callback) {
        ReviewBuilderAdapter<GC> adapter = mFactoryAdapter.newEditAdapter(review);

        FactoryActionsBuild<GC> factory
                = new FactoryActionsEditReview<>(adapter.getGvDataType(), config, launcher,
                mFactoryCommands, ReviewEditor.EditMode.FULL, locationClient,
                new PublishAction(publisher, callback));

        return newEditor(launcher, adapter, factory);
    }
}
