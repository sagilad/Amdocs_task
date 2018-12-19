/**************************************************************************************
 * 							Programming task
 **************************************************************************************
 *	 			Name 	 : Configurations.java
 *	 			Location : com.AmdocsTask
 *	 			Author   : Gilad Sagi
 *	 			Purpose  : This class is implementing configuration file... 
 *-------------------------------------------------------------------------------------
 **************************************************************************************
 */

package com.AmdocsTask;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
	
	public static Properties confFile2Properties(String confFileName)
			throws Exception {
		InputStream confFile = new FileInputStream(confFileName);
		Properties confProp = new java.util.Properties();
		confProp.load(confFile);
		return confProp;
	}

}
