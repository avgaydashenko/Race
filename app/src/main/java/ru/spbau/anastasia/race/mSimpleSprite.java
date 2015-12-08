package ru.spbau.anastasia.race;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public abstract class mSimpleSprite extends mBasic{

	public static final String TAG = mSimpleSprite.class.getSimpleName();

	int width, height;
	boolean selected = false;

	public mSimpleSprite(float x, float y, float dx, float dy, Bitmap bmp) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		initLog();
	}

	public mSimpleSprite(float x, float y, float dx, float dy, Resources res, int id) {
		this(x, y, dx, dy, BitmapFactory.decodeResource(res, id));
	}

	private void initLog() {
		if (bmp == null) {
			Log.e(TAG, "Created invalid sprite with no bitmap, width = " + Integer.toString(width) + ", height = " + Integer.toString(height));
		} else {
			Log.d(TAG, "Created valid sprite with bitmap = " + bmp.toString() + ", width = " + Integer.toString(width) + ", height = " + Integer.toString(height));
		}
	}

	abstract void update();

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isSelected(float x, float y) {
		selected = false;
		if (x > this.x && x < (this.x + this.width) && y > this.y
				&& y < (this.y + this.height)) {
			selected = true;
		}
		return selected;
	}
	
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void draw(Canvas c, Paint p)
	{
		Rect src = new Rect(0,0, bmp.getWidth(), bmp.getHeight());
		Rect dst;
		float hight = y / mSettings.ScaleFactorX / 60 + 40;
		switch (type) {
			case TYPE_BACKGROUNDSPRITE:
				dst = new Rect(0, 0,(int)((hight / 2 + 10) * mSettings.ScaleFactorX), (int)((hight) * mSettings.ScaleFactorX / 2));
				break;
			default:
				dst = new Rect(0, 0,(int)((hight / 2) * mSettings.ScaleFactorX), (int)((hight) * mSettings.ScaleFactorX));
				break;
		}

		dst.offset((int)x, (int)y);
		c.drawBitmap(bmp, src, dst, p);
	}
}
