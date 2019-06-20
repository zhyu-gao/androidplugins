package com.newvision.enjoycrop.core.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * 支持缩放功能、拖动功能的ImageView
 *
 * @author Zhouztashin
 * @version 1.0
 * @created 2016/3/17
 */
public class EnjoyImageView extends ImageView
{
    private final String TAG = EnjoyImageView.class.getSimpleName();
    private ImageMatrixTouchImpl mImageToucheHandler;
    private Context context;

    public EnjoyImageView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }


    public EnjoyImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init()
    {
        mImageToucheHandler = ImageMatrixTouchImpl.newInstance(this);
        mImageToucheHandler.init();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        try
        {
            super.onDraw(canvas);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"不支持的图片!",Toast.LENGTH_LONG).show();
            Activity activity = (Activity) context;
            activity.finish();
        }
    }


    public double getScale()
    {
        return mImageToucheHandler.getScale();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean result = mImageToucheHandler.touch(event);
        if (result)
        {
            return result;
        }
        else
        {
            return super.onTouchEvent(event);
        }
    }

    public float getActuallyScrollX()
    {
        return getScrollX() + mImageToucheHandler.getScrollX();
    }

    public float getActuallyScrollY()
    {
        return getScrollY() + mImageToucheHandler.getScrollY();
    }

    /**
     * 设置图片移动放大边界
     *
     * @param rectF
     * @return
     */
    public void setRestrictBound(RectF rectF)
    {
        mImageToucheHandler.setRestrictRect(rectF);
    }
}
