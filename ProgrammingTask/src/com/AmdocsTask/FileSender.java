/**************************************************************************************
 * 							Programming task
 **************************************************************************************
 *	 			Name 	 : FileSender.java
 *				Date     : 12/19/2018
 *	 			Location : com.AmdocsTask
 *	 			Author   : Gilad Sagi
 *	 			Purpose  : This class is implementing the server which will send 
 *						   the file over SocketChannel
 *				Routines : -getEndPoint   - retrieve the host and port from configuration file
 *						   -createChannel - create the SocketChannel on the desired address
 *						   -sendFile      - write the file to the SocketChannel
 *-------------------------------------------------------------------------------------
 **************************************************************************************
 */

package com.AmdocsTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender {
	static java.util.Properties confProp;
	static String confFile = "/Configurations.con";
	static String host;
	static String port;

	public static void main(String[] args) throws Exception {
		FileSender client = new FileSender();
		getEndPoint();
		SocketChannel socketChannel = client.createChannel();
		client.sendFile(socketChannel);

	}
	
	private static void getEndPoint() throws Exception {
		confProp = Configurations.confFile2Properties(System.getProperty("user.dir") + confFile);
		host=confProp.getProperty("END_POINT_ADDRESS");
		port=confProp.getProperty("PORT");
	}
	
	private SocketChannel createChannel() throws IOException  {
		SocketChannel socketChannel = SocketChannel.open();
		SocketAddress socketAddress = new InetSocketAddress(host, Integer.valueOf(port)); //Create the SocketChannel with host and port parameters from configuration file
		try {
			socketChannel.connect(socketAddress);
		} catch (IOException e) {
			System.out.println("Connection refused, please verify that Receiver process is running first");
			e.printStackTrace();
		}
		return socketChannel;
	}

	private void sendFile(SocketChannel socketChannel) throws Exception {
		String FileURL = confProp.getProperty("FILE_TO_SEND");
		Path path = Paths.get(FileURL);
		FileChannel inChannel = FileChannel.open(path);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (inChannel.read(buffer)>0) { //write the file in the SocketChannel in chunks 
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		}
		socketChannel.close();
		
	}


}
