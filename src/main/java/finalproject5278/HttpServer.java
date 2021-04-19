package finalproject5278;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		int dimensionCount = 3;
		int webServerPort = 8080;
		if (args != null && args.length > 0) {
			for (String arg : args) {
				System.out.println("Args:" + arg);
			}

			String[] argsSplit = args[0].split(",");
			if (Utils.isInt(argsSplit[0])) {
				dimensionCount = Integer.parseInt(argsSplit[0]);
			}

			if (argsSplit.length > 1 && Utils.isInt(argsSplit[1])) {
				webServerPort = Integer.parseInt(argsSplit[1]);
			}
		}

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(webServerPort);
		KdStoreConfigManager.Configure(dimensionCount);
		System.out.println("K-d web sever started on port:" + webServerPort);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client request received: " + clientSocket.toString());
			ClientSocketHandler clientHandler = new ClientSocketHandler(clientSocket);
			executor.submit(clientHandler);
		}
	}
}
