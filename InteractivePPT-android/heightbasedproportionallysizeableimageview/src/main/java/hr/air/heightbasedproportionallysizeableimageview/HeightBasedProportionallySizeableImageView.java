package hr.air.heightbasedproportionallysizeableimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by zeko868 on 8.11.2016..
 */

public class HeightBasedProportionallySizeableImageView extends ImageView {
    public HeightBasedProportionallySizeableImageView(Context context) {
        super(context);
    }

    public HeightBasedProportionallySizeableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightBasedProportionallySizeableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        BitmapDrawable b = ((BitmapDrawable)this.getDrawable());
        int w = b.getIntrinsicWidth();
        int h = b.getIntrinsicHeight();
        float ratio = ((float)height)/h;
        setMeasuredDimension((int)(ratio*w), height);
    }
}
