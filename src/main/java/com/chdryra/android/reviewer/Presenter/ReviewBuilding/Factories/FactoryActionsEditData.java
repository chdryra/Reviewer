/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BannerButtonAdd;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiDeleteAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiDoneAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiPreviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MaiUpDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuEditDataDefault;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEdit;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class FactoryActionsEditData<T extends GvDataParcelable> extends FactoryActionsNone<T> {
    private final UiConfig mUiConfig;
    private final FactoryGvData mDataFactory;
    private final FactoryCommands mCommandsFactory;
    private final ParcelablePacker<T> mPacker;

    public FactoryActionsEditData(GvDataType<T> dataType,
                                  UiConfig uiConfig,
                                  FactoryGvData dataFactory,
                                  FactoryCommands commandsFactory) {
        super(dataType);
        mUiConfig = uiConfig;
        mDataFactory = dataFactory;
        mCommandsFactory = commandsFactory;
        mPacker = new ParcelablePacker<>();
    }

    @Override
    public SubjectAction<T> newSubject() {
        return new SubjectEdit<>();
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        return new RatingEdit<>();
    }

    @Override
    public BannerButtonAction<T> newBannerButton() {
        return new BannerButtonAdd<>(getAdderConfig(), getBannerButtonTitle(),
                mDataFactory.newDataList(getDataType()), mPacker);
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemEdit<>(getEditorConfig(), mPacker);
    }

    @Override
    public MenuAction<T> newMenu() {
        return new MenuEditDataDefault<>(geDataName(), newUpAction(),
                newDoneAction(), newDeleteAction(), newPreviewAction());
    }

    LaunchableConfig getAdderConfig() {
        return mUiConfig.getAdder(getDataType().getDatumName());
    }

    LaunchableConfig getEditorConfig() {
        return mUiConfig.getEditor(getDataType().getDatumName());
    }

    UiConfig getUiConfig() {
        return mUiConfig;
    }

    FactoryGvData getDataFactory() {
        return mDataFactory;
    }

    ParcelablePacker<T> getPacker() {
        return mPacker;
    }

    String getBannerButtonTitle() {
        String title = Strings.Buttons.ADD;
        title += " " + geDataName();
        return title;
    }

    @NonNull
    MenuActionItem<T> newUpAction() {
        return new MaiUpDataEditor<>();
    }

    @NonNull
    MenuActionItem<T> newPreviewAction() {
        return new MaiPreviewDataEditor<>(mCommandsFactory.newLaunchFormattedCommand(null));
    }

    @NonNull
    MenuActionItem<T> newDoneAction() {
        return new MaiDoneAction<>();
    }

    @NonNull
    MenuActionItem<T> newDeleteAction() {
        return new MaiDeleteAction<>(geDataName());
    }

    private String geDataName() {
        return getDataType().getDataName();
    }

    FactoryCommands getCommandsFactory() {
        return mCommandsFactory;
    }
}
