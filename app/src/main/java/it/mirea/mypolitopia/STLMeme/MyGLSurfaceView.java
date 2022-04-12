package it.mirea.mypolitopia.STLMeme;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import it.mirea.mypolitopia.Map.Cell;
import it.mirea.mypolitopia.R;
import it.mirea.mypolitopia.ViewActivity;

public class MyGLSurfaceView extends GLSurfaceView {

    private final GLRenderer glRenderer;

    public MyGLSurfaceView(Context context, Cell[][] field){
        super(context);

        glRenderer = new GLRenderer(this.getContext(), field);
        setRenderer(glRenderer);

    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                if (glRenderer.getEyeX() + dx > -5 && glRenderer.getEyeX() + dx < 5 && glRenderer.getEyeZ() + dy > -7 && glRenderer.getEyeZ() + dy < 6) {
                    glRenderer.setEyeCoords(glRenderer.getEyeX() + dx, glRenderer.getEyeY(), glRenderer.getEyeZ() + dy);
                    glRenderer.setCenterCoords(glRenderer.getCenterX() + dx, glRenderer.getCenterY(), glRenderer.getCenterZ() + dy);
                }


                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }

}