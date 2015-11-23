/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.TextView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewComment extends DialogLayout<GvComment> {
    public static final int LAYOUT = R.layout.dialog_text_view;
    public static final int COMMENT = R.id.medium_text_view;
    public static final int[] VIEWS = new int[]{COMMENT};

    //Constructors
    public ViewComment() {
        super(LAYOUT, VIEWS);
    }

    //Overridden
    @Override
    public void updateLayout(GvComment comment) {
        ((TextView) getView(COMMENT)).setText(comment.getComment());
    }
}
