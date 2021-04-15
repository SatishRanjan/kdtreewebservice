package finalproject5278;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		int port = 8080;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Http sever started on port:" + port);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client request received: " + clientSocket.toString());
			ClientSocketHandler clientHandler = new ClientSocketHandler(clientSocket);
			executor.submit(clientHandler);
		}
	}
}
