/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentUi extends ViewUi<TextView, RefCommentList> {
    public CommentUi(TextView view, final RefCommentList reference, @Nullable final Command onClick) {
        super(view, new ValueGetter<RefCommentList>() {
            @Override
            public RefCommentList getValue() {
                return reference;
            }
        });
        if(onClick != null) {
            getView().setClickable(true);
            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.execute();
                }
            });
        }
    }

    public String getText() {
        return getView().getText().toString().trim();
    }

    @Override
    public void update() {
        getValue().dereference(new DataReference.DereferenceCallback<IdableList<DataComment>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<DataComment>> value) {
                if(value.hasValue()) {
                    getView().setText(DataFormatter.formatComments(value.getData()));
                }
            }
        });
    }
}
