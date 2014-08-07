package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.DateUtils;
import eu.heiconnect.android.utils.FormatUtils;
import eu.heiconnect.android.webservice.schedule.Course;

public class CourseCellView extends CellView<Course> {

    private View indicatorView;
    private TextView startTimeTextView;
    private TextView durationTextView;
    private TextView nameTextView;
    private TextView roomTextView;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public CourseCellView(Context context) {
        super(context);
        initializeView();
    }

    public CourseCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }


    public CourseCellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    protected void bindData(Course course) {
        startTimeTextView.setText(FormatUtils.getTimeFormat().format(course.getDate()));
        durationTextView.setText(getContext().getString(R.string.generic_time_minute,
                DateUtils.getDifferenceInMinutes(course.getDate(), course.getEndDate())));
        nameTextView.setText(getContext().getString(R.string.cellview_course_name,
                course.getKind(), course.getName()));
        roomTextView.setText(course.getPlace());

        Date now = new Date();
        if (now.before(course.getDate())) {
            // Course not started
            indicatorView.setBackgroundColor(getResources().getColor(R.color.primary));
        } else if (now.before(course.getEndDate())) {
            // Course in progress
            indicatorView.setBackgroundColor(getResources().getColor(R.color.warning));
        } else {
            // Course finished
            indicatorView.setBackgroundColor(getResources().getColor(R.color.success));
        }
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView() {
        getInflater().inflate(R.layout.cellview_course, this);

        indicatorView = findViewById(R.id.view_course_indicator);
        startTimeTextView = (TextView) findViewById(R.id.textview_course_start_time);
        durationTextView = (TextView) findViewById(R.id.textview_course_duration);
        nameTextView = (TextView) findViewById(R.id.textview_course_name);
        roomTextView = (TextView) findViewById(R.id.textview_course_room);
    }
}
