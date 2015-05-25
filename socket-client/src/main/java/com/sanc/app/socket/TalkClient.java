package com.sanc.app.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TalkClient {

	public static void main(String[] args) {

		Socket socket = null;
		try {
			// 1. get socket
			// 向本机的 8001 端口发出客户请求
			socket = new Socket("127.0.0.1", 8001);
			System.out.println("socket created on IP 127.0.0.1, port : 8001");

			// 2. system input stream
			// 由系统标准输入设备构造 BufferedReader 对象
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("sin created");

			// 3. socket output/input stream
			// 由 Socket 对象得到输出流，并构造 PrintWriter 对象
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			// 由 Socket 对象得到输入流，并构造相应的 BufferedReader 对象
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("os and is created");

			String readLine;

			// 从系统标准输入读入一字符串
			readLine = sin.readLine();
			if (readLine != null && readLine.trim().length() > 0) {
				os.println(readLine);
				os.flush();
			}

			// 如果该字符串为 "bye"，则停止循环
			while (!readLine.equalsIgnoreCase("bye")) {

				// 将从系统标准输入读入的字符串输出到 Server
				os.println(readLine);
				// 刷新输出流，使Server马上收到该字符串
				os.flush();

				// 在系统标准输出上打印读入的字符串
				System.out.println("Clinet : " + readLine);
				// 从Server读入一字符串，并打印到标准输出上
				String serverLine = is.readLine();
				if (serverLine == null || serverLine.equalsIgnoreCase("bye")) {
					break;
				}
				System.out.println("Server : " + serverLine);

				// 从系统标准输入读入一字符串
				readLine = sin.readLine();
			}
			os.close();
			is.close();
			sin.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
