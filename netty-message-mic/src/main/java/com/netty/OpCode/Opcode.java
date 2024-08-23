package com.netty.OpCode;

public enum Opcode {


        BUSI_REQ((byte)0),
        BUSI_RESP((byte)1),
        PING((byte)3),
        PONG((byte)4);

        private byte code;

        private Opcode(byte code) {
            this.code=code;
        }

        public byte code(){
            return this.code;
        }

}
