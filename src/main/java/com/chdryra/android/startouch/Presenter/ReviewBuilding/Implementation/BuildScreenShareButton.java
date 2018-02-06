/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenShareButton<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewEditorActionBasic<GC>
        implements ButtonAction<GC> {
    private final LaunchableConfig mBuildScreenConfig;
    private final List<ClickListener> mListeners;

    private ButtonTitle mButtonTitle;
    private String mTitle;

    public BuildScreenShareButton(LaunchableConfig buildScreenConfig) {
        mBuildScreenConfig = buildScreenConfig;
        mListeners = new ArrayList<>();
    }

    protected void setTitle(String title) {
        mTitle = title;
        if(mButtonTitle != null) mButtonTitle.update(mTitle);
    }

    @Override
    public void setTitle(ButtonTitle title) {
        mButtonTitle = title;
        setTitle(mTitle);
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        ReviewEditor.ReadyToBuildResult result = getEditor().isReviewBuildable();
        if (!result.equals(ReviewEditor.ReadyToBuildResult.YES)) {
            showToast(result.getMessage());
            return;
        }

        notifyListeners();
        mBuildScreenConfig.launch();
    }

    @Override
    public String getButtonTitle() {
        return Strings.Buttons.SHARE;
    }

    @Override
    public void registerListener(ClickListener listener) {
        if(!mListeners.contains(listener)) mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ClickListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    protected void notifyListeners() {
        for(ClickListener listener : mListeners) {
            listener.onButtonClick();
        }
    }
}
