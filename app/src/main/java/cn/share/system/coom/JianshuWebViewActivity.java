package cn.share.system.coom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/18.
 *
 *
 * 将webview变成长图
 */

public class JianshuWebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_jianshu_webview_layout);
        final WebView mWvContent = (WebView) findViewById(R.id.id_jianshu_webview);
        mWvContent.getSettings().setJavaScriptEnabled(true);
        mWvContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWvContent.getSettings().setDomStorageEnabled(true);
        mWvContent.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 自适应屏幕
        mWvContent.getSettings().setUseWideViewPort(true);
        mWvContent.getSettings().setLoadWithOverviewMode(true);
        mWvContent.loadUrl("http://www.jianshu.com/p/5bc8f8b5d45d");
        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                saveImage(mWvContent);


                // do your stuff here
                mWvContent.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                mWvContent.layout(0, 0, mWvContent.getMeasuredWidth(),
                        mWvContent.getMeasuredHeight());
                mWvContent.setDrawingCacheEnabled(true);
                mWvContent.buildDrawingCache();
                Bitmap bm = Bitmap.createBitmap(mWvContent.getMeasuredWidth(),
                        mWvContent.getMeasuredHeight(), Bitmap.Config.RGB_565);

                Canvas bigcanvas = new Canvas(bm);
                Paint paint = new Paint();
                int iHeight = bm.getHeight();
                bigcanvas.drawBitmap(bm, 0, iHeight, paint);
                mWvContent.draw(bigcanvas);

                if (bm != null) {
                    try {
                        String path = Environment.getExternalStorageDirectory()
                                .toString();
                        OutputStream fOut = null;
                        File file = new File(path, "/jianshu111.png");
                        fOut = new FileOutputStream(file);

                        bm.compress(Bitmap.CompressFormat.PNG, 0, fOut);
                        fOut.flush();
                        fOut.close();
                        bm.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }



    /**
     * 保存图片
     *
     * @param webView
     */
    public void saveImage(WebView webView) {
        Picture picture = webView.capturePicture();
        Bitmap b = Bitmap.createBitmap(
                picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        picture.draw(c);
        File file = new File("/sdcard/" + "page.jpg");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsoluteFile());
            if (fos != null) {
                b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
    // 这里的 mWebView 就是 X5 内核的 WebView ，代码中的 longImage 就是最后生成的长图
//mWebView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED),
//            MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
//mWebView.layout(0,0,mWebView.getMeasuredWidth(),mWebView.getMeasuredHeight());
//mWebView.setDrawingCacheEnabled(true);
//mWebView.buildDrawingCache();
//    Bitmap longImage = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
//            mWebView.getMeasuredHeight() + endHeight, Bitmap.Config.ARGB_8888);
//    Canvas canvas = new Canvas(longImage);    // 画布的宽高和 WebView 的网页保持一致
//    Paint paint = new Paint();
//canvas.drawBitmap(longImage,0,mWebView.getMeasuredHeight(),paint);
//    float scale = getResources().getDisplayMetrics().density;
//    x5Bitmap =Bitmap.createBitmap(mWebView.getWidth(),mWebView.getHeight(),Bitmap.Config.ARGB_8888);
//    Canvas x5Canvas = new Canvas(x5Bitmap);
//x5Canvas.drawColor(ContextCompat.getColor(this,R.color.fragment_default_background));
//mWebView.getX5WebViewExtension().
//
//    snapshotWholePage(x5Canvas, false,false);  // 少了这行代码就无法正常生成长图
//
//    Matrix matrix = new Matrix();
//matrix.setScale(scale,scale);
//longCanvas.drawBitmap(x5Bitmap,matrix,paint);
}
