/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataView<T extends GvData> extends DialogTwoButtonFragment implements
        LaunchableUi {
    public static final ActionType DONE_ACTION = ActionType.DONE;

    private GvDataType<T> mDataType;
    private DialogLayout<T> mLayout;
    private T mDatum;

    protected DialogGvDataView(GvDataType<T> dataType) {
        mDataType = dataType;
    }

//protected methods
    @Override
    protected Intent getReturnData() {
        return null;
    }

    //Overridden
    @Override
    public String getLaunchTag() {
        return "View" + mDataType.getDatumName();
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUi() {
        return mLayout.createLayoutUi(getActivity(), mDatum);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRightButtonAction(DONE_ACTION);
        dismissDialogOnLeftClick();
        dismissDialogOnRightClick();
        hideKeyboardOnLaunch();

        Bundle args = getArguments();
        GvDataPacker<T> unpacker = new GvDataPacker<>();
        mDatum = unpacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args);
        mLayout = FactoryGvDataViewLayout.newLayout(mDatum.getGvDataType());
        mLayout.onActivityAttached(getActivity(), args);

        GvDataType dataType = mDatum.getGvDataType();
        if (dataType.equals(GvImage.TYPE)) {
            setDialogTitle(null);
        } else {
            setDialogTitle(dataType.getDatumName());
        }
    }
}

