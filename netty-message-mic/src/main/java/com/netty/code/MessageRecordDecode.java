package com.netty.code;

import com.netty.proto.Header;
import com.netty.proto.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;


import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

@Slf4j
public class MessageRecordDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception{
        log.info("====开始解码header=====");
        MessageRecord record=new MessageRecord();
        Header header=new Header();
        header.setSessionId(byteBuf.readLong());
        header.setType(byteBuf.readByte());
        record.setHeader(header);

        log.info("====开始解码body=====");
        if(byteBuf.readableBytes()>4){
            int length=byteBuf.readInt();
            header.setLength(length);
            byte[] contents=new byte[length];
            byteBuf.readBytes(contents,0,length);
            ByteArrayInputStream bis=new ByteArrayInputStream(contents);
            ObjectInputStream ois = new ObjectInputStream(bis);
            record.setBody(ois.readObject());
            list.add(record);
            log.info("序列化出来的结果："+record);
        }else{
            log.info("消息内容为空");
        }

    }
}
