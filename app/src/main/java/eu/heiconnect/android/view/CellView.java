package eu.heiconnect.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public abstract class CellView<T> extends LinearLayout {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------

    private final LayoutInflater inflater;
    private T data;
    protected int position;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
    }

    public CellView(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    public CellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflater = LayoutInflater.from(context);
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        bindData(data);
    }

    public void setData(T data, int position) {
        this.data = data;
        this.position = position;
        bindData(data);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    // ----------------------------------
    // ABSTRACT METHODS
    // ----------------------------------

    protected abstract void bindData(T object);

}