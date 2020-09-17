package simpleNote.A2JavaPlus.nettyInfo.nio;

public interface note {
/**
	NIO服务端程序详细分析：
	1、创建一个 ServerSocketChannel 和 Selector ，并将 ServerSocketChannel 注册到 Selector 上
	2、 selector 通过 select() 方法监听 channel 事件，当客户端连接时，selector 监听到连接事件， 获取到 ServerSocketChannel 注册时绑定的 selectionKey
	3、selectionKey 通过 channel() 方法可以获取绑定的 ServerSocketChannel
	4、ServerSocketChannel 通过 accept() 方法得到 SocketChannel
	5、将 SocketChannel 注册到 Selector 上，关心 read 事件
	6、注册后返回一个 SelectionKey, 会和该 SocketChannel 关联
	7、selector 继续通过 select() 方法监听事件，当客户端发送数据给服务端，selector 监听到read事件，获取到 SocketChannel 注册时绑定的 selectionKey
	8、selectionKey 通过 channel() 方法可以获取绑定的 socketChannel
	9、将 socketChannel 里的数据读取出来
	10、用 socketChannel 将服务端数据写回客户端
	总结：
	NIO模型的selector 就像一个大总管，负责监听各种IO事件，然后转交给后端线程去处理NIO相对于BIO非阻塞的体现就在，
	BIO的后端线程需要阻塞等待客户端写数据(比如read方法)，如果客户端不写数据线程就要阻塞，NIO把等待客户端操作的事情交给了大总管 selector，
	selector 负责轮询所有已注册的客户端，发现有事件发生了才转交给后端线程处理，后端线程不需要做任何阻塞等待，直接处理客户端事件的数据即可，
	处理完马上结束，或返回线程池供其他客户端事件继续使用。还有就是 channel 的读写是非阻塞的。Redis就是典型的NIO线程模型，
	selector收集所有连接的事件并且转交给后端线程，线程连续执行所有事件命令并将结果写回客户端
**/
}
