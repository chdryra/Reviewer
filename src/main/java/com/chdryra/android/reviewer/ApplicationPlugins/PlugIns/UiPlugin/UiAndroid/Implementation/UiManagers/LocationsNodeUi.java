/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
import com.chdryra.android.reviewer.R;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationsNodeUi extends NodeDataLayoutUi<DataLocation> {
    private static final int LAYOUT = R.layout.formatted_locations;
    private static final int NAME = R.id.short_name;
    private static final int ADDRESS = R.id.address;

    public LocationsNodeUi(LinearLayout view,
                           int placeholder,
                           LayoutInflater inflater,
                           final ReviewNode node) {
        super(view, new ValueGetter<RefDataList<DataLocation>>() {
            @Override
            public RefDataList<DataLocation> getValue() {
                return node.getLocations();
            }
        }, LAYOUT, placeholder, inflater);
    }

    @Override
    protected void updateView(View view, DataLocation location) {
        TextView name = (TextView) view.findViewById(NAME);
        TextView address = (TextView) view.findViewById(ADDRESS);
        name.setText(location.toString());
        address.setText(DataFormatter.getAddress(location.getName()));
    }
}
