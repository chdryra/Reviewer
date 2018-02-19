/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.support.annotation.NonNull;
import android.view.View;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemComments extends GridItemConfigLauncher<GvComment.Reference> {
    public GridItemComments(UiLauncher launcher,
                            LaunchableConfig commentsViewConfig,
                            FactoryReviewView launchableFactory) {
        super(launcher, launchableFactory, commentsViewConfig);
    }

    @Override
    public void onClickNotExpandable(GvComment.Reference item, final int position, final View v) {
        final GvComment.Reference comment = item.getFullCommentReference();
        comment.getReference().dereference(new DataReference.DereferenceCallback<DataComment>() {
            @Override
            public void onDereferenced(DataValue<DataComment> value) {
                if(value.hasValue()) {
                    comment.setParcelable(newParcelable(value.getData()));
                    GridItemComments.super.onClickNotExpandable(comment, position, v);
                }
            }
        });
    }

    @NonNull
    private GvComment newParcelable(DataComment data) {
        return new GvComment(new GvReviewId(data.getReviewId()), data.getComment(), data.isHeadline());
    }
}
