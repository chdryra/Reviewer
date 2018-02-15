/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterComments extends GvConverterReviewData.RefCommentList<GvComment, GvCommentList, GvComment.Reference> {

    public GvConverterComments() {
        super(GvCommentList.class, GvComment.Reference.TYPE);
    }

    @Override
    public GvComment convert(DataComment datum, @Nullable ReviewId reviewId) {
        return new GvComment(newId(reviewId), datum.getComment(), datum.isHeadline());
    }

    @Override
    protected GvComment.Reference convertReference(CommentRef reference) {
        return new GvComment.Reference(reference, this);
    }
}
