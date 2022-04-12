package it.mirea.mypolitopia.STLMeme;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;


import java.io.IOException;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import it.mirea.mypolitopia.Map.Cell;


public class GLRenderer implements GLSurfaceView.Renderer {

    private Model model;
    private Model city;
    private Model npc;
    private Point mCenterPoint;
    public Point eye = new Point(0, 3, -6);
    private Point up = new Point(0, 0, 1);
    public Point center = new Point(0, 0, 0);
    private float mScalef = 1;
    private float mDegree = 0;
    private Cell[][] field;
    public volatile float mAngle;


    public GLRenderer(Context context, Cell[][] field) {
        try {
            this.field = field;
            //model = new Model();
            model = new STLReader().parserBinStlInAssets(context, "sixgon.stl");
            city = new STLReader().parserBinStlInAssets(context, "xyi.stl");
            npc = new STLReader().parserBinStlInAssets(context, "npc.stl");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        // Очистить экран и кэш глубины
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // сброс текущей модели наблюдения
        gl.glLoadIdentity();

        // глаз смотрит на происхождение
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);

        // Для того, чтобы иметь трехмерное ощущение, сделать модель постоянно вращаться, изменяя значение MDegree.
        //gl.glRotatef(mDegree, 0, 1, 0);

        // продолжить модель к представлению, просто установите
        gl.glScalef(mScalef, mScalef, mScalef);
        // переместить модель в начало
        gl.glTranslatef(0, 0,0);


        //===================begin==============================//

        // Разрешить каждую вершину установить вектор
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        // Разрешить настройки вершин
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Разрешить настройки цвета

        // Нарисуйте треугольник
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
            //gl.glColor4f(0.5f, 0.5f * i, 1.0f * i, 1.0f * i);
        for(int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                switch (field[i][j].getType()) {
                    case WATER:
                        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
                        break;

                    case GROUND:
                        switch (field[i][j].getResource().getResource()) {
                            case TREE:
                                gl.glColor4f(0.34f, 0.22f, 0.09f, 1f);
                                break;
                            case STONE:
                                gl.glColor4f(0.31f, 0.31f, 0.31f, 1f);
                                break;
                            case GOLD:
                                gl.glColor4f(0.91f, 1f, 0.12f, 1f);
                                break;

                            default:
                                gl.glColor4f(0.15f, 0.35f, 0.03f, 1.0f);
                                break;
                        }
                        //System.out.println(field[i][j].getResource().getResource());
                }
                gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFacetCount() * 3);
                if (field[i][j].getBoolCity()) {
                    gl.glColor4f(0.31f, 0.31f, 0.31f, 1f);
                    gl.glTranslatef(0, 1f, 0);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, city.getVnormBuffer());
                    // Установить треугольную вершинную вершину источника данных
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, city.getVertBuffer());
                    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, city.getFacetCount() * 3);
                    gl.glTranslatef(0, -1f, 0);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, city.getVnormBuffer());
                    // Установить треугольную вершинную вершину источника данных
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, city.getVertBuffer());
                    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, city.getFacetCount() * 3);
                }
                if (field[i][j].isNPC()) {
                    gl.glColor4f(0.54f, 0.17f, 0.89f, 1f);
                    gl.glTranslatef(0, 1f, 0);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, npc.getVnormBuffer());
                    // Установить треугольную вершинную вершину источника данных
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, npc.getVertBuffer());
                    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, npc.getFacetCount() * 3);
                    gl.glTranslatef(0, -1f, 0);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, npc.getVnormBuffer());
                    // Установить треугольную вершинную вершину источника данных
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, npc.getVertBuffer());
                }
                //gl.glTranslatef(1f, 0, 1.5f);
                gl.glTranslatef(1f, 0, 1.5f);
                // Установить метод векторных данных источника данных
                gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
                // Установить треугольную вершинную вершину источника данных
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());

            }
            //gl.glTranslatef(-((float) field.length + 1) + 1f, 0, -1.5f * ((float) field.length - 2));

            gl.glTranslatef(-((float) field.length + 1), 0, -1.5f * ((float) field.length));

            gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
            // Установить треугольную вершинную вершину источника данных
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());
            gl.glTranslatef(0, 0, 1.5f); // 1.5 - красивенько

            gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
            // Установить треугольную вершинную вершину источника данных
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());
        }
            // Отмените настройку вершины
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            // Остановка Векторные настройки
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

            //=====================end============================//

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Установите размер сцены OpenGL, (0, 0) указывает верхний левый угол визуального порта в окне, (ширина, высота) определяет размер просмотра просмотра.
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION); // Установите матрицу проекции
        gl.glLoadIdentity(); // Установите матрицу в качестве блок-матрицы, эквивалентной для сброса матрицы
        GLU.gluPerspective(gl, 50.0f, ((float) width) / height, 1f, 100f);// Установить диапазон перспективы

        // Объявлены следующие два предложения, все преобразования в будущем являются моделями (т.е. мы рисуем графику)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();


    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST); // включить кэш глубины
        gl.glClearDepthf(1.0f); // установить значение кэша глубины
        gl.glDepthFunc(GL10.GL_LEQUAL); // Установить функцию сравнения кэша глубины

        gl.glShadeModel(GL10.GL_SMOOTH);// Установите режим тени GL_SMooth
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                float r = model.getR();

                // R - радиус, а не диаметр, поэтому отношение декомпрессии может быть рассчитано с использованием 0,5 / R
                mScalef = 0.5f / r;
                mCenterPoint = model.getCentrePoint();
            }
        }
    }



    // ГЕТТЕРЫ КАМЕРЫ
    public float getEyeX() {
        return eye.getX();
    }

    public float getEyeY() {
        return eye.getY();
    }

    public float getEyeZ() {
        return eye.getZ();
    }

    public float getCenterX() {
        return center.getX();
    }

    public float getCenterY() {
        return center.getY();
    }

    public float getCenterZ() {
        return center.getZ();
    }

    // СЕТТЕРЫ КАМЕРЫ
    public void setEyeX(float x) {
         this.eye.setX(x);
    }

    public void setEyeY(float y) {
        eye.setY(y);
    }

    public void setEyeZ(float z) {
        eye.setZ(z);
    }

    public void setCenterX(float x) {
        center.setX(x);
    }

    public void setCenterY(float y) {
        center.setY(y);
    }

    public void setCenterZ(float z) {
        center.setZ(z);
    }

    public void setEyeCoords(float x, float y, float z) {
        eye.setCoord(x, y, z);
    }

    public void setCenterCoords(float x, float y, float z) {
        center.setCoord(x, y, z);
    }


}

