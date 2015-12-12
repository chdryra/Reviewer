package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuDataEditCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ConfigDataUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsCriteria extends FactoryEditActionsDefault<GvCriterion> {
    private static final GvDataType<GvCriterion> TYPE
            = GvCriterion.TYPE;

    public FactoryEditActionsCriteria(Context context, ConfigDataUi config,
                                      LaunchableUiLauncher launchableFactory,
                                      FactoryGvData dataFactory,
                                      GvDataPacker<GvCriterion> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected MenuAction<GvCriterion> newMenuEdit() {
        return new MenuDataEditCriteria();
    }
}
