package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddImage extends BannerButtonAdd<GvImage>
        implements ImageChooser.ImageChooserListener {
    private static final GvDataType<GvImage> TYPE = GvImage.TYPE;
    private ImageChooser mImageChooser;

    //Constructors
    public BannerButtonAddImage(LaunchableConfig<GvImage> adderConfig,
                                String title,
                                FactoryGvData dataFactory,
                                GvDataPacker<GvImage> dataPacker,
                                FactoryLaunchableUi launchableFactory,
                                ImageChooser imageChooser) {
        super(adderConfig, title, TYPE, dataFactory, dataPacker, launchableFactory);
        mImageChooser = imageChooser;
    }

    private void setCover() {
        GvImageList images = (GvImageList) getGridData();
        GvImage cover = images.getItem(0);
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
    public void onChosenImage(GvImage image) {
        if (getGridData().size() == 0) image.setIsCover(true);
        if (addData(image) && getGridData().size() == 1) setCover();
    }
}
