/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataReferenceWrapper;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ButtonAdd;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.DoneEditingButton;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.GridItemEdit;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiDeleteAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiPreviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MaiUpDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.MenuEditDataDefault;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.RatingEdit;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.SubjectEdit;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class FactoryActionsEditData<T extends GvDataParcelable> extends FactoryActionsNone<T> {
    private final UiConfig mUiConfig;
    private final FactoryGvData mDataFactory;
    private final FactoryLaunchCommands mCommandsFactory;
    private final ParcelablePacker<T> mPacker;

    public FactoryActionsEditData(GvDataType<T> dataType,
                                  UiConfig uiConfig,
                                  FactoryGvData dataFactory,
                                  FactoryLaunchCommands commandsFactory) {
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
    public ButtonAction<T> newBannerButton() {
        return new ButtonAdd<>(getAdderConfig(), getBannerButtonTitle(),
                mDataFactory.newDataList(getDataType()), mPacker);
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemEdit<>(getEditorConfig(), mPacker);
    }

    @Override
    public MenuAction<T> newMenu() {
        return new MenuEditDataDefault<>(geDataName(), newUpAction(), newDeleteAction(),
                newPreviewAction());
    }

    @Nullable
    @Override
    public ButtonAction<T> newContextButton() {
        return new DoneEditingButton<>(true);
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

    DataReference<String> getBannerButtonTitle() {
        String title = Strings.Buttons.ADD;
        title += " " + geDataName();
        return new DataReferenceWrapper<>(title);
    }

    FactoryLaunchCommands getCommandsFactory() {
        return mCommandsFactory;
    }

    @NonNull
    MenuActionItem<T> newUpAction() {
        return new MaiUpDataEditor<>();
    }

    @NonNull
    MenuActionItem<T> newPreviewAction() {
        return new MaiPreviewDataEditor<>(mCommandsFactory.newLaunchPagedCommand(null));
    }

    @NonNull
    MenuActionItem<T> newDeleteAction() {
        return new MaiDeleteAction<>(geDataName());
    }

    private String geDataName() {
        return getDataType().getDataName();
    }
}
