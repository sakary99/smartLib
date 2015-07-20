package com.webcash.sws.comm.picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.webcash.sws.comm.define.Msg;
import com.webcash.sws.comm.extras.Extras_Picture;
import com.webcash.sws.comm.ui.DlgAlert;
import com.webcash.sws.comm.util.Convert;
import com.webcash.sws.lib.R;

/**
 * 콜라보 Ver 2.0 부터 사용함.
 *
 */
public class PictureViewActivity extends Activity {
	
	public static final int ACTIVITY_REQ_CD = 32760;
		
	private DisplayImageOptions imageOptions;
	
	private TextView mTvPage;
	
	private ViewPager mViewPager;
	
	/*
	 * 사진 파라미터
	 */
	private Extras_Picture mExtrasPhoto;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
        	mExtrasPhoto = new Extras_Picture(this, getIntent());
	        
	        setContentView(R.layout.picture_view_activity);
        	
        	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
    		.threadPriority(Thread.NORM_PRIORITY - 2)
    		.denyCacheImageMultipleSizesInMemory()
    		.discCacheFileNameGenerator(new Md5FileNameGenerator())
    		.tasksProcessingOrder(QueueProcessingType.LIFO)
    		.build();

    		ImageLoader.getInstance().init(config);
        	
        	
        	imageOptions = new DisplayImageOptions.Builder()
			.cacheOnDisc(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.showImageOnFail(R.drawable.thumbnail_img_pic_fail)
			.showImageOnLoading(R.drawable.thumbnail_img_pic_nor)
			.showImageForEmptyUri(R.drawable.thumbnail_img_pic_nor)
			.build();
	        
	        
	        mTvPage = (TextView) findViewById(R.id.tv_Page);
	        
	        
	        mViewPager = (ViewPager) findViewById(R.id.view_pager);
	        ImagePagerAdapter adapter = new ImagePagerAdapter();
	        mViewPager.setAdapter(adapter);
	        
	        
	        if (mExtrasPhoto.Param.getPHOTO_CURRENTITEM() > 0 && mExtrasPhoto.Param.getPHOTO_CURRENTITEM() < mExtrasPhoto.Param.getPHOTOUUIDLIST().size()) {
	        	mViewPager.setCurrentItem(mExtrasPhoto.Param.getPHOTO_CURRENTITEM());
		        setPagerIndicator(mExtrasPhoto.Param.getPHOTO_CURRENTITEM() + 1);
	        } else {
	        	setPagerIndicator(1);
	        }	       
	        
	        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override public void onPageSelected(int position) {
					try {
						setPagerIndicator(position + 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override public void onPageScrolled(int arg0, float arg1, int arg2) { }				
				@Override public void onPageScrollStateChanged(int arg0) { }
			});
	        
	        // 창 닫기
	        findViewById(R.id.btn_Close).setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					finish();
				}
			});
	        
	        // 앨범에 저장 (이미지가 정상적으로 표현되면 보여준다.)
	        findViewById(R.id.btn_SaveToAlbum).setVisibility(View.INVISIBLE);
	        	
	        
        } catch (Exception e) {
			DlgAlert.Error(this, Msg.Exp.DEFAULT, e);
		}
	}
    
	
	protected void onDestroy() {
		super.onDestroy();
	};
	
	private void saveToAlbum(int position) {
		
		try {
			
			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
		
			
			
			String imageUrl = mExtrasPhoto.Param.getPHOTOUUIDLIST().get(position);
			String extention = imageUrl.substring(imageUrl.lastIndexOf(".")+1,imageUrl.length());
			final String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + String.valueOf( Convert.ComDate.getCurrentTime() ) + "." + extention;
		    File fileForImage = new File(filePath);
	
		    InputStream sourceStream;
		    File cachedImage = ImageLoader.getInstance().getDiscCache().get(imageUrl);
		    if (cachedImage != null && cachedImage.exists()) { // if image was cached by UIL
		        sourceStream = new FileInputStream(cachedImage);
		    } else { // otherwise - download image
		        ImageDownloader downloader = new BaseImageDownloader(this);
		        sourceStream = downloader.getStream(imageUrl, null);
		    }
	
		    if (sourceStream != null) {
		        try {
		            OutputStream targetStream = new FileOutputStream(fileForImage);
		            try {
		                IoUtils.copyStream(sourceStream, targetStream, null);
		            } finally {
		                targetStream.close();
		                Toast.makeText(this, "사진을 저장했습니다.", Toast.LENGTH_SHORT).show();
		                
		                
		                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
		            }
		        } finally {
		            sourceStream.close();
		        }
		    }
			
		} catch (Exception e) {
			DlgAlert.Error(this, Msg.Exp.DEFAULT, e);
		}
		
		
	}
	
	/**
	 * ViewPager 현재 선택된 페이지 이미지 
	 */
	private void setPagerIndicator(int position)  throws Exception {
		
		if (mTvPage != null) {
			mTvPage.setText(String.valueOf(position) + "/" + mExtrasPhoto.Param.getPHOTOUUIDLIST().size());			
		}
	}
	
	private class ImagePagerAdapter extends PagerAdapter {
		
		private ArrayList<String> mPHOTOUUIDLIST;
		
		private PhotoViewAttacher mAttacher;
	   
		public ImagePagerAdapter() {
			mPHOTOUUIDLIST = mExtrasPhoto.Param.getPHOTOUUIDLIST();
		}

	    @Override
	    public int getCount() {
	      return mPHOTOUUIDLIST.size();
	    }


	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	    	Context context = PictureViewActivity.this;
	    	
	    	final ImageView imageView = new ImageView(context);	
	    	int padding = 10;
	    	imageView.setPadding(padding, padding, padding, padding);
	    	imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	    	
	    	ImageLoader.getInstance().displayImage(mPHOTOUUIDLIST.get(position), imageView, imageOptions, new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String url, View view) {  
				}
				
				@Override
				public void onLoadingFailed(String url, View view, FailReason arg2) { 
					findViewById(R.id.btn_SaveToAlbum).setVisibility(View.INVISIBLE);
					findViewById(R.id.btn_SaveToAlbum).setOnClickListener(null);
				}
				
				@Override
				public void onLoadingComplete(String url, View view, Bitmap arg2) {	
					mAttacher = new PhotoViewAttacher(imageView);
			        // 앨범에 저장
					findViewById(R.id.btn_SaveToAlbum).setVisibility(View.VISIBLE);
		        	findViewById(R.id.btn_SaveToAlbum).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							int position = mViewPager.getCurrentItem();
							saveToAlbum(position);
						}
					});
				}
				
				@Override
				public void onLoadingCancelled(String url, View view) {	}
			});
	      
	    	((ViewPager) container).addView(imageView, 0);
	    	
	    	return imageView;
	    }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	    	((ViewPager) container).removeView((ImageView) object);
	    }

		@Override
		public boolean isViewFromObject(View view, Object object) {
			 return view == ((ImageView) object);
		}
	}
}
