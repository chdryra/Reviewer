/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentNodeUi extends NodeDataSectionUi<DataComment, RefCommentList> {
    private TextView mHeadline;

    public CommentNodeUi(LinearLayout section, TextView headline, final ReviewNode node, @Nullable final Command onClick) {
        super(section, new ValueGetter<RefCommentList>() {
            @Override
            public RefCommentList getValue() {
                return node.getComments();
            }
        }, Strings.FORMATTED.COMMENT);

        setOnClickCommand(onClick);

        mHeadline = headline;
        if(onClick != null) {
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
        getValueView().setText(DataFormatter.formatComments(data));
        String headline = data.size() > 0 ?
                DataFormatter.getHeadlineQuote(data) : Strings.FORMATTED.DASHES;
        mHeadline.setText(headline);
    }

    @Override
    protected void setEmpty() {
        getValueView().setTypeface(getValueView().getTypeface(), Typeface.ITALIC);
        getValueView().setText(Strings.FORMATTED.NONE);
    }
}
