package cn.share.system.coom;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/18.
 * <p>
 * <p>
 * 将webview变成长图
 */

public class JianshuWebViewActivity extends AppCompatActivity {

    public static final String TAG = "gear2";
    private WebView mWvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }

        setContentView(R.layout.activity_jianshu_webview_layout);

        mWvContent = (WebView) findViewById(R.id.id_jianshu_webview);
        mWvContent.getSettings().setJavaScriptEnabled(true);
        mWvContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWvContent.getSettings().setDomStorageEnabled(false);
        mWvContent.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 自适应屏幕
        mWvContent.getSettings().setUseWideViewPort(true);
        mWvContent.getSettings().setLoadWithOverviewMode(true);
        mWvContent.loadUrl("http://www.jianshu.com/p/5bc8f8b5d45d");
        registerForContextMenu(mWvContent);
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
                //能获取整个WebView的截图，但是WebView中未加载的图片，是不会截图的，所以，截取下来的只是里面的文字，
                //未显示的图片会展示空白
//                mWvContent.measure(View.MeasureSpec.makeMeasureSpec(
//                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                mWvContent.layout(0, 0, mWvContent.getMeasuredWidth(),
//                        mWvContent.getMeasuredHeight());
//                mWvContent.setDrawingCacheEnabled(true);
//                mWvContent.buildDrawingCache();
//                saveBitmap(captureWebView(mWvContent));

            }


        });

    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        final WebView.HitTestResult webViewHitTestResult = mWvContent.getHitTestResult();

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

            contextMenu.setHeaderTitle("网页中下载图片");

            contextMenu.add(0, 1, 0, "点击保存")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            String DownloadImageURL = webViewHitTestResult.getExtra();

                            if (URLUtil.isValidUrl(DownloadImageURL)) {

                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                                request.allowScanningByMediaScanner();
//                                request.setDestinationInExternalPublicDir("/img", "/baobao.jpg");
                                request.setDestinationInExternalFilesDir(JianshuWebViewActivity.this, "/img", "/baobao.png");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                downloadManager.enqueue(request);
                                Toast.makeText(JianshuWebViewActivity.this, "下载成功。。。", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(JianshuWebViewActivity.this, "下载失败。。。", Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
        }
    }

    private Bitmap captureWebView(final WebView webView) {
        int width = webView.getMeasuredWidth();
        int height = webView.getMeasuredHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);
//        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length).copy(Bitmap.Config.ARGB_8888, true);;
        Log.d(TAG, "未压缩的: ---->" + bitmap.getByteCount());
//        Tiny.getInstance().source(bitmap).asBitmap().compress(new BitmapCallback() {
//            @Override
//            public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
//                Log.d(TAG, "callback: ---->"+bitmap.getByteCount()/1024.0f/1024f/1024f);
//                Canvas canvas = new Canvas(bitmap);
//                webView.draw(canvas);
//            }
//        });

        return bitmap;
    }

//    private Bitmap captureWebView(WebView webView) {
//        Picture picture = webView.capturePicture();
//        int width = picture.getWidth();
//        int height = picture.getHeight();
//        if (width > 0 && height > 0) {
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//            Canvas canvas = new Canvas(bitmap);
//            picture.draw(canvas);
//            return bitmap;
//        }
//        return null;
//    }

    private void saveBitmap(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), "JianShu111.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
            Log.d(TAG, "saveBitmap: 保存成功。。。");
            Toast.makeText(this, "截图成功了", Toast.LENGTH_SHORT).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            Log.d(TAG, "saveBitmap: 内存泄漏了哦");
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
