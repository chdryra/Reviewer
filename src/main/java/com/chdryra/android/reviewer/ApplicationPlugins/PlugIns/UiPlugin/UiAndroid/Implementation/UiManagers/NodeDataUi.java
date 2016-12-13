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
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataUi<T extends HasReviewId, R extends ReviewListReference<T, ?>> extends ViewUi<TextView, R> {
    private final ValueFormatter<T> mFormatter;

    public interface ValueFormatter<T extends HasReviewId> {
        String format(IdableList<T> data);
    }

    public NodeDataUi(TextView view,
                      ValueGetter<R> getter,
                      ValueFormatter<T> formatter,
                      @Nullable final Command onClick) {
        super(view, getter);
        mFormatter = formatter;
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

    @Override
    public void update() {
        getValue().dereference(new DataReference.DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<T>> value) {
                if(value.hasValue()) setView(value.getData());
            }
        });
    }

    protected void setView(IdableList<T> data) {
        TextView view = getView();
        if(data.size() > 0) {
            view.setText(mFormatter.format(data));
        } else {
            view.setTypeface(view.getTypeface(), Typeface.ITALIC);
            view.setText(Strings.FORMATTED.NONE);
        }
    }
}
