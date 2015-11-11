package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

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
public class FactoryConfiguredGridUi {
    private FactoryDataCollectionGridCell mFactoryDataCollectionGridCell;

    public FactoryConfiguredGridUi(FactoryDataCollectionGridCell factoryDataCollectionGridCell) {
        mFactoryDataCollectionGridCell = factoryDataCollectionGridCell;
    }

    public AdapterGridUi<ReviewBuilderAdapter> newReviewBuilderAdapterGridUi() {
        AdapterGridUi<ReviewBuilderAdapter> ui =
                new BuildScreenGridUi(mFactoryDataCollectionGridCell);
        ui.addGridCell(GvTagList.GvTag.TYPE);
        ui.addGridCell(GvCriterionList.GvCriterion.TYPE);
        ui.addGridCell(GvImageList.GvImage.TYPE);
        ui.addGridCell(GvCommentList.GvComment.TYPE);
        ui.addGridCell(GvLocationList.GvLocation.TYPE);
        ui.addGridCell(GvFactList.GvFact.TYPE);

        return ui;
    }
}
