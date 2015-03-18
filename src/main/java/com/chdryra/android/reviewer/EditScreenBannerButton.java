/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenBannerButton extends ReviewViewAction.BannerButtonAction {
    private static final String TAG = "ActionBannerButtonAddListener";

    private ConfigGvDataUi.LaunchableConfig mConfig;
    private Fragment                        mListener;

    public EditScreenBannerButton(ConfigGvDataUi.LaunchableConfig config, String title) {
        super(title);
        mConfig = config;
        setListener(new AddListener(config.getGVType()) {
        });
    }

    @Override
    public void onClick(View v) {
        if (getReviewView() == null) return;

        LauncherUi.launch(mConfig.getLaunchable(), getListener(), getRequestCode(),
                mConfig.getTag(), new Bundle());
    }

    protected Fragment getListener() {
        return mListener;
    }

    protected void setListener(Fragment listener) {
        mListener = listener;
        super.registerActionListener(listener, TAG);
    }

    //TODO make type safe
    protected boolean addData(GvDataList.GvData data) {
        boolean added = getDataBuilder().add(data);
        getReviewView().updateUi();
        return added;
    }

    protected int getRequestCode() {
        return mConfig.getRequestCode();
    }

    private ReviewBuilder.DataBuilder getDataBuilder() {
        return ((ReviewBuilder.DataBuilder) getAdapter());
    }

    //Dialogs expected to communicate directly with target fragments so using "invisible"
    // fragment as listener.
    //Restrictions on how fragments are constructed mean I have to use an abstract class...
    protected abstract class AddListener extends Fragment implements DialogAddGvData
            .GvDataAddListener {
        private GvDataList<GvDataList.GvData> mAdded;

        private AddListener(GvDataList.GvDataType dataType) {
            mAdded = FactoryGvData.newList(dataType);
        }

        //TODO make type safe
        @Override
        public boolean onGvDataAdd(GvDataList.GvData data) {
            boolean success = addData(data);
            if (success) mAdded.add(data);
            return success;
        }

        @Override
        public void onGvDataCancel() {
            for (GvDataList.GvData added : mAdded) {
                getDataBuilder().delete(added);
            }
        }

        @Override
        public void onGvDataDone() {
            mAdded = FactoryGvData.newList(mAdded.getGvDataType());
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == getRequestCode() && data != null
                    && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                onGvDataAdd(GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data));
            }
        }
    }
}
