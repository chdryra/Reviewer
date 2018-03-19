/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataReferenceWrapper;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
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
public class BuildScreenShare<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewEditorActionBasic<GC>
        implements ButtonAction<GC> {
    private final DataReference<String> mTitle;
    private final LaunchableConfig mBuildScreenConfig;
    private final List<ClickListener> mListeners;

    public BuildScreenShare(LaunchableConfig buildScreenConfig) {
        mTitle = new DataReferenceWrapper<>(Strings.Buttons.SHARE);
        mBuildScreenConfig = buildScreenConfig;
        mListeners = new ArrayList<>();
    }

    @Override
    public DataReference<String> getTitle() {
        return mTitle;
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
    public void registerListener(ClickListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ClickListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    private void notifyListeners() {
        for (ClickListener listener : mListeners) {
            listener.onButtonClick();
        }
    }
}
