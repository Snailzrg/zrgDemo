package simpleNote.A2JavaPlus.nettyInfo.netty;

public interface NettyInfo {
	/*
	ByteBuf详解
	从结构上来说，ByteBuf 由一串字节数组构成。数组中每个字节用来存放信息。
	ByteBuf 提供了两个索引，一个用于读取数据，一个用于写入数据。这两个索引通过在字节数
	组中移动，来定位需要读或者写信息的位置。
	当从 ByteBuf 读取时，它的 readerIndex（读索引）将会根据读取的字节数递增。
	同样，当写 ByteBuf 时，它的 writerIndex 也会根据写入的字节数进行递增。
	*/
}
