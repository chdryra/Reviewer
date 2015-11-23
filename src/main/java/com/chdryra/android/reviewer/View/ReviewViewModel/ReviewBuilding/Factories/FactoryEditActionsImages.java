package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.BannerButtonAddImage;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GridItemDataEditImage;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsImages extends FactoryEditActionsDefault<GvImage> {
    private static final GvDataType<GvImage> TYPE = GvImage.TYPE;
    private ImageChooser mImageChooser;

    public FactoryEditActionsImages(Context context, ConfigDataUi config, FactoryLaunchableUi launchableFactory,
                                    FactoryGvData dataFactory,
                                    GvDataPacker<GvImage> packer,
                                    ImageChooser imageChooser) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
        mImageChooser = imageChooser;
    }

    @Override
    protected BannerButtonAction<GvImage> newBannerButtonAdd() {
        return new BannerButtonAddImage(getAdderConfig(), getBannerButtonTitle(), getDataFactory(),
                getPacker(), getLaunchableFactory(), mImageChooser);
    }

    @Override
    protected GridItemAction<GvImage> newGridItemEdit() {
        return new GridItemDataEditImage(getEditorConfig(), getLaunchableFactory(), getPacker());
    }

}
