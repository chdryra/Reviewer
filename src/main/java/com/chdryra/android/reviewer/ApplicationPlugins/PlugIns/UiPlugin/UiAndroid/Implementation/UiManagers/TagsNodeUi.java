/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsNodeUi extends NodeDataTextUi<DataTag, RefDataList<DataTag>> {
    private static final int MAX_TAGS = 10;

    public TagsNodeUi(TextView tags, final ReviewNode node, @Nullable final Command onClick) {
        super(tags, new ValueGetter<RefDataList<DataTag>>() {
            @Override
            public RefDataList<DataTag> getValue() {
                return node.getTags();
            }
        }, new ValueFormatter<DataTag>() {
            @Override
            public String format(IdableList<DataTag> data) {
                return DataFormatter.formatTags(data, MAX_TAGS, null);
            }
        }, onClick, Strings.FORMATTED.NO_TAGS);
    }
}
