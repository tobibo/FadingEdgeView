package au.com.tosa.fadingEdgeView;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by tosa on 5/02/15.
 */
public class FadingEdgeView extends FrameLayout {
    private boolean bottomFade;
    private boolean topFade;
    private int fadingEdgeHeight;
    private View topFadeView;
    private View bottomFadeView;
    private int gradientColor;
    private int gradientColorMiddle;
    private int gradientColorEnd;

    public FadingEdgeView(Context context) {
        this(context, null);
    }

    public FadingEdgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadingEdgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.FadingEdgeView, defStyleAttr, 0);
        topFade = a.getBoolean(R.styleable.FadingEdgeView_topFade, false);
        bottomFade = a.getBoolean(R.styleable.FadingEdgeView_bottomFade, false);
        gradientColor = a.getColor(R.styleable.FadingEdgeView_colorFade, getResources().getColor(android.R.color.white));
        fadingEdgeHeight = convertDpToPixel(20, getContext());
        fadingEdgeHeight = a.getDimensionPixelSize(R.styleable.FadingEdgeView_heightFade, fadingEdgeHeight);

        int r = (gradientColor >> 16) & 0xFF;
        int g = (gradientColor >> 8) & 0xFF;
        int b = (gradientColor >> 0) & 0xFF;

        gradientColorMiddle = Color.argb(85, r, g, b);
        gradientColorEnd = Color.argb(0, r, g, b);

        if (topFade && topFadeView == null) {
            topFadeView = createFadeView(true);
            addView(topFadeView);
        }
        if (bottomFade && bottomFadeView == null) {
            bottomFadeView = createFadeView(false);
            addView(bottomFadeView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected View createFadeView(boolean top) {
        View view = new View(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, fadingEdgeHeight);

        final int[] colorGradient;
        final float[] weightColorsGradient;
        if (top) {
            params.gravity = Gravity.LEFT | Gravity.TOP;
            colorGradient = new int[]{gradientColor, gradientColorMiddle, gradientColorEnd};
            weightColorsGradient = new float[]{0, 0.3f, 1};
        } else {
            params.gravity = Gravity.LEFT | Gravity.BOTTOM;
            colorGradient = new int[]{gradientColorEnd, gradientColorMiddle, gradientColor};
            weightColorsGradient = new float[]{0, 0.7f, 1};
        }
        view.setLayoutParams(params);
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, 0,
                        fadingEdgeHeight,
                        colorGradient,
                        weightColorsGradient,
                        Shader.TileMode.REPEAT);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        setBackgroundForView(view, p);
        return view;
    }

    @SuppressWarnings("deprecation")
    private void setBackgroundForView(View view, Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (topFade && topFadeView != null) {
            topFadeView.bringToFront();
        }
        if (bottomFade && bottomFadeView != null) {
            bottomFadeView.bringToFront();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;
    }

}
