/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutLocation extends DatumLayoutBasic<GvLocation> {
    private static final int LAYOUT = R.layout.dialog_location_view;
    private static final int NAME = R.id.name_text_view;
    private static final int ADDRESS = R.id.address_text_view;

    public ViewLayoutLocation() {
        super(new LayoutHolder(LAYOUT, NAME, ADDRESS));
    }

    @Override
    public void updateView(GvLocation locaton) {
        ((TextView) getView(NAME)).setText(locaton.getName());
        ((TextView) getView(ADDRESS)).setText(locaton.getAddress());
    }
}
