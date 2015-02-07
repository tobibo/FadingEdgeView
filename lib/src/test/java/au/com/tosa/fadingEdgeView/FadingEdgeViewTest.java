package au.com.tosa.fadingEdgeView;

import android.app.Activity;
import android.app.RobolectricActivityManager;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.res.Attribute;
import org.robolectric.res.ResourceLoader;
import org.robolectric.shadows.RoboAttributeSet;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class FadingEdgeViewTest {

    private static final String TEST_PACKAGE = "au.com.tosa.fadingEdgeView";
    private FadingEdgeView mFadingView;
    private ActivityController<Activity> mActivityController;
    private ResourceLoader resourceLoader;


    @Before
    public void setUp() throws Exception {
        Resources r = RuntimeEnvironment.application.getResources();
        resourceLoader = shadowOf(r).getResourceLoader();
    }


    @Test
    public void testOnMeasure() throws Exception {
        AttributeSet attributes = new RoboAttributeSet(createAttribute("true", "true", "#fff", "50dp"), resourceLoader);
        mFadingView = new FadingEdgeView(RuntimeEnvironment.application, attributes);
        FadingEdgeView fadingViewSpy = spy(mFadingView);
        verify(fadingViewSpy).createFadeView(true);
    }

    @Test
    public void testCreateFadeViewTop() throws Exception {
        AttributeSet attributes = new RoboAttributeSet(createAttribute("true", "true", "#fff", "50dp"), resourceLoader);
        mFadingView = new FadingEdgeView(RuntimeEnvironment.application, attributes);
        View v = mFadingView.createFadeView(true);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        assertNotNull("Should have params set", params);
        assertEquals("If top view gravity should be top", Gravity.TOP | Gravity.LEFT, params.gravity);
    }

    @Test
    public void testCreateFadeViewBottom() throws Exception {
        AttributeSet attributes = new RoboAttributeSet(createAttribute("true", "true", "#fff", "50dp"), resourceLoader);
        mFadingView = new FadingEdgeView(RuntimeEnvironment.application, attributes);
        View v = mFadingView.createFadeView(false);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        assertNotNull("Should have params set", params);
        assertEquals("If bottom view gravity should be top", Gravity.BOTTOM | Gravity.LEFT, params.gravity);
    }

    private List<Attribute> createAttribute(String bottomFade, String topFade, String colorFade, String heightFade) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute("au.com.tosa.fadingEdgeView:attr/topFade", bottomFade, TEST_PACKAGE));
        attributeList.add(new Attribute("au.com.tosa.fadingEdgeView:attr/bottomFade", bottomFade, TEST_PACKAGE));
        attributeList.add(new Attribute("au.com.tosa.fadingEdgeView:attr/colorFade", colorFade, TEST_PACKAGE));
        attributeList.add(new Attribute("au.com.tosa.fadingEdgeView:attr/heightFade", heightFade, TEST_PACKAGE));
        return attributeList;
    }
}
