/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.NonNull;
import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemComments extends GridItemConfigLauncher<GvComment.Reference> {
    public GridItemComments(LaunchableConfig commentsViewConfig,
                            FactoryReviewView launchableFactory,
                            ParcelablePacker<GvDataParcelable> packer) {
        super(commentsViewConfig, launchableFactory, packer);
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
