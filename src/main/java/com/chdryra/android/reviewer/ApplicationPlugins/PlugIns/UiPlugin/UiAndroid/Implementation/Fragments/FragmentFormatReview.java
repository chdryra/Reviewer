/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ButtonStampUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ButtonUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CommentUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverRefUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HideableViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalAdapterRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalGridUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuReviewFormatted;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuReviewPreview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarLaunchSummary;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TextUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.VhFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhCriterionSmall;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhFactSmall;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhLocationSmall;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhTagSmall;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentFormatReview extends Fragment {
    private static final String ID = TagKeyGenerator.getKey(FragmentFormatReview.class, "ReviewId");
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentFormatReview.class,
            "published");

    private static final int LAYOUT = R.layout.fragment_review_formatted;
    private static final int IMAGE = R.id.image_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int STAMP = R.id.stamp_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int TAGS_VIEW = R.id.tags_formatted_view;
    private static final int LOCATIONS_VIEW = R.id.locations_formatted_view;
    private static final int LOCATIONS = R.id.locations_formatted;
    private static final int CRITERIA_VIEW = R.id.criteria_formatted_view;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int COMMENT_VIEW = R.id.comment_formatted_view;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int IMAGES_VIEW = R.id.images_formatted_view;
    private static final int IMAGES = R.id.images_formatted;
    private static final int FACTS_VIEW = R.id.facts_formatted_view;
    private static final int FACTS = R.id.facts_formatted;

    private static final ReviewViewParams.CellDimension HALF = ReviewViewParams.CellDimension.HALF;
    private static final ReviewViewParams.CellDimension EIGHTH = ReviewViewParams.CellDimension
            .EIGHTH;
    private static final ReviewViewParams.CellDimension QUARTER = ReviewViewParams.CellDimension
            .QUARTER;

    private boolean mIsPublished = true;
    private ReviewNode mNode;
    private NamedAuthor mAuthor;
    private UiSuite mUi;
    private RepositorySuite mRepo;
    private ButtonUi mStamp;
    private MenuUi mMenu;

    public static FragmentFormatReview newInstance(ReviewId nodeId, boolean isPublished) {
        //Can't use FactoryFragment as Support fragment rather than normal fragment
        Bundle args = new Bundle();
        args.putString(ID, nodeId.toString());
        args.putBoolean(PUBLISHED, isPublished);
        FragmentFormatReview fragment = new FragmentFormatReview();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        mUi = app.getUi();
        mRepo = app.getRepository();

        Bundle args = getArguments();
        if (args == null) throwNoReview();
        mIsPublished = args.getBoolean(PUBLISHED);

        setNode(args);
        setMenu();

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims28 = calculator.calcDimensions(HALF, EIGHTH);
        CellDimensionsCalculator.Dimensions dims24 = calculator.calcDimensions(HALF, QUARTER);
        CellDimensionsCalculator.Dimensions dims44 = calculator.calcDimensions(QUARTER, QUARTER);

        ReviewLauncher launcher = mUi.getLauncher().getReviewLauncher();

        View v = inflater.inflate(LAYOUT, container, false);

        setCover(v);
        int textColour = setSubject(v);
        setRating(v, launcher);
        setBannerButton(v, launcher, textColour);
        setComment(v);
        setTags(v, dims28);
        setLocations(v, dims28);
        setCriteria(v, dims24);
        setImages(v, dims44);
        setFacts(v, dims24);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private FactoryCommands getCommandsFactory() {
        return mUi.getCommandsFactory();
    }

    private ConverterGv getConverter() {
        return mUi.getGvConverter();
    }

    private void setNode(Bundle args) {
        String reviewId = args.getString(ID);
        if (reviewId == null) throwNoReview();
        try {
            ActivityFormatReview activity = (ActivityFormatReview) getActivity();
            mNode = activity.getNode(new DatumReviewId(reviewId));
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());

        MenuAction<?> action;
        if (mIsPublished) {
            LaunchOptionsCommand command
                    = getCommandsFactory().newLaunchOptionsCommand(mUi.getConfig()
                    .getReviewOptions());
            MaiReviewOptions<GvData> mai = new MaiReviewOptions<>(command, mNode.getAuthorId());
            action = new MenuReviewFormatted(mai, Strings.Screens.REVIEW, app);
        } else {
            action = new MenuReviewPreview(Strings.Screens.PREVIEW, app);
        }

        mMenu = new MenuUi(action);
    }

    private void setFacts(View v, CellDimensionsCalculator.Dimensions dims) {
        newGridUi(v.findViewById(FACTS_VIEW), FACTS, VhFactSmall.class, 1, dims,
                mNode.getFacts(), getConverter().newConverterFacts()).update();
    }

    private void setImages(View v, CellDimensionsCalculator.Dimensions dims) {
        newGridUi(v.findViewById(IMAGES_VIEW), IMAGES, VhImage.class, 1, dims,
                mNode.getImages(), getConverter().newConverterImages()).update();
    }

    private void setCriteria(View v, CellDimensionsCalculator.Dimensions dims) {
        newGridUi(v.findViewById(CRITERIA_VIEW), CRITERIA, VhCriterionSmall.class, 1, dims,
                mNode.getCriteria(), getConverter().newConverterCriteria()).update();
    }

    private void setLocations(View v, CellDimensionsCalculator.Dimensions dims) {
        newGridUi(v.findViewById(LOCATIONS_VIEW), LOCATIONS, VhLocationSmall.class, 1, dims,
                mNode.getLocations(), getConverter().newConverterLocations()).update();
    }

    private void setTags(final View v, final CellDimensionsCalculator.Dimensions dims) {
        ReviewItemReference<DataSize> sizeRef = mNode.getTags().getSize();
        sizeRef.dereference(new DataReference.DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(DataValue<DataSize> value) {
                int size = 0;
                if (value.hasValue()) size = value.getData().getSize();
                int span = size > 2 ? 2 : 1;
                newGridUi(v.findViewById(TAGS_VIEW), TAGS, VhTagSmall.class, span, dims,
                        mNode.getTags(), getConverter().newConverterTags()).update();
            }
        });

    }

    private void setCover(View v) {
        new CoverRefUi(v.findViewById(IMAGE), mNode.getCover(), getActivity()).update();
    }

    private void setComment(View v) {
        LaunchViewCommand onClick = mIsPublished ? newLaunchViewCommand(GvComment.TYPE) : null;
        CommentUi commentUi
                = new CommentUi((TextView) v.findViewById(COMMENT), mNode.getComments(), onClick);
        new HideableViewUi<>(commentUi, v.findViewById(COMMENT_VIEW)).update();
    }

    private void setBannerButton(View v, ReviewLauncher launcher, int textColour) {
        DataAuthorId authorId = mNode.getAuthorId();
        mStamp = new ButtonStampUi((Button) v.findViewById(STAMP), stamp(), textColour,
                authorId, launcher, mIsPublished);
        mStamp.update();
        mRepo.getName(authorId).dereference(setAuthorAndUpdateStamp());
    }

    private void setRating(View v, ReviewLauncher launcher) {
        new RatingBarLaunchSummary((RatingBar) v.findViewById(RATING), rating(),
                mNode.getReviewId(), launcher, mIsPublished).update();
    }

    private int setSubject(View v) {
        TextUi<TextView> subject = new TextUi<>((TextView) v.findViewById(SUBJECT), subject());
        subject.update();
        return subject.getTextColour();
    }

    @NonNull
    private <T1 extends HasReviewId, T2 extends GvData, Vh extends ViewHolder>
    ViewUi<View, RefDataList<T1>>
    newGridUi(View v, int viewId, Class<Vh> vhClass,
              int span, CellDimensionsCalculator.Dimensions dims,
              RefDataList<T1> reference,
              DataConverter<T1, T2, ? extends GvDataList<T2>> converter) {
        RecyclerView view = (RecyclerView) v.findViewById(viewId);
        IdableDataList<T1> empty = new IdableDataList<>(reference.getReviewId());
        GvDataType<T2> dataType = converter.convert(empty).getGvDataType();

        LaunchViewCommand onClick = mIsPublished ? newLaunchViewCommand(dataType) : null;
        HorizontalAdapterRef<T1, T2, Vh> adapter
                = new HorizontalAdapterRef<>(reference, converter, new VhFactory<>(vhClass), dims);
        HorizontalGridUi<T1> ui
                = new HorizontalGridUi<>(getActivity(), view, adapter, span, onClick);

        return new HideableViewUi<>(ui, v);
    }

    private LaunchViewCommand newLaunchViewCommand(GvDataType<?> dataType) {
        ReviewView<?> dataView = mUi.newDataView(mNode, dataType);
        return getCommandsFactory().newLaunchViewCommand(mUi.getLauncher(), dataView);
    }

    @NonNull
    private DataReference.DereferenceCallback<NamedAuthor> setAuthorAndUpdateStamp() {
        return new DataReference.DereferenceCallback<NamedAuthor>() {
            @Override
            public void onDereferenced(DataValue<NamedAuthor> value) {
                if (value.hasValue()) {
                    mAuthor = value.getData();
                    mStamp.update();
                }
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> stamp() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                String author = mAuthor != null ? mAuthor.getName() : "";
                String date = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date
                        (mNode.getPublishDate().getTime()));
                return mAuthor != null ? author + " " + date : date;
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<Float> rating() {
        return new ViewUi.ValueGetter<Float>() {
            @Override
            public Float getValue() {
                return mNode.getRating().getRating();
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> subject() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                return mNode.getSubject().getSubject();
            }
        };
    }
}
