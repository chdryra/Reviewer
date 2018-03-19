/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import android.os.Bundle;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataReference;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GridItemBespokeLauncher<R extends GvDataRef<R, V, ?>, V extends HasReviewId, T
        extends GvDataParcelable> extends GridItemConfigLauncher<R> {
    private final LaunchableConfig mBespokeViewer;
    private final DataConverter<V, T, ?> mConverter;

    public GridItemBespokeLauncher(UiLauncher launcher,
                                   FactoryReviewView launchableFactory,
                                   LaunchableConfig dataConfig,
                                   LaunchableConfig bespokeViewer,
                                   DataConverter<V, T, ?> converter) {
        super(launcher, launchableFactory, dataConfig);
        mBespokeViewer = bespokeViewer;
        mConverter = converter;
    }

    @Override
    protected void launchViewerIfPossible(R item, int position) {
        ParcelablePacker<GvDataParcelable> packer = new ParcelablePacker<>();
        Bundle args = new Bundle();
        VhDataReference<V> vh = item.getReferenceViewHolder();
        if (vh != null && vh.getDataValue() != null) {
            packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, mConverter.convert(vh
                    .getDataValue()), args);
            mBespokeViewer.launch(new UiLauncherArgs(mBespokeViewer.getDefaultRequestCode())
                    .setBundle(args));
        }
    }
}
