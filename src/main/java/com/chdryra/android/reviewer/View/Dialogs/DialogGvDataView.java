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
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataView<T extends GvData> extends DialogTwoButtonFragment implements
        LaunchableUi {
    public static final ActionType GOTO_REVIEW_ACTION = ActionType.OTHER;
    public static final ActionType DONE_ACTION        = ActionType.DONE;

    private GvDataType<T> mDataType;
    private DialogLayout<T>       mLayout;
    private GotoReviewListener<T> mListener;
    private T                     mDatum;

    /**
     * Provides a callback that can be called delete or done buttons are pressed.
     *
     * @param <T>:{@link GvData} type
     */
    public interface GotoReviewListener<T extends GvData> {
        void onGotoReview(T data);
    }

    protected DialogGvDataView(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    public static DialogGvDataView<GvData> getTextDialog(GvDataType dataType) {
        GvDataType<GvData> genericType = new GvDataType<>(GvData.class, dataType.getDatumName(),
                dataType.getDataName());
        return new DialogGvDataView<GvData>(genericType) {
        };
    }

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

        setLeftButtonAction(GOTO_REVIEW_ACTION);
        setLeftButtonText(getResources().getString(R.string.button_goto_review));
        setRightButtonAction(DONE_ACTION);
        dismissDialogOnLeftClick();
        dismissDialogOnRightClick();
        hideKeyboardOnLaunch();

        Bundle args = getArguments();
        GvDataPacker<T> unpacker = new GvDataPacker<>();
        mDatum = unpacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args);
        mLayout = FactoryGvDataViewLayout.newLayout(mDatum.getGvDataType());
        mLayout.onActivityAttached(getActivity(), args);

        //TODO make type safe
        mListener = (GotoReviewListener<T>) getTargetListener(GotoReviewListener.class);

        GvDataType type = mDatum.getGvDataType();
        if (type == GvImageList.GvImage.TYPE) {
            setDialogTitle(null);
        } else {
            setDialogTitle(type.getDatumName());
        }
    }

    @Override
    protected void onLeftButtonClick() {
        mListener.onGotoReview(mDatum);
        super.onLeftButtonClick();
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }
}

