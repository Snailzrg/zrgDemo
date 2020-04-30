//package com.zrg.study.io.netty;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPromise;
//import io.netty.channel.EventLoop;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.util.concurrent.EventExecutor;
//
///**
// * Netty客户端
// *
// * @author zhouruigang
// *
// */
//public class NettyClient {
//
//	public static void main(String[] args) {
//		// 客户端需要一个事件循环组
//		EventLoopGroup loopGroup = new NioEventLoopGroup();
//		try {
//			// 创建客户端启动对象  注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
//			Bootstrap bootstrap = new Bootstrap();
//			// 设置相关参数
//			bootstrap.group(loopGroup) // 设置线程组
//			.channel(NioSocketChannel.class)// 使用 NioSocketChannel 作为客户端的通道实现
//			.handler( new ChannelInitializer<SocketChannel>() {
//				@Override
//				protected void initChannel(SocketChannel ch) throws Exception {
//					//加入处理器  基本逻辑实现在处理器 xxxHandler处理
//					ch.pipeline().addLast(new NettyClientHandler());
//				}
//			})
//			;
//			System.out.println("netty client start");
//			//启动客户端去连接服务器端
//			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9652).sync();
//			//对关闭通道进行监听
//			 channelFuture.channel().closeFuture().sync();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}finally {
//			loopGroup.shutdownGracefully();
//		}
//
//	}
//}
