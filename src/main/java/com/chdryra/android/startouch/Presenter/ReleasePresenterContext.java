/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter;

import android.content.Context;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Factories
        .FactoryDataAggregatorParams;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.startouch.ApplicationContexts.Implementation.PresenterContextBasic;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsApi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api
        .DataComparatorsApi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api
        .DataComparatorsPlugin;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation
        .ReviewBuilderInitialiser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataComparators;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;

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
        setFactoryLaunchCommands(new FactoryLaunchCommands());

        setConverter(new ConverterGv(getCommandsFactory()));

        DataComparatorsApi comparators = comparatorsPlugin.getComparatorsApi();
        GvDataComparators.initialise(comparators);

        FactoryFileIncrementor factoryFileIncrementor = setFactoryImageChooser(context,
                deviceContext);
        setFactoryReviewView(modelContext, viewContext, persistenceContext,
                aggregatorsPlugin, getGvConverter(), comparators, validator,
                factoryFileIncrementor);
    }

    private FactoryFileIncrementor setFactoryImageChooser(Context context, DeviceContext
            deviceContext) {
        String dir = deviceContext.getImageStorageDirectory();
        FactoryFileIncrementor incrementorFactory
                = new FactoryFileIncrementor(deviceContext.getImageStoragePath(),
                dir, dir.toLowerCase());
        setFactoryImageChooser(new FactoryImageChooser(context, incrementorFactory));

        return incrementorFactory;
    }

    private void setFactoryReviewView(ModelContext modelContext,
                                      ViewContext viewContext,
                                      PersistenceContext persistenceContext,
                                      DataAggregatorsPlugin aggregatorsPlugin,
                                      ConverterGv gvConverter,
                                      DataComparatorsApi comparators,
                                      DataValidator validator,
                                      FactoryFileIncrementor incrementorFactory) {
        FactoryGvData dataFactory = new FactoryGvData();

        FactoryReviewBuilderAdapter<?> builderFactory =
                getReviewBuilderAdapterFactory(modelContext, gvConverter, dataFactory, validator);

        FactoryReviewViewParams paramsFactory = new FactoryReviewViewParams();
        UiConfig uiConfig = viewContext.getUiConfig();
        FactoryReviewDataEditor dataEditorFactory
                = new FactoryReviewDataEditor(uiConfig, dataFactory, getCommandsFactory(),
                paramsFactory);


        AuthorsRepo authorRepo = persistenceContext.getAuthorsRepo();
        ReviewNodeRepo reviewsRepo = persistenceContext.getReviewsRepo();

        FactoryReviewViewActions actionsFactory
                = new FactoryReviewViewActions(uiConfig, reviewsRepo, authorRepo,
                getCommandsFactory(), comparators, gvConverter);
        FactoryReviewEditor<?> editorFactory
                = new FactoryReviewEditor<>(uiConfig, builderFactory, actionsFactory,
                paramsFactory, dataEditorFactory,
                incrementorFactory, getImageChooserFactory());
        FactoryReviewViewAdapter adapterFactory
                = newAdaptersFactory(modelContext,
                persistenceContext.getRepoFactory(),
                reviewsRepo,
                authorRepo,
                gvConverter,
                aggregatorsPlugin.getAggregatorsApi());

        FactoryReviewView factoryReviewView
                = new FactoryReviewView(editorFactory, adapterFactory, actionsFactory,
                paramsFactory);

        setFactoryReviewView(factoryReviewView);
    }

    private FactoryReviewViewAdapter newAdaptersFactory(ModelContext modelContext,
                                                        FactoryReviewsRepo reposFactory,
                                                        ReviewNodeRepo reviewNodeRepo,
                                                        AuthorsRepo authorsRepo,
                                                        ConverterGv gvConverter,
                                                        DataAggregatorsApi aggregator) {
        FactoryDataAggregatorParams paramsFactory = new FactoryDataAggregatorParams();
        DataAggregatorParams params = paramsFactory.getDefaultParams();
        GvDataAggregator aggregater = new GvDataAggregator(aggregator, params, gvConverter);
        return new FactoryReviewViewAdapter(modelContext.getReviewsFactory(),
                modelContext.getReferencesFactory(),
                reposFactory,
                modelContext.getBucketerFactory(),
                aggregater,
                authorsRepo, reviewNodeRepo, gvConverter);
    }

    private FactoryReviewBuilderAdapter<?> getReviewBuilderAdapterFactory(ModelContext modelContext,
                                                                          ConverterGv converter,
                                                                          FactoryGvData dataFactory,
                                                                          DataValidator validator) {
        FactoryReviewBuilder builder
                = new FactoryReviewBuilder(validator, modelContext.getReviewsFactory(),
                new FactoryDataBuilder(dataFactory), new ReviewBuilderInitialiser(converter));

        //TODO make type safe
        return new FactoryReviewBuilderAdapter<>(builder, new FactoryDataBuilderAdapter(), new FactoryDataBuildersGridUi(), validator);
    }
}
