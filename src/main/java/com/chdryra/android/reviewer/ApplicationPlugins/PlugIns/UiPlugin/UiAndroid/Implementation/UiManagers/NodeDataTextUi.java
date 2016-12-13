/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Typeface;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataTextUi<T extends HasReviewId, R extends ReviewListReference<T, ?>>
        extends NodeDataUi<T, R, TextView> {
    private static final String NONE = Strings.FORMATTED.NONE;
    private final ValueFormatter<T> mFormatter;
    private final String mNoneText;

    public interface ValueFormatter<T extends HasReviewId> {
        String format(IdableList<T> data);
    }

    public NodeDataTextUi(TextView view,
                          ValueGetter<R> getter,
                          ValueFormatter<T> formatter) {
        this(view, getter, formatter, NONE);
    }

    public NodeDataTextUi(TextView view,
                          ValueGetter<R> getter,
                          ValueFormatter<T> formatter,
                          String noneText) {
        super(view, getter);
        mFormatter = formatter;
        mNoneText = noneText;
    }

    @Override
    protected void updateView(IdableList<T> data) {
        getView().setText(mFormatter.format(data));
    }

    @Override
    protected void setEmpty() {
        getView().setTypeface(getView().getTypeface(), Typeface.ITALIC);
        getView().setText(mNoneText);
    }
}
