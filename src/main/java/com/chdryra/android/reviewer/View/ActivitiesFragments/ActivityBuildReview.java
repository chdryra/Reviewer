package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.content.Intent;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.FactoryConfiguredGridUi;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.FactoryDataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.FactoryDataCollectionGridCell;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.BuildScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Utils.FactoryImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView implements LaunchableUi {
    private static final String TAG = "BuildScreen";
    private BuildScreen mBuildScreen;

    @Override
    protected ReviewView createReviewView() {
        Administrator admin = Administrator.getInstance(this);

        if(admin.getReviewBuilderAdapter() == null) {
            ApplicationContext appContext = admin.getApplicationContext();
            admin.setReviewBuilderAdapter(newAdapter(appContext));
        }

        mBuildScreen = new BuildScreen(this, admin.getReviewBuilderAdapter());

        return mBuildScreen.getEditor();
    }

    private ReviewBuilderAdapter newAdapter(ApplicationContext appContext) {
        DataValidator validator = appContext.getDataValidator();

        ConverterGv converter = appContext.getDataConverters().getGvConverter();
        FactoryDataBuilder builderFactory = new FactoryDataBuilder(converter);
        ReviewBuilder builder = new ReviewBuilder(converter,
                appContext.getTagsManager(),
                appContext.getReviewFactory(),
                builderFactory,
                validator);

        FactoryDataCollectionGridCell gridCellFactory = new FactoryDataCollectionGridCell();
        FactoryConfiguredGridUi gridUiFactory  = new FactoryConfiguredGridUi(gridCellFactory);
        FactoryReviewBuilderAdapter factory = new FactoryReviewBuilderAdapter(gridUiFactory);

        return factory.newAdapter(this,
                builder,
                validator,
                appContext.getFileIncrementorFactory(),
                new FactoryImageChooser());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBuildScreen.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }
}
