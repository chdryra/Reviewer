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
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewTag extends DialogLayout<GvTagList.GvTag> {
    public static final int   LAYOUT = R.layout.dialog_tag_view;
    public static final int   TAG    = R.id.tag_text_view;
    public static final int[] VIEWS  = new int[]{TAG};

    public ViewTag() {
        super(LAYOUT, VIEWS);
    }

    @Override
    public void updateLayout(GvTagList.GvTag tag) {
        ((TextView) getView(TAG)).setText(tag.get());
    }
}
