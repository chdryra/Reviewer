/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.TextView;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutComment extends DialogLayoutBasic<GvComment> {
    private static final int LAYOUT = R.layout.dialog_text_view;
    private static final int COMMENT = R.id.medium_text_view;

    public ViewLayoutComment() {
        super(new LayoutHolder(LAYOUT, COMMENT));
    }

    @Override
    public void updateView(GvComment comment) {
        ((TextView) getView(COMMENT)).setText(comment.getFormattedComment());
    }
}
