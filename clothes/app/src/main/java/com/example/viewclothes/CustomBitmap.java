package com.example.viewclothes;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;

public class CustomBitmap {
    private long id;
    public float startDis;
    public PointF midPoint;
    public float oldRotation = 0;
    public float rotation = 0;
    public PointF startPoint = new PointF();
    public Matrix matrix = new Matrix();
    public Bitmap bitmap;

    public CustomBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
