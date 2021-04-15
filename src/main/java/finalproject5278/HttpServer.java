package finalproject5278;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.io.*;

public class HttpServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		System.out.println("Starting simple web server");
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();		
		ServerSocket serverSocket = new ServerSocket(8080);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client request received: " + clientSocket.toString());
			ClientSocketHandler clientHandler = new ClientSocketHandler(clientSocket);
			executor.submit(clientHandler);
		}
	}
}
