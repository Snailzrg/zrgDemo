package simpleNote.A3JavaPlus.NettyInfo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞模型，一个客户端连接对应一个处理线程 
 * 缺点：
 * 1、IO代码里read操作是阻塞操作，如果连接不做数据读写操作会导致线程阻塞，浪费资源
 * 2、如果线程很多，会导致服务器线程太多，压力太大。
 * 应用场景：
 * BIO 方式适用于连接数目比较小且固定的架构， 这种方式对服务器资源要求比较高 但程序简单易理解
 * 
 * @author zhouruigang
 *
 */
public class BioServer {
	
	public static void main(String[] args) throws IOException {
	
		ServerSocket serverSocket = new ServerSocket(9000);
		
		while(true){
			System.out.println("正在等待连接....");
			Socket socket = serverSocket.accept();//阻塞
			System.out.println("有连接了");
			new Thread( new Runnable() {
				@Override
				public void run() {
					try {
						handler(socket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	
	private static void handler(Socket socket) throws IOException{
		System.out.println("thread id 线程id"+Thread.currentThread().getId());
		byte[] bytes = new byte[1024];
		System.out.println("准备read.......");
		//接收客户端的数据，阻塞方法，没有数据可读时就阻塞
		 int read = socket.getInputStream().read(bytes);
		 System.out.println("read 读取完毕");
		 if (read !=-1 ) {
			 System.out.println("接收到客户端的数据：" + new String(bytes, 0, read));
			 System.out.println("thread id = " + Thread.currentThread().getId());
		}
		 socket.getOutputStream().write("HelloClient".getBytes());
		 socket.getOutputStream().flush();
		 
	}
}
