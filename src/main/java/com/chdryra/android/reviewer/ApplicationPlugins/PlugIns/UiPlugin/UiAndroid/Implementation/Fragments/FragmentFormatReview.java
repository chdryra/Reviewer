/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.AuthorNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CommentNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverNodeBannerUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CriteriaNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.DateNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.FactsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalAdapterRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalGridUi;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ImagesNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.LocationsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuOptionsAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUpAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.NodeDataExpandableUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarTouchable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TagsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TextUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.VhFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentFormatReview extends Fragment implements ReviewNode.NodeObserver,
        OptionSelectListener {
    private static final String ID = TagKeyGenerator.getKey(FragmentFormatReview.class,
            "ReviewId");
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentFormatReview.class,
            "published");

    private static final int LAYOUT = R.layout.fragment_review_formatted;
    private static final int IMAGE = R.id.image_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int HEADLINE = R.id.headline_formatted;
    private static final int AUTHOR = R.id.author_formatted;
    private static final int DATE = R.id.date_formatted;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int FACTS = R.id.facts_formatted;
    private static final int LOCATIONS = R.id.locations_formatted;
    private static final int IMAGES = R.id.images_formatted;
    private static final int TITLE = R.id.section_title;
    private static final int VALUE = R.id.section_value;
    private static final int DATA = R.id.section_data;

    private static final ReviewViewParams.CellDimension FULL
            = ReviewViewParams.CellDimension.FULL;
    private static final ReviewViewParams.CellDimension HALF
            = ReviewViewParams.CellDimension.HALF;
    private static final int IMAGE_PADDING = R.dimen.formatted_image_padding;

    private boolean mIsPublished = true;
    private ReviewNode mNode;
    private UiSuite mUi;
    private RepositorySuite mRepo;
    private MenuUi mMenu;

    private CoverNodeBannerUi mCover;
    private TextUi<TextView> mSubject;
    private RatingBarTouchable mRating;
    private AuthorNodeUi mAuthor;
    private DateNodeUi mDate;
    private CommentNodeUi mComment;
    private TagsNodeUi mTags;
    private NodeDataExpandableUi<DataCriterion> mCriteria;
    private NodeDataExpandableUi<DataFact> mFacts;
    private NodeDataExpandableUi<DataLocation> mLocations;
    private ImagesNodeUi mImages;

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

    public ReviewId getNodeId() {
        return mNode.getReviewId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        setCover(v);
        setSubject(v);
        setRating(v);
        setAuthor(v);
        setDate(v);
        setComment(v);
        setTags(v);
        setCriteria(v);
        setFacts(v);
        setLocations(v);
        setImages(v);

        update();

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

    @Override
    public void onChildAdded(ReviewNode child) {
        update();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        update();
    }

    @Override
    public void onNodeChanged() {
        update();
    }

    @Override
    public void onTreeChanged() {
        update();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mMenu.onOptionSelected(requestCode, option);
    }

    private FactoryCommands getCommandsFactory() {
        return mUi.getCommandsFactory();
    }

    private void setNode(Bundle args) {
        String reviewId = args.getString(ID);
        if (reviewId == null) throwNoReview();
        try {
            ActivityFormatReview activity = (ActivityFormatReview) getActivity();
            mNode = activity.getNode(new DatumReviewId(reviewId));
            mNode.registerObserver(this);
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);

        MenuAction<?> action;
        if (mIsPublished) {
            OptionsCommand command
                    = getCommandsFactory().newReviewOptionsSelector(new NodeAuthorId(mNode));
            MaiOptionsCommand<GvData> mai = new MaiOptionsCommand<>(command);
            action = new MenuOptionsAppLevel(Strings.Screens.FORMATTED, upAction, mai, ui);
        } else {
            action = new MenuUpAppLevel(Strings.Screens.PREVIEW, upAction, ui);
        }

        mMenu = new MenuUi(action);
    }

    private void update() {
        mCover.update();
        mSubject.update();
        mRating.update();
        mAuthor.update();
        mDate.update();
        mComment.update();
        mTags.update();
        mCriteria.update();
        mFacts.update();
        mLocations.update();
        mImages.update();
    }

    @Nullable
    private Command launchView(GvDataType<?> type) {
        return mIsPublished ? newLaunchViewCommand(type) : null;
    }

    private void setCover(View v) {
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(FULL, HALF, 0);

        Bitmap placeholder = BitmapFactory.decodeResource(getResources(),
                R.drawable.image_placeholder);
        ImageView image = (ImageView) v.findViewById(IMAGE);
        image.getLayoutParams().width = dims.getCellWidth();
        image.getLayoutParams().height = dims.getCellHeight();
        mCover = new CoverNodeBannerUi(image, mNode, placeholder);
        setLaunchOnClick(mCover, launchSummary());
    }

    private void setTags(final View v) {
        TextView tags = (TextView) v.findViewById(TAGS);
        mTags = new TagsNodeUi(tags, mNode);
        setLaunchOnClick(mTags, GvTag.TYPE);
    }

    private void setComment(View v) {
        mComment = new CommentNodeUi(getSection(v, COMMENT), (TextView) v.findViewById(HEADLINE),
                mNode, launchView(GvComment.TYPE));
    }

    private LinearLayout getSection(View v, int id) {
        return (LinearLayout) v.findViewById(id);
    }

    private void setCriteria(View v) {
        mCriteria = new CriteriaNodeUi(getSection(v, CRITERIA), inflater(), mNode);
        setLaunchOnClick(mCriteria, GvCriterion.TYPE);
    }

    private void setFacts(View v) {
        mFacts = new FactsNodeUi(getSection(v, FACTS), inflater(), mNode);
        setLaunchOnClick(mFacts, GvFact.TYPE);
    }

    private void setLocations(View v) {
        mLocations = new LocationsNodeUi(getSection(v, LOCATIONS), inflater(), mNode);
        setLaunchOnClick(mLocations, GvLocation.TYPE);
    }

    private void setAuthor(View v) {
        AuthorReference reference = mRepo.getAuthorsRepository().getReference(mNode.getAuthorId());
        mAuthor = new AuthorNodeUi(getSection(v, AUTHOR), reference);
        setLaunchOnClick(mAuthor, launchAuthor());
    }

    private void setDate(View v) {
        mDate = new DateNodeUi(getSection(v, DATE), mNode);
        setLaunchOnClick(mDate, launchAuthor());
    }

    private void setRating(View v) {
        Command onTouch = mIsPublished ? launchSummary() : null;
        mRating = new RatingBarTouchable((RatingBar) v.findViewById(RATING), mNode, onTouch);
    }

    private void setSubject(View v) {
        mSubject = new SubjectNodeUi((TextView) v.findViewById(SUBJECT), mNode, launchAuthor());
        setLaunchOnClick(mSubject, launchSummary());
    }

    private void setImages(View v) {
        LinearLayout section = getSection(v, IMAGES);
        final RecyclerView grid = (RecyclerView) section.findViewById(DATA);
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        float padding = getResources().getDimensionPixelSize(IMAGE_PADDING);
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(HALF, HALF, (int)padding);

        ViewUi<RecyclerView, RefDataList<DataImage>> dataView = newGridUi
                (grid, VhImage.class, 1, dims, mUi.getGvConverter().newConverterImages(),
                new ViewUi.ValueGetter<RefDataList<DataImage>>() {
                    @Override
                    public RefDataList<DataImage> getValue() {
                        return mNode.getImages();
                    }
                });

        mImages = new ImagesNodeUi(section, dataView, mNode);
    }

    private void setLaunchOnClick(ViewUi<?, ?> layout, GvDataType<?> type) {
        Command onClick = launchView(type);
        setLaunchOnClick(layout, onClick);
    }

    private void setLaunchOnClick(ViewUi<?, ?> layout, @Nullable Command onClick) {
        if(mIsPublished && onClick != null) layout.setOnClickCommand(onClick);
    }

    private LayoutInflater inflater() {
        return LayoutInflater.from(getActivity());
    }

    @NonNull
    private <T1 extends HasReviewId, T2 extends GvData, Vh extends ViewHolder>
    ViewUi<RecyclerView, RefDataList<T1>>
    newGridUi(RecyclerView view, Class<Vh> vhClass,
              int span, CellDimensionsCalculator.Dimensions dims,
              DataConverter<T1, T2, ? extends GvDataList<T2>> converter,
              ViewUi.ValueGetter<RefDataList<T1>> getter) {
        IdableDataList<T1> empty = new IdableDataList<>(getter.getValue().getReviewId());
        GvDataType<T2> dataType = converter.convert(empty).getGvDataType();

        HorizontalAdapterRef<T1, T2, Vh> adapter
                = new HorizontalAdapterRef<>(getter, converter, new VhFactory<>(vhClass), dims);

        Command onClick = mIsPublished ? newLaunchViewCommand(dataType) : null;
        return new HorizontalGridUi<>(getActivity(), view, adapter, span, onClick);
    }

    private Command newLaunchViewCommand(GvDataType<?> dataType) {
        return getCommandsFactory().newLaunchViewCommand(mUi.newDataView(mNode, dataType));
    }

    private Command launchAuthor() {
        return mUi.getCommandsFactory().newLaunchAuthorCommand(mNode.getAuthorId());
    }

    private Command launchSummary() {
        return mUi.getCommandsFactory().newLaunchSummaryCommand(mNode.getReviewId());
    }

    private static class NodeAuthorId extends DatumAuthorId {
        private ReviewNode mNode;

        public NodeAuthorId(ReviewNode node) {
            super(node.getReviewId(), node.getAuthorId().toString());
            mNode = node;
        }

        @Override
        public ReviewId getReviewId() {
            return mNode.getReviewId();
        }

        @Override
        public String toString() {
            return mNode.getAuthorId().toString();
        }
    }
}
