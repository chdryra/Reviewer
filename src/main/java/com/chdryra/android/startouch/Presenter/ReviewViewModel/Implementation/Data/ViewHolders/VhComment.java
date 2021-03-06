/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .GvCommentList.GvComment}. Shows comment
 * headline
 * <p>
 * A {@link VhText} with an appropriately defined {@link com.chdryra.android.corelibrary
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
                return comment != null ? comment.getFirstSentence() : "";
            }
        });

    }
}
