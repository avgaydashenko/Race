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
	public static final int SIZE_OF_BARRIER = 40;
	public static final int SIZE_OF_DELTA_BARRIER = 5;
	public static final int SIZE_OF_BACKGROUND = 70;
	public static final int SIZE_OF_DELTA_BACKGROUND = 4;

	boolean selected = false;

	Rect src, dst;

	public mSimpleSprite(float x, float y, float dx, float dy, Bitmap bmp) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.bmp = bmp;
		recalcParametrs();
		src = new Rect(0,0, bmp.getWidth(), bmp.getHeight());
		dst = new Rect();
		initLog();
	}

	public mSimpleSprite(float x, float y, float dx, float dy, Resources res, int id) {
		this(x, y, dx, dy, BitmapFactory.decodeResource(res, id));
	}

	private void initLog() {
		if (bmp == null) {
			Log.e(TAG, "Created invalid sprite with no bitmap, width = " + Integer.toString( (int) width) + ", height = " + Integer.toString( (int) height));
		} else {
			Log.d(TAG, "Created valid sprite with bitmap = " + bmp.toString() + ", width = " + Integer.toString( (int) width) + ", height = " + Integer.toString( (int) height));
		}
	}

	abstract void update();

	private boolean intersect( float x1, float y1, float x2, float y2){
		return(
				(
						(
								( x>=x1 && x<=x2 )||( x + width>=x1 && x + width<=x2  )
						) && (
								( y>=y1 && y<=y2 )||( y + height>=y1 && y + height<=y2 )
						)
				)||(
						(
								( x1>=x && x1<=x + width )||( x2>=x && x2<=x + width  )
						) && (
								( y1>=y && y1<=y + height )||( y2>=y && y2<=y + height )
						)
				)
		)||(
				(
						(
								( x>=x1 && x<=x2 )||( x + width >= x1 && x + width<=x2  )
						) && (
								( y1>=y && y1<=y + height )||( y2>=y && y2<=y + height )
						)
				)||(
						(
								( x1>=x && x1<=x + width )||( x2>=x && x2<=x + width  )
						) && (
								( y>=y1 && y<=y2 )||( y + height>=y1 && y + height<=y2 )
						)
				)
		);
	}

	public boolean isSelected(mBasic player) {
		return intersect(player.x, player.y, player.getWidth(), player.getHeight());
	}
	
	public boolean isSelected() {
		return selected;
	}

	private void recalcParametrs(){
		switch (type) {
			case TYPE_BACKGROUNDSPRITE:
				height = y * mSettings.ScaleFactorY / SIZE_OF_DELTA_BACKGROUND + SIZE_OF_BACKGROUND;
				width = height * mSettings.ScaleFactorX / 4;
				break;
			default:
				height = y * mSettings.ScaleFactorY / SIZE_OF_DELTA_BARRIER + SIZE_OF_BARRIER;
				width = height * mSettings.ScaleFactorX / 4;
				break;
		}
	}

	@Override
	public void draw(Canvas c, Paint p)
	{
		recalcParametrs();
		if (type != TYPE_LIVE) {
			dst.set(- (int) width, -2 * (int) width, (int) width, 2 * (int) width);
		}

		dst.offset((int)x, (int)y);
		c.drawBitmap(bmp, src, dst, p);
	}
}
