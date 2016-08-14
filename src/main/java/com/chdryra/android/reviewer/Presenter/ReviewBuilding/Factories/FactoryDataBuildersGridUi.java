/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Application.DataTypeCellOrder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenGridUiImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.DataBuilderGridCell;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataBuildersGridUi implements FactoryGridUi<DataBuilderGridCell>{
    @Override
    public BuildScreenGridUi<DataBuilderGridCell>
    newGridUiWrapper(FactoryVhDataCollection vhFactory) {
        BuildScreenGridUiImpl ui = new BuildScreenGridUiImpl(vhFactory);
        for(GvDataType<? extends GvDataParcelable> type : DataTypeCellOrder.ReviewOrder.ORDER) {
            ui.addGridCell(type);
        }

        return ui;
    }
}
