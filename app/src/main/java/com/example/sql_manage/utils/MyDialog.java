package com.example.sql_manage.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sql_manage.R;

/**
 * @ClassName MyDialog
 * @Description 图片弹窗
 * @Version 1.0
 */
public class MyDialog extends Dialog {

    private GestureDetector mGestureDetector;
    private Bitmap bm, bmTemp;

    private static final float SMALL_SCALE = 0.8f;
    private static final float BIG_SCALE = 1.25f;
    private int startX = 0, startY = 0;
    private int imageWidth, imageHeight;
    private float scaleWidth = 1, scaleHeight = 1;
    private int displayWidth, displayHeight;
    private ImageView imageView;
    private TextView textView;


    public MyDialog(Context context, Bitmap bm) {
        super(context, R.style.dialog);
        this.bm = bm;
        this.bmTemp = bm;
        this.mGestureDetector = new GestureDetector(context, new ViewGestureListener());
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);
        imageView = (ImageView) findViewById(R.id.myImageView);
        textView = findViewById(R.id.textView2);
        init();
        writeImage();
    }
    private void init() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        displayWidth = dm.widthPixels;
        displayHeight = dm.heightPixels;
        imageWidth = bm.getWidth();
        imageHeight = bm.getHeight();
    }
    private void writeImage() {
        int w = (w = bmTemp.getWidth()) > displayWidth ? displayWidth : w;
        int h = (h = bmTemp.getHeight()) > displayHeight ? displayHeight : h;
        if (startX + w <= bmTemp.getWidth() && startY + h <= bmTemp.getHeight()) {
            Bitmap bmOrg = Bitmap.createBitmap(bmTemp, startX, startY, w, h);
            imageView.setImageDrawable(new BitmapDrawable(bmOrg));
        }
    }
    private void buttonShow() {
        MyDialog.this.dismiss();
    }
//    private void bindListener() {
//        Button.OnClickListener imageButtonListener = new Button.OnClickListener() {
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.image_close:
//                        MyDialog.this.dismiss();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        imageClose.setOnClickListener(imageButtonListener);
//    }
    public boolean onTouchEvent(MotionEvent event) {
        buttonShow();
        return mGestureDetector.onTouchEvent(event);
    }




    class ViewGestureListener implements GestureDetector.OnGestureListener {
        public boolean onDown(MotionEvent e) {
            return false;
        }
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
        public void onLongPress(MotionEvent e) {
        }
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            startX = (startX + distanceX + displayWidth) > bmTemp.getWidth() ? startX : (int) (startX + distanceX);
            startY = (startY + distanceY + displayHeight) > bmTemp.getHeight() ? startY : (int) (startY + distanceY);
            startX = startX <= 0 ? 0 : startX;
            startY = startY <= 0 ? 0 : startY;
            writeImage();
            return false;
        }
        public void onShowPress(MotionEvent e) {
        }
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
    }

}
