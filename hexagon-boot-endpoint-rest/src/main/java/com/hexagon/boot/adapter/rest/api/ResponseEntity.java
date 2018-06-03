package com.hexagon.boot.adapter.rest.api;

import com.hexagon.boot.Code;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseEntity<T> {

    private Integer code;
    private String message;
    private Long timestamp;
    private T data;

    public ResponseEntity(){
        this.code = 200;
        this.message = "ok";
        this.timestamp = System.currentTimeMillis();
    }
    public ResponseEntity(Code code) {
        this.code = code.val();
        this.message = code.desc();
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseEntity(T d) {
        this();
        data = d;
    }


}
