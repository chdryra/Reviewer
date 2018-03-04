/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentFormattedUi extends DataSectionUi<DataComment> {
    private TextView mHeadline;

    public CommentFormattedUi(LinearLayout section, TextView headline, @Nullable final Command
            onClick) {
        super(section, Strings.Formatted.COMMENT);

        setOnClickCommand(onClick);

        mHeadline = headline;
        if (onClick != null) {
            mHeadline.setClickable(true);
            mHeadline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.execute();
                }
            });
        }
    }

    @Override
    protected void updateView(IdableList<DataComment> data) {
        updateView(Typeface.NORMAL, DataFormatter.formatComments(data), DataFormatter
                .getHeadlineQuote(data));
    }

    @Override
    protected void setEmpty(String label) {
        updateView(Typeface.ITALIC, label, Strings.Formatted.DASHES);
    }

    private void updateView(int style, String comment, String headline) {
        TextView view = getValueView();
        view.setTypeface(Typeface.DEFAULT, style);
        view.setText(comment);
        mHeadline.setText(headline);
    }
}
