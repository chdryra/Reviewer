package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenGridUiImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.DataBuilderGridCell;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

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
