/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
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
public class TagsFormattedUi extends ViewUi<TextView, IdableList<DataTag>> {
    private static final int MAX_TAGS = 10;

    public TagsFormattedUi(TextView tags, @Nullable Command onClick) {
        super(tags, onClick);
    }

    @Override
    public void update(IdableList<DataTag> value) {
        if(value.size() > 0) {
            getView().setText(DataFormatter.formatTags(value, MAX_TAGS, null));
        } else {
            getView().setTypeface(getView().getTypeface(), Typeface.ITALIC);
            getView().setText(Strings.Formatted.NO_TAGS);
        }
    }

    @Override
    public void onInvalidated() {
        update(new IdableDataList<DataTag>());
    }
}
