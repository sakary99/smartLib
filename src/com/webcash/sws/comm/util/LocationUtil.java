package com.webcash.sws.comm.util;

public class LocationUtil {
	
	public static int convertTo1E6Int(double coord)
	{
		if(coord == 0)
		{
			return 0;
		}
		else
			return (int)(coord * 1E6);
	}
	
	public static double convertToDouble(int coord)
	{
		if(coord == 0)
			return 0;
		else
			return coord / 1E6;
	}

}
