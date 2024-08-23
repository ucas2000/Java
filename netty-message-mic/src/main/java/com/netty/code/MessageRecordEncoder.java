package com.netty.code;

import com.netty.proto.Header;
import com.netty.proto.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;


import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Slf4j
public class MessageRecordEncoder extends MessageToByteEncoder<MessageRecord> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageRecord record, ByteBuf byteBuf) throws Exception{
        log.info("===========开始编码Header部分===========");
        Header header=record.getHeader();
        byteBuf.writeLong(header.getSessionId());
        byteBuf.writeByte(header.getType());

        log.info("===========开始编码Body部分===========");
        Object body=record.getBody();
        if(body!=null){
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(bos);
            oos.writeObject(body);
            byte[] bytes=bos.toByteArray();
            byteBuf.writeInt(bytes.length); //写入消息体长度:占4个字节
            byteBuf.writeBytes(bytes); //写入消息体内容
        }else{
            byteBuf.writeInt(0);
        }
    }
}
