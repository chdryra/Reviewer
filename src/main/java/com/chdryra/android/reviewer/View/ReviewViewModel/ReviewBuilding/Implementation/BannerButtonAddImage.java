package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddImage extends BannerButtonAdd<GvImageList.GvImage>
        implements ImageChooser.ImageChooserListener {
    private static final GvDataType<GvImageList.GvImage> TYPE = GvImageList.GvImage.TYPE;
    private ImageChooser mImageChooser;

    //Constructors
    public BannerButtonAddImage(LaunchableConfig<GvImageList.GvImage> adderConfig,
                                String title,
                                FactoryGvData dataFactory,
                                GvDataPacker<GvImageList.GvImage> dataPacker,
                                FactoryLaunchableUi launchableFactory,
                                ImageChooser imageChooser) {
        super(adderConfig, title, TYPE, dataFactory, dataPacker, launchableFactory);
        mImageChooser = imageChooser;
    }

    private void setCover() {
        GvImageList images = (GvImageList) getGridData();
        GvImageList.GvImage cover = images.getItem(0);
        cover.setIsCover(true);
        getReviewView().notifyObservers();
    }

    //Overridden
    @Override
    public void onClick(View v) {
        getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                getLaunchableRequestCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean correctCode = requestCode == getLaunchableRequestCode();
        boolean isOk = ActivityResultCode.OK.equals(resultCode);
        boolean imageExists = mImageChooser.chosenImageExists(ActivityResultCode.get
                (resultCode), data);

        if (correctCode && isOk && imageExists) mImageChooser.getChosenImage(this);
    }

    @Override
    public void onChosenImage(GvImageList.GvImage image) {
        if (getGridData().size() == 0) image.setIsCover(true);
        if (addData(image) && getGridData().size() == 1) setCover();
    }
}
