package com.terra.assignment.global.resData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Custom API response")
public class ResData<T> {

    @Schema(description = "Response code")
    private ResCode resCode;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Response data")
    private T data;

    @Schema(description = "Success flag")
    private Boolean isSuccess;

    public static <T> ResData<T> of(ResCode resCode, String msg, T data) {

        return new ResData<>(resCode, msg, data, resCode.getCode().startsWith("S-"));

    }

    public static <T> ResData<T> of(ResCode resCode, String msg) {

        return new ResData<>(resCode, msg, null, resCode.getCode().startsWith("S-"));
    }


}
