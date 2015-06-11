/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .GvCommentList.GvComment}. Shows comment
 * headline
 * <p>
 * A {@link VhText} with an appropriately defined {@link com.chdryra.android.mygenerallibrary
 * .VHDataStringGetter}.
 * </p>
 */
public class VhComment extends VhText {

    public VhComment() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GvCommentList.GvComment comment = (GvCommentList.GvComment) data;
                return comment != null ? comment.getHeadline() : null;
            }
        });

    }
}
