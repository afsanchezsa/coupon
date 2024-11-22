package com.coupons.sprgb.app.item.infrastructure.aop.logging;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder(builderClassName = "Builder")
public class AnaliticsDdr {
    private String controllerMethod;
    private String args;
    private Integer statusResponse;
    private String responseData;
    private String traceId;

}
