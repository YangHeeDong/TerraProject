package com.terra.assignment.global.resData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResData<T> {
    private ResCode rsCode;
    private String msg;
    private T data;
    private Boolean isSuccess;

    public static <T> ResData<T> of(ResCode rsCode, String msg, T data) {

        return new ResData<>(rsCode, msg, data, rsCode.getCode().startsWith("S-"));

    }

    public static <T> ResData<T> of(ResCode rsCode, String msg) {

        return new ResData<>(rsCode, msg, null, rsCode.getCode().startsWith("S-"));
    }


}
