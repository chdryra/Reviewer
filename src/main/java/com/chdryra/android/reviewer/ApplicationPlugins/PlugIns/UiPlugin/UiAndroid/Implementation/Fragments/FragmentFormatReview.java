/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ButtonUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalGridUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TextUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.CommentFormatter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;
import com.chdryra.android.reviewer.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentFormatReview extends Fragment {
    private static final int LAYOUT = R.layout.fragment_review_formatted;
    private static final int HEADER = R.id.header_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int STAMP = R.id.stamp_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int LOCATIONS = R.id.locations_formatted;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int IMAGES = R.id.images_formatted;
    private static final int FACTS = R.id.facts_formatted;

    private ButtonUi mStamp;
    private MenuUi mMenu;

    private Review mReview;
    private NamedAuthor mAuthor;
    private TagsManager mTagsManager;
    private ConverterGv mConverter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        Bundle args = getArguments();
        if (args != null) mReview = app.unpackReview(args);
        if (mReview == null) throw new RuntimeException("No review found");

        mTagsManager = app.getRepository().getTagsManager();
        mConverter = app.getUi().getGvConverter();

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        TextUi<TextView> subject = new TextUi<>((TextView) v.findViewById(SUBJECT), subject());
        RatingBarUi ratingBar = new RatingBarUi((RatingBar) v.findViewById(RATING), rating());
        int colour = subject.getTextColour();
        mStamp = new ButtonUi((Button) v.findViewById(STAMP), stamp(), colour);
        HorizontalGridUi<GvTag> tags = new HorizontalGridUi<>(getActivity(), (RecyclerView) v
                .findViewById(TAGS),
                newVhFactory(new GvTag()), tags());
        ButtonUi locations = new ButtonUi((Button) v.findViewById(LOCATIONS), locations(), colour);
        HorizontalGridUi<GvCriterion> criteria = new HorizontalGridUi<>(getActivity(),
                (RecyclerView) v.findViewById(CRITERIA),
                newVhFactory(new GvCriterion()), criteria());
        TextUi<TextView> comment = new TextUi<>((TextView) v.findViewById(COMMENT), comment());
        HorizontalGridUi<GvImage> images = new HorizontalGridUi<>(getActivity(), (RecyclerView) v
                .findViewById(IMAGES),
                newVhFactory(new GvImage()), images());
        HorizontalGridUi<GvFact> facts = new HorizontalGridUi<>(getActivity(), (RecyclerView) v
                .findViewById(FACTS),
                newVhFactory(new GvFact()), facts());
        CoverUi cover = new CoverUi(v.findViewById(HEADER), cover(), getActivity());

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());

        FactoryCommands factory = new FactoryCommands();
        LaunchOptionsCommand command = factory.newLaunchOptionsCommand(app.getUi().getConfig()
                .getReviewOptions());
        MaiReviewOptions<GvData> mai = new MaiReviewOptions<>(command, mReview.getAuthorId());
        MenuAction<GvData> menu = new MenuReviewFormatted(mai, Strings.Screens.REVIEW, app);
        mMenu = new MenuUi(menu);

        subject.update();
        ratingBar.update();
        mStamp.update();
        tags.update();
        locations.update();
        criteria.update();
        comment.update();
        images.update();
        facts.update();
        cover.update();

        DataReference<NamedAuthor> name = app.getRepository().getName(mReview.getAuthorId());
        name.dereference(dereference());

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

    private GvDataList<GvTag> getTags() {
        ReviewId reviewId = mReview.getReviewId();
        ItemTagCollection tags = mTagsManager.getTags(reviewId.toString());
        return mConverter.newConverterItemTags().convert(tags, reviewId);
    }

    @NonNull
    private DataReference.DereferenceCallback<NamedAuthor> dereference() {
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
    private ViewUi.ValueGetter<Bitmap> cover() {
        return new ViewUi.ValueGetter<Bitmap>() {
            @Override
            @Nullable
            public Bitmap getValue() {
                IdableList<? extends DataImage> images = mReview.getImages();
                return images.size() > 0 ? images.getItem(0).getBitmap() : null;
            }
        };
    }

    @NonNull
    private ViewHolderFactory<ViewHolder> newVhFactory(final GvData datum) {
        return new ViewHolderFactory<ViewHolder>() {
            @Override
            public ViewHolder newViewHolder() {
                return datum.getViewHolder();
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<GvDataList<GvTag>> tags() {
        return new ViewUi.ValueGetter<GvDataList<GvTag>>() {
            @Override
            public GvDataList<GvTag> getValue() {
                return getTags();
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<GvDataList<GvCriterion>> criteria() {
        return new ViewUi.ValueGetter<GvDataList<GvCriterion>>() {
            @Override
            public GvDataList<GvCriterion> getValue() {
                return mConverter.newConverterCriteria().convert(mReview.getCriteria());
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<GvDataList<GvImage>> images() {
        return new ViewUi.ValueGetter<GvDataList<GvImage>>() {
            @Override
            public GvDataList<GvImage> getValue() {
                return mConverter.newConverterImages().convert(mReview.getImages());
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<GvDataList<GvFact>> facts() {
        return new ViewUi.ValueGetter<GvDataList<GvFact>>() {
            @Override
            public GvDataList<GvFact> getValue() {
                return mConverter.newConverterFacts().convert(mReview.getFacts());
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
                        (mReview.getPublishDate().getTime()));
                return mAuthor != null ? "By " + author + " on " + date : "on " + date;
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<Float> rating() {
        return new ViewUi.ValueGetter<Float>() {
            @Override
            public Float getValue() {
                return mReview.getRating().getRating();
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> subject() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                return mReview.getSubject().getSubject();
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> comment() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                return CommentFormatter.formatComments(mReview.getComments());
            }
        };
    }

    @NonNull
    private ViewUi.ValueGetter<String> locations() {
        return new ViewUi.ValueGetter<String>() {
            @Override
            public String getValue() {
                IdableList<? extends DataLocation> locations = mReview.getLocations();
                if (locations.size() == 0) return "";
                if (locations.size() == 1) return "@" + locations.getItem(0).getName();
                return "@ " + String.valueOf(locations.size()) + " locations";
            }
        };
    }
}
