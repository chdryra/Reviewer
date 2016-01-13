package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories
        .FactoryDataAggregatorParams;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvConverter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsSource;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryVhBuildReviewData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregater;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePresenterContext extends PresenterContextBasic {
    public ReleasePresenterContext(Context context,
                                   ModelContext modelContext,
                                   ViewContext viewContext,
                                   DeviceContext deviceContext,
                                   DataAuthor author) {
        super(modelContext, viewContext);

        setLaunchablesFactory(viewContext);

        setFactoryGvData(new FactoryGvData());

        ConverterGv gvConverter = getConverterGv(modelContext.getTagsManager());
        setAdaptersFactory(modelContext, gvConverter);

        setReviewBuilderAdapterFactory(context, gvConverter,
                modelContext.getTagsManager(),
                modelContext.getReviewsFactory(),
                getGvDataFactory(),
                modelContext.getDataValidator(),
                deviceContext.getImageStoragePath(), deviceContext.getImageStorageDirectory(),
                author.getName());
    }

    private ConverterGv getConverterGv(TagsManager tagsManager) {
        FactoryGvConverter converterFactory = new FactoryGvConverter(tagsManager);
        return converterFactory.newGvConverter();
    }


    private void setAdaptersFactory(ModelContext modelContext, ConverterGv gvConverter) {
        setFactoryReviewViewAdapter(getReviewViewLaunchableFactory(), modelContext.getReviewsSource(),
                gvConverter, modelContext.getVisitorsFactory(), modelContext.getNodeTraversersFactory());
    }

    private void setLaunchablesFactory(ViewContext viewContext) {
        FactoryReviewViewLaunchable launchableFactory
                = new FactoryReviewViewLaunchable(viewContext.getUiConfig(),
                viewContext.getUiLauncher(), new FactoryReviewViewParams());
        setFactoryReviewViewLaunchable(launchableFactory);
    }

    private void setReviewBuilderAdapterFactory(Context context,
                                                ConverterGv converter,
                                                TagsManager tagsManager,
                                                FactoryReviews reviewsFactory,
                                                FactoryGvData dataFactory,
                                                DataValidator validator,
                                                File extDir, String dir,
                                                String authorName) {
        FactoryGridUi<? extends GvDataList> gridUi = new FactoryDataBuildersGridUi();
        FactoryVhBuildReviewData factoryVhBuildReviewData = new FactoryVhBuildReviewData();
        FactoryDataBuilderAdapter factoryDataBuilderAdapter = new FactoryDataBuilderAdapter
                (context);
        FactoryImageChooser factoryImageChooser = new FactoryImageChooser(context);
        FactoryDataBuilder dataBuilderFactory = new FactoryDataBuilder(dataFactory);
        FactoryReviewBuilder factoryReviewBuilder = new FactoryReviewBuilder(converter,
                tagsManager, reviewsFactory, dataBuilderFactory, validator);
        FactoryFileIncrementor incrementorFactory = new FactoryFileIncrementor(extDir, dir,
                authorName);
        FactoryReviewBuilderAdapter builderAdapterFactory
                = new FactoryReviewBuilderAdapter(factoryReviewBuilder,
                gridUi,
                factoryVhBuildReviewData,
                validator,
                factoryDataBuilderAdapter, incrementorFactory, factoryImageChooser);

        setFactoryBuilderAdapter(builderAdapterFactory);
    }

    private void setFactoryReviewViewAdapter(FactoryReviewViewLaunchable launchableFactory,
                                             ReviewsSource provider,
                                             ConverterGv converter,
                                             FactoryVisitorReviewNode visitorFactory,
                                             FactoryNodeTraverser traverserFactory) {
        FactoryDataAggregatorParams paramsFactory = new FactoryDataAggregatorParams();
        DataAggregatorParams params = paramsFactory.getDefaultParams();
        GvDataAggregater aggregater = new GvDataAggregater(new FactoryDataAggregator(params), converter);
        FactoryReviewViewAdapter factory = new FactoryReviewViewAdapter(launchableFactory,
                visitorFactory, traverserFactory, aggregater, provider, converter);
        setFactoryReviewViewAdapter(factory);
    }
}
