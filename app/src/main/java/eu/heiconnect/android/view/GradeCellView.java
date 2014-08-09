package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.FormatUtils;
import eu.heiconnect.android.webservice.grades.Grade;

public class GradeCellView extends CellView<Grade> {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private View indicatorView;
    private TextView dateTextView;
    private TextView sectionTextView;
    private TextView examTextView;
    private TextView markTextView;
    private View averageViewGroup;
    private TextView averageCountTextView;
    private TextView averageTextView;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public GradeCellView(Context context) {
        super(context);
        initializeView();
    }

    public GradeCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }


    public GradeCellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    // ----------------------------------
    // OVERRIDEN METHODS
    // ----------------------------------
    @Override
    protected void bindData(Grade grade) {
        dateTextView.setText(FormatUtils.getShortDateFormat().format(grade.getDate()));
        sectionTextView.setText(grade.getSectionName());
        examTextView.setText(grade.getExamName());

        if (grade.isUnknown()) {
            markTextView.setText("??");
            indicatorView.setBackgroundColor(getResources().getColor(R.color.info));
        } else {
            markTextView.setText(FormatUtils.getTwoDecimalNumberFormat().format(grade.getMark()));

            if (grade.getMark() < 10) {
                indicatorView.setBackgroundColor(getResources().getColor(R.color.danger));
            } else if (grade.getMark() >= 10 && grade.getMark() < 12) {
                indicatorView.setBackgroundColor(getResources().getColor(R.color.warning));
            } else {
                indicatorView.setBackgroundColor(getResources().getColor(R.color.success));
            }
        }

        if (grade.getAverageCount() > 0) {
            averageViewGroup.setVisibility(VISIBLE);

            averageCountTextView.setText(getContext().getString(R.string.cellview_grade_count, grade.getAverageCount()));
            averageTextView.setText(FormatUtils.getTwoDecimalNumberFormat().format(grade.getAverage()));
        } else {
            averageViewGroup.setVisibility(GONE);
        }
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView() {
        getInflater().inflate(R.layout.cellview_grade, this);

        indicatorView = findViewById(R.id.view_grade_indicator);
        dateTextView = (TextView) findViewById(R.id.textview_grade_date);
        sectionTextView = (TextView) findViewById(R.id.textview_grade_section);
        examTextView = (TextView) findViewById(R.id.textview_grade_exam);
        markTextView = (TextView) findViewById(R.id.textview_grade_mark);
        averageViewGroup = findViewById(R.id.viewgroup_grade_average);
        averageCountTextView = (TextView) findViewById(R.id.textview_grade_average_count);
        averageTextView = (TextView) findViewById(R.id.textview_grade_average);
    }
}
