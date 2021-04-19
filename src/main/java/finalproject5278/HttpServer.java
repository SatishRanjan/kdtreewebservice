package finalproject5278;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		int dimensionCount = 3;
		if (args != null && args.length > 0) {
			for (String arg : args) {
				System.out.println("Args:" + arg);
			}

			if (Utils.isInt(args[0])) {
				dimensionCount = Integer.parseInt(args[0]);
			}
		}

		int port = 8080;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(port);
		KdStoreConfigManager.Configure(dimensionCount);
		System.out.println("K-d web sever started on port:" + port);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client request received: " + clientSocket.toString());
			ClientSocketHandler clientHandler = new ClientSocketHandler(clientSocket);
			executor.submit(clientHandler);
		}
	}
}
