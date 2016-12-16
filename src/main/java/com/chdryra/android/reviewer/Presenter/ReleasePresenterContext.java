/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter;

import android.content.Context;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Factories.FactoryDataAggregatorParams;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.PresenterContextBasic;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api
        .DataComparatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataComparators;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePresenterContext extends PresenterContextBasic {
    public ReleasePresenterContext(Context context,
                                   ModelContext modelContext,
                                   ViewContext viewContext,
                                   PersistenceContext persistenceContext,
                                   DeviceContext deviceContext,
                                   DataComparatorsPlugin comparatorsPlugin,
                                   DataAggregatorsPlugin aggregatorsPlugin,
                                   DataValidator validator) {
        setConverter(new ConverterGv());

        setFactoryCommands(new FactoryCommands());


        DataComparatorsApi comparators = comparatorsPlugin.getComparatorsApi();
        GvDataComparators.initialise(comparators);

        setFactoryReviewView(context,
                modelContext, deviceContext, viewContext, persistenceContext,
                aggregatorsPlugin, getGvConverter(), comparators, validator);
    }

    private void setFactoryReviewView(Context context,
                                      ModelContext modelContext,
                                      DeviceContext deviceContext,
                                      ViewContext viewContext,
                                      PersistenceContext persistenceContext,
                                      DataAggregatorsPlugin aggregatorsPlugin,
                                      ConverterGv gvConverter,
                                      DataComparatorsApi comparators,
                                      DataValidator validator) {
        FactoryGvData dataFactory = new FactoryGvData();

        FactoryReviewBuilderAdapter<?> builderFactory =
                getReviewBuilderAdapterFactory(modelContext, gvConverter,dataFactory, validator);

        FactoryReviewViewParams paramsFactory = new FactoryReviewViewParams();
        UiConfig uiConfig = viewContext.getUiConfig();
        FactoryReviewDataEditor dataEditorFactory
                = new FactoryReviewDataEditor(uiConfig, dataFactory, getCommandsFactory(), paramsFactory);

        String dir = deviceContext.getImageStorageDirectory();
        FactoryFileIncrementor incrementorFactory
                = new FactoryFileIncrementor(deviceContext.getImageStoragePath(),
                dir, dir.toLowerCase());

        FactoryReviewEditor<?> editorFactory
                = new FactoryReviewEditor<>(builderFactory, paramsFactory, dataEditorFactory,
                incrementorFactory, new FactoryImageChooser(context), getCommandsFactory());

        AuthorsRepository authorRepo = persistenceContext.getAuthorsRepository();
        FactoryReviewViewAdapter factoryReviewViewAdapter = newAdaptersFactory(modelContext,
                persistenceContext.getReviewsRepository(),
                authorRepo,
                gvConverter,
                aggregatorsPlugin.getAggregatorsApi());
        FactoryReviewView factoryReviewView = new FactoryReviewView(uiConfig,
                factoryReviewViewAdapter, editorFactory, paramsFactory,
                modelContext.getBucketerFactory(), getCommandsFactory(),
                authorRepo, comparators, gvConverter.newConverterLocations());

        setFactoryReviewView(factoryReviewView);
    }

    private FactoryReviewViewAdapter newAdaptersFactory(ModelContext modelContext,
                                    ReviewsSource reviewsSource,
                                    AuthorsRepository authorsRepository,
                                    ConverterGv gvConverter,
                                    DataAggregatorsApi aggregator) {
        FactoryDataAggregatorParams paramsFactory = new FactoryDataAggregatorParams();
        DataAggregatorParams params = paramsFactory.getDefaultParams();
        GvDataAggregator aggregater = new GvDataAggregator(aggregator, params, gvConverter);
        return new FactoryReviewViewAdapter(modelContext.getReviewsFactory(),
                modelContext.getReferenceFactory(),
                aggregater,
                reviewsSource,
                authorsRepository,
                gvConverter);
    }

    private FactoryReviewBuilderAdapter<?> getReviewBuilderAdapterFactory(ModelContext modelContext,
                                                ConverterGv converter,
                                                FactoryGvData dataFactory,
                                                DataValidator validator) {
        FactoryReviewBuilder builder
                = new FactoryReviewBuilder(converter, validator, modelContext.getReviewsFactory(),
                new FactoryDataBuilder(dataFactory));

        //TODO make type safe
        return new FactoryReviewBuilderAdapter<>(builder, new FactoryDataBuilderAdapter(), new FactoryDataBuildersGridUi(), validator);
    }
}
