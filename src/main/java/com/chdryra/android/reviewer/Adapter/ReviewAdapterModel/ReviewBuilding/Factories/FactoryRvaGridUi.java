package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.BuildScreenGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.WrapperGridData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.RvaGridCell;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryRvaGridUi implements FactoryGridUi<RvaGridCell, ReviewBuilderAdapter>{
    @Override
    public WrapperGridData<RvaGridCell, ReviewBuilderAdapter> newGridUiWrapper(FactoryVhDataCollection vhFactory) {
        BuildScreenGridUi ui = new BuildScreenGridUi(vhFactory);
        ui.wrapGridCell(GvTagList.GvTag.TYPE);
        ui.wrapGridCell(GvCriterionList.GvCriterion.TYPE);
        ui.wrapGridCell(GvImageList.GvImage.TYPE);
        ui.wrapGridCell(GvCommentList.GvComment.TYPE);
        ui.wrapGridCell(GvLocationList.GvLocation.TYPE);
        ui.wrapGridCell(GvFactList.GvFact.TYPE);

        return ui;
    }
}
