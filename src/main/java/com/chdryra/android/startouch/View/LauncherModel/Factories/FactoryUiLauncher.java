/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.LauncherModel.Factories;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.EditUiLauncher;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.NodeLauncher;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.ReviewLauncherImpl;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherAndroid;

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

    public UiLauncherAndroid newLauncher(EditorSuite builder,
                                         FactoryReviewView viewFactory,
                                         ReviewNodeRepo masterRepo,
                                         UiConfig config) {
        List<NodeLauncher<?>> launchers = new ArrayList<>();
        launchers.add(newLauncher(config, GvNode.TYPE));
        launchers.add(newLauncher(config, GvLocation.TYPE));
        launchers.add(newLauncher(config, GvImage.TYPE));
        ReviewLauncherImpl reviewLauncher
                = new ReviewLauncherImpl(masterRepo, launchers, viewFactory);

        EditUiLauncher buildUi = new EditUiLauncher(config.getBespokeEditor(GvNode.TYPE.getDatumName()), builder, masterRepo);
        return new UiLauncherAndroid(buildUi, reviewLauncher, mDefaultActivity);
    }

    @NonNull
    private <T extends GvData> NodeLauncher<T> newLauncher(UiConfig config, GvDataType<T> dataType) {
        return new NodeLauncher<>(config.getBespokeViewer(dataType.getDatumName()), dataType);
    }
}
