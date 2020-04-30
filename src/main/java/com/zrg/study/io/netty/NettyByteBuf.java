//package com.zrg.study.io.netty;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.util.CharsetUtil;
//
///**
// * ByteBuf详解 从结构上来说，ByteBuf 由一串字节数组构成。数组中每个字节用来存放信息。 ByteBuf
// * 提供了两个索引，一个用于读取数据，一个用于写入数据。这两个索引通过在字节数 组中移动，来定位需要读或者写信息的位置。 当从 ByteBuf 读取时，它的
// * readerIndex（读索引）将会根据读取的字节数递增。 同样，当写 ByteBuf 时，它的 writerIndex 也会根据写入的字节数进行递增。
// *
// * @author zhouruigang
// *
// */
//public class NettyByteBuf {
//
//	public static void main(String[] args) {
//		// 创建byteBuf对象，该对象内部包含一个字节数组byte[10]
//		// 通过readerindex和writerIndex和capacity，将buffer分成三个区域
//		// 已经读取的区域：[0,readerindex)
//		// 可读取的区域：[readerindex,writerIndex)
//		// 可写的区域: [writerIndex,capacity)
//		ByteBuf byteBuf = Unpooled.buffer(10);
//		System.out.println("byteBuf=" + byteBuf);
//
//		for (int i = 0; i < 8; i++) {
//			byteBuf.writeByte(i);
//		}
//		System.out.println("byteBuf-->" + byteBuf);
//
//		for (int i = 0; i < 5; i++) {
//			System.out.println(byteBuf.getByte(i));
//		}
//		System.out.println("byteBuf=" + byteBuf);
//
//		/*
//		 * 只有read()方法才改变byteBuffer下标
//		 *
//		 *
//		 *
//		 */
//
//		for (int i = 0; i < 5; i++) {
//			System.out.println(byteBuf.readByte());
//		}
//		System.out.println("byteBuf-->" + byteBuf);
//		// 用Unpooled工具类创建ByteBuf
//		ByteBuf byteBuf2 = Unpooled.copiedBuffer("hello,zhuge!", CharsetUtil.UTF_8);
//		// 使用相关的方法
//		if (byteBuf2.hasArray()) {
//			byte[] content = byteBuf2.array();
//			// 将 content 转成字符串
//			System.out.println(new String(content, CharsetUtil.UTF_8));
//			System.out.println("byteBuf=" + byteBuf2);
//			System.out.println(byteBuf2.readerIndex()); // 0
//
//			System.out.println(byteBuf2.writerIndex()); // 12
//			System.out.println(byteBuf2.capacity()); // 36
//			System.out.println(byteBuf2.getByte(0)); // 获取取数组0这个位置的字符h的ascii码，h=104
//
//			int len = byteBuf2.readableBytes(); //可读的字节数 12
//			 System.out.println("len=" + len);
//
//			//使用for取出各个字节
//			  for (int i = 0; i < len; i++) {
//			  System.out.println((char) byteBuf2.getByte(i));
//			  }
//			//范围读取
//			   System.out.println(byteBuf2.getCharSequence(0, 6, CharsetUtil.UTF_8));
//			   System.out.println(byteBuf2.getCharSequence(6, 6, CharsetUtil.UTF_8));
//		}
//	}
//}
