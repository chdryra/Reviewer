package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAddImage extends BannerButtonAdd<GvImage>
        implements ImageChooser.ImageChooserListener {
    private ImageChooser mImageChooser;

    //Constructors
    public BannerButtonAddImage(LaunchableConfig adderConfig,
                                LaunchableUiLauncher launchableFactory, String title,
                                GvDataList<GvImage> emptyImageList,
                                GvDataPacker<GvImage> dataPacker,
                                ImageChooser imageChooser) {
        super(adderConfig, launchableFactory, title, emptyImageList, dataPacker);
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
