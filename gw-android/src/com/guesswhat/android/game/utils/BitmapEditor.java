package com.guesswhat.android.game.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

public class BitmapEditor {
	
	 static Canvas canvas;	 
	 static Rect rect;
	 static RectF rectF;	 
	 static float roundPx;
	 static Paint paint;
	 static int color;
	 static Bitmap output;
	 static Bitmap curImage;

	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        canvas = new Canvas(output);
        
        color = 0xff424242;
        paint = new Paint();
        rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rectF = new RectF(rect);
        roundPx = pixels;
        
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        curImage = output;

        return output;
    }
	 
//	 public static Bitmap setRedBodersBitmap() {
//		if(curImage!=null){
//			
//			paint.setColor(Color.BLACK);
//	        paint.setStyle(Paint.Style.STROKE);
//	        paint.setStrokeWidth(1.5f);
//	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
//	        rect = new Rect(0, 0, curImage.getWidth(), curImage.getHeight());
//	        rectF = new RectF(rect);
//	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//		}
//		return output;
//	 }
	 
	 public static Bitmap getDefaultBitmap() {
		return curImage;
	 }
		
}
