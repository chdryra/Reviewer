/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.TextView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewText extends DialogLayout<GvData> {
    public static final int   LAYOUT = R.layout.dialog_tag_view;
    public static final int   TAG    = R.id.tag_text_view;
    public static final int[] VIEWS  = new int[]{TAG};

    public ViewText() {
        super(LAYOUT, VIEWS);
    }

    @Override
    public void updateLayout(GvData datum) {
        ((TextView) getView(TAG)).setText(datum.getStringSummary());
    }
}
