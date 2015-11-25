/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;

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

    //Constructors
    public VhComment() {
        super(new VHDataStringGetter() {
            //Overridden
            @Override
            public String getString(ViewHolderData data) {
                GvComment comment = (GvComment) data;
                return comment != null ? comment.getHeadline() : null;
            }
        });

    }
}
