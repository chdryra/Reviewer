/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActionBannerButtonAdd extends ReviewView.BannerButtonAction {
    private static final String TAG = "ActionBannerButtonAddListener";
    private ConfigGvDataUi.GvDataUiConfig mConfig;
    private GvDataHandler                 mHandler;

    public ActionBannerButtonAdd(ControllerReviewEditable controller, GvDataList.GvType dataType) {
        super(controller, dataType);
        ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(getDataType());
        mConfig = config.getAdderConfig();
    }

    @Override
    public void onSetReviewView() {
        mHandler = FactoryGvDataHandler.newHandler(getReviewView().getGridData());
    }

    protected Fragment getNewListener() {
        return new AddListener() {
            @Override
            public boolean onGvDataAdd(GvDataList.GvData data) {
                boolean added = mHandler.add(data, getActivity());
                getReviewView().updateUi();
                return added;
            }
        };
    }

    @Override
    public String getButtonTitle() {
        return getActivity().getResources().getString(R.string.add) + " " + getDataType()
                .getDatumString();
    }

    @Override
    public void onClick(View v) {
        if (getReviewView() == null) return;

        LauncherUi.launch(mConfig.getReviewDataUI(), getListener(TAG), mConfig.getRequestCode(),
                mConfig.getTag(), Administrator.get(getActivity()).pack(getController()));
    }

    //Dialogs expected to communicate directly with target fragments so using "invisible"
    // fragment as listener.
    //Restrictions on how fragments are constructed mean I have to use an abstract class...
    private abstract class AddListener extends Fragment implements DialogFragmentGvDataAdd
            .GvDataAddListener {
        @Override
        public boolean onGvDataAdd(GvDataList.GvData data) {
            return false;
        }
    }
}
