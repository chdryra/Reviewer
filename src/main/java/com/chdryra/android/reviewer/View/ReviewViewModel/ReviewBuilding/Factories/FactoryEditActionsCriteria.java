package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation
        .MenuEditCriteria;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsCriteria extends FactoryEditActionsDefault<GvCriterionList.GvCriterion> {
    private static final GvDataType<GvCriterionList.GvCriterion> TYPE
            = GvCriterionList.GvCriterion.TYPE;

    public FactoryEditActionsCriteria(Context context, ConfigDataUi config,
                                      FactoryLaunchableUi launchableFactory,
                                      FactoryGvData dataFactory,
                                      GvDataPacker<GvCriterionList.GvCriterion> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected MenuAction<GvCriterionList.GvCriterion> newMenuEdit() {
        return new MenuEditCriteria();
    }
}
