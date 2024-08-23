package com.netty.code;


import com.netty.OpCode.Opcode;
import com.netty.proto.Header;
import com.netty.proto.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

public class CodesMainTest {
    public static void main( String[] args ) throws Exception{
        //EmbeddedChannel是netty专门改进针对ChannelHandler的单元测试而提供的
        EmbeddedChannel channel=new EmbeddedChannel(
                new LoggingHandler(),
                new MessageRecordEncoder(),
                new MessageRecordDecode());
        Header header=new Header();
        header.setSessionId(123456);
        header.setType(Opcode.PING.code());
        MessageRecord record=new MessageRecord();
        record.setHeader(header);
        record.setBody("Hello World");
        channel.writeOutbound(record);
        ByteBuf byteBuf= ByteBufAllocator.DEFAULT.buffer();
        new MessageRecordEncoder().encode(null,record,byteBuf);
        channel.writeInbound(byteBuf);


        //*********模拟半包和粘包问题************//
        //把一个包通过slice拆分成两个部分
        ByteBuf bb1=byteBuf.slice(0,7); //获取前面7个字节
        ByteBuf bb2=byteBuf.slice(7,byteBuf.readableBytes()-7); //获取后面的字节
        bb1.retain();

        channel.writeInbound(bb1);
        channel.writeInbound(bb2);
    }
}
