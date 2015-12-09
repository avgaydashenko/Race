package ru.spbau.anastasia.race;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class mBasic {

	Bitmap bmp;

	public int type;

	public boolean exist = true;

	float x, y;
	float dx, dy;
	float width, height;

	public static final int TYPE_PLAYERSPRITE = 1;
	public static final int TYPE_BARRIERSPRITE = 2;
	public static final int TYPE_BACKGROUNDSPRITE = 3;
	public static final int TYPE_LIVE = 4;

	abstract void update();

	abstract boolean isSelected(mBasic player);
	
	abstract void draw(Canvas c, Paint p);

	public float getX() { return x; }

	public float getY() { return y; }

	public float getWidth() {
		return width;
	}

	public float getHeight() { return height; }

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getDx() { return dx; }

	public void setDx(float dx) { this.dx = dx; }

	public float getDy() { return dy; }

	public void setDy(float dy) { this.dy = dy;	}
}
