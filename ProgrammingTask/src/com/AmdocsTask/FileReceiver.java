/**************************************************************************************
 * 							Programming task
 **************************************************************************************
 *	 			Name 	 : FileReceiver.java
 *				Date     : 12/19/2018
 *	 			Location : com.AmdocsTask
 *	 			Author   : Gilad Sagi
 *	 			Purpose  : This class is implementing the server which will receive 
 *						   the file from SocketChannel  
 *				Routines : -getEndPoint         - retrieve the host and port from configuration file 
 *						   -getFileName         - retrieve the filename from the configuration file
 *						   -createSocketChannel - create the SocketChannel once a connection arrives
 *						   -readFile            - retrieve the file from the Socket
 *							
 *-------------------------------------------------------------------------------------
 **************************************************************************************
 */
package com.AmdocsTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class FileReceiver {
	static java.util.Properties confProp;
	static String confFile = "/Configurations.con";
	static String host;
	static String port;

	public static void main(String[] args) throws Exception {
		FileReceiver server = new FileReceiver();
		confProp = Configurations.confFile2Properties(System.getProperty("user.dir")+confFile);
		getEndPoint();
		SocketChannel socketChannel = server.createSocketChannel();
		server.readFile(socketChannel);
	}
	
	private static void getEndPoint() {
		host=confProp.getProperty("END_POINT_ADDRESS");
		port=confProp.getProperty("PORT");
	}
	
	private String getFileName() throws Exception {
		String fileLocation = confProp.getProperty("FILE_TO_SEND");
		String fileName = fileLocation.substring(fileLocation.lastIndexOf('\\') + 1);
		return fileName;
	}
	
	private SocketChannel createSocketChannel() throws IOException {  // Create the SocketChannel over TCP network socket using a ServerSocketChannel when a connection is arrived
		System.out.println("Waiting for a connection to be established...");
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.socket().bind(new InetSocketAddress(Integer.valueOf(port)));
		SocketChannel client = serverSocket.accept();
		System.out.println("Connection established successfully " + client.getRemoteAddress());
		return client;
	}

	private void readFile(SocketChannel socketChannel) throws Exception {
		System.out.println("Transfering the file...");
		String fileName = getFileName();
		String retrivedPath = confProp.getProperty("RETRIVED_PATH");
		Path path = Paths.get(retrivedPath+"\\"+fileName);
		FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE,
																	StandardOpenOption.TRUNCATE_EXISTING,
																	StandardOpenOption.WRITE));
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (socketChannel.read(buffer) > 0) {	// read in chunks of 1024 bytes and write them to the file using fileChannel until end of file
			buffer.flip(); 		// flip the buffer after reading will set the limit to the current position (usually 1024) to the current pos, and pos will set to 0
			fileChannel.write(buffer);
			buffer.clear();
		}
		fileChannel.close();
		System.out.println("The file was received successfully! " + path.toString());
		socketChannel.close();
		
	}



}
