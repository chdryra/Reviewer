/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.EditUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.NodeLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewLauncherImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherAndroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUiLauncher {
    private final Class<? extends Activity> mDefaultActivity;

    public FactoryUiLauncher(Class<? extends Activity> defaultActivity) {
        mDefaultActivity = defaultActivity;
    }

    public UiLauncherAndroid newLauncher(RepositorySuite repository,
                                         ReviewEditorSuite builder,
                                         FactoryReviewView viewFactory,
                                         ReviewsSource masterRepo,
                                         UiConfig config) {

        EditUiLauncher buildUi = new EditUiLauncher(config.getBespokeEditor(GvNode.TYPE.getDatumName()), builder, repository);

        List<NodeLauncher<?>> launchers = new ArrayList<>();
        launchers.add(newLauncher(config, GvNode.TYPE));
        launchers.add(newLauncher(config, GvLocation.TYPE));
        ReviewLauncherImpl reviewLauncher
                = new ReviewLauncherImpl(masterRepo, launchers, viewFactory);

        return new UiLauncherAndroid(buildUi, reviewLauncher, mDefaultActivity);
    }

    @NonNull
    private <T extends GvData> NodeLauncher<T> newLauncher(UiConfig config, GvDataType<T> dataType) {
        return new NodeLauncher<>(config.getBespokeViewer(dataType.getDatumName()), dataType);
    }
}
