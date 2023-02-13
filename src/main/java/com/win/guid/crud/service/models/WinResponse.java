package com.win.guid.crud.service.models;

import lombok.Builder;
import lombok.Data;
import lombok.With;

/**
 * Common response class for controllers
 */
@With
@Builder
@Data
public class WinResponse {

    private Object meta;
    private Object data;

    public static WinResponse fromData(Object data) {
        return WinResponse.builder().data(data).build();
    }

    public static WinResponse fromMeta(Object meta) {
        return WinResponse.builder().meta(meta).build();
    }

    public static WinResponse fromMetaAndData(Object meta, Object data) {
        return WinResponse.builder().meta(meta).data(data).build();
    }

}
