package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.BuildScreenGridUiImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.DataBuilderGridCell;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryVhDataCollection;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;

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
        ui.addGridCell(GvTag.TYPE);
        ui.addGridCell(GvCriterion.TYPE);
        ui.addGridCell(GvImage.TYPE);
        ui.addGridCell(GvComment.TYPE);
        ui.addGridCell(GvLocation.TYPE);
        ui.addGridCell(GvFact.TYPE);

        return ui;
    }
}
