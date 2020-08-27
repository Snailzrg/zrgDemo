package simpleNote.A3JavaPlus.NettyInfo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 同步非阻塞 服务器实现模式为一个线程可以处理多个请求(连接)，客户端发送的连接请求都会注册到多路复用器selector上，多路复用器
 * 轮询到连接有IO请求就进行处理。 I/O多路复用底层一般用的Linux API（select，poll，epoll）来实现，他们的区别如下表：
 *
 * NIO 有三大核心组件： Channel(通道)， Buffer(缓冲区)，Selector(选择器) 1、channel 类似于流，每个 channel
 * 对应一个 buffer缓冲区，buffer 底层就是个数组 2、channel 会注册到 selector 上，由 selector 根据 channel
 * 读写事件的发生将其交由某个空闲的线程处理 3、selector 可以对应一个或多个线程 4、NIO 的 Buffer 和 channel
 * 都是既可以读也可以写
 * 
 * @author zhouruigang
 *
 */
public class NioServer {

	public static void main(String[] args) throws IOException {
		// 创建一个在本地端口进行监听的服务Socket通道.并设置为非阻塞方式
		ServerSocketChannel ssc = ServerSocketChannel.open();
		// 必须配置为非阻塞才能往selector上注册，否则会报错，selector模式本身就是非阻塞模式
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(9000));
		// 创建一个选择器并将serverSocketChannel注册到它上面
		Selector selector = Selector.open();
		// 把channel注册到selector上，并且selector对客户端accept连接操作感兴趣
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while (true) {
			System.out.println("正在等待连接....等待事件发生");
			// 轮询监听key，select是阻塞的，accept()也是阻塞的
			selector.select();
			System.out.println("有事件发生了。。");
			// 有客户端请求，被轮询监听到
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				// 删除本次已处理的key，防止下次select重复处理
				it.remove();
				handle(key);
			}
		}
	}

	
	
	private static void handle(SelectionKey key) throws IOException{
		if(key.isAcceptable()){
			System.out.println("此时是连接...有客户端连接事件发生了...");
			ServerSocketChannel serverSocketChannel =(ServerSocketChannel) key.channel();
			//此处accept方法是阻塞的，但是这里因为是发生了连接事件，所以这个方法会马上执行完
			SocketChannel sc = serverSocketChannel.accept();
			sc.configureBlocking(false);
			//通过Selector监听Channel时对读事件感兴趣
			sc.register(key.selector(), SelectionKey.OP_READ);
		} else if (key.isReadable()) {
			System.out.println("有客户端数据可读事件发生了。。");
			SocketChannel sc = (SocketChannel) key.channel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.clear();
			int len = sc.read(buffer);
			if (len != -1) {
				System.out.println("读取到客户端发送的数据：" + new String(buffer.array(), 0, len));
				 }
			ByteBuffer byteBufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
			sc.write(byteBufferToWrite);
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			sc.close();
		}
	}

}
