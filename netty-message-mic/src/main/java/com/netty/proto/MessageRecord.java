package com.netty.proto;

import lombok.Data;

@Data

public class MessageRecord {

    private Header header;
    private Object body;
}
