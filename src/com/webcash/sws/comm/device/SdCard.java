package com.webcash.sws.comm.device;

import java.io.File;
import java.io.IOException;

import com.webcash.sws.comm.define.Msg;
import com.webcash.sws.comm.util.BizException;

public class SdCard {
	private static final String SDCARD_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
//	private static final String DB_BACKUP_PATH = "";
//	private static final String CSV_PATH = "";
	
	public static boolean exist() throws BizException {
		return exist(true);
	}

	public static boolean exist(boolean throwBizException) throws BizException {
		File sdcard = android.os.Environment.getExternalStorageDirectory();

		if(sdcard.canRead())
			return true;

		if(throwBizException)
			throw new BizException(Msg.Err.SdCard.SDCARD_NOT_EXIST);

		return false;
	}

	public static boolean existFile(String pathName, String fileName) throws BizException {
		return existFile(pathName, fileName, true);
	}
	
	public static boolean existFile(String pathName, String fileName, boolean throwBizException) throws BizException {
		String fn = SDCARD_PATH + pathName + "/" + fileName;

		File fp = new File(fn);

		if(fp.exists() || fp.isFile())
			return true;

		if(throwBizException)
			throw new BizException(Msg.Err.SdCard.FILE_NOT_EXIST);

		return false;
	}
	
	public static boolean existDirectory(String pathName) throws BizException {
		return existDirectory(pathName, true);
	}
	
	public static boolean existDirectory(String pathName, boolean throwBizException) throws BizException {
		String fn = SDCARD_PATH + "/" + pathName;

		File fp = new File(fn);

		if(fp.exists() || fp.isDirectory())
			return true;

		if(throwBizException)
			throw new BizException(Msg.Err.SdCard.DIRECTORY_NOT_EXIST);

		return false;
	}
	
	public static File openFile(String pathName, String fileName) throws BizException {
		return openFile(pathName, fileName, true);
	}
	
	public static File openFile(String pathName, String fileName, boolean throwBizException) throws BizException {
		String fn = SDCARD_PATH + pathName + "/" + fileName;

		File fp = new File(fn);

		if(fp.exists())
			return fp;

		if(throwBizException)
			throw new BizException(Msg.Err.SdCard.FILE_NOT_EXIST);

		return fp;
	}
	
	public static File openFileOrCreate(String pathName, String fileName, boolean throwBizException) throws BizException, IOException {
		File fp = openFile(pathName, fileName, false);
		
		if(fp.exists())
			return fp;
		
		if(existDirectory(pathName, false) == false)
			fp.getParentFile().mkdirs();
		
		if(existFile(pathName, fileName, false) == false)
			fp.createNewFile();
		
		return fp;
	}
	
	public static String fullPath(String pathName, String fileName) {
		String fn = SDCARD_PATH + pathName + "/" + fileName;
		return fn;
	}
}
