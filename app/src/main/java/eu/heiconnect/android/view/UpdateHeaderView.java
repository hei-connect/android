package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.DateUtils;
import eu.heiconnect.android.utils.FormatUtils;

public class UpdateHeaderView extends LinearLayout {

    private View cardView;
    private ImageView iconImageView;
    private TextView syncTextView;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public UpdateHeaderView(Context context) {
        super(context);
        initializeView(context);
    }

    public UpdateHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public UpdateHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    public void bindData(Date lastSync) {
        syncTextView.setText(FormatUtils.getUserFriendlyElapsedTimeSinceDate(lastSync, getContext()));

        if (DateUtils.getDifferenceInHours(lastSync, new Date()) >= 4) {
            cardView.setBackgroundColor(getResources().getColor(R.color.warning));
            iconImageView.setImageResource(R.drawable.ic_warning_150);
        } else {
            cardView.setBackgroundColor(getResources().getColor(R.color.success));
            iconImageView.setImageResource(R.drawable.ic_done_150);
        }
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.headerview_update, this);
        cardView = findViewById(R.id.viewgroup_update_card);
        iconImageView = (ImageView) findViewById(R.id.imageview_update_icon);
        syncTextView = (TextView) findViewById(R.id.textview_update_last_sync);
    }
}
