package com.terra.assignment.global.resData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResData<T> {
    private ResCode resCode;
    private String msg;
    private T data;
    private Boolean isSuccess;

    public static <T> ResData<T> of(ResCode resCode, String msg, T data) {

        return new ResData<>(resCode, msg, data, resCode.getCode().startsWith("S-"));

    }

    public static <T> ResData<T> of(ResCode resCode, String msg) {

        return new ResData<>(resCode, msg, null, resCode.getCode().startsWith("S-"));
    }


}
