package com.coupons.sprgb.app.item.infrastructure.aop.logging;

import com.coupons.sprgb.app.item.domain.dto.GetOptimalListDto;
import com.coupons.sprgb.app.item.domain.dto.ResponseOptimalListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component 
@Aspect
@RequiredArgsConstructor
@Slf4j
public class LogginAspect {

    private final AnaliticsDdr.Builder ddr;


    @Pointcut("execution(* com.coupons.sprgb.app.item.infrastructure.inbound.controllers.ItemController.getOptimalItemList(..))")
    public void getOptimalItemList(){

    }
    @Before("getOptimalItemList() && args(getOptimalListDto)")
    public void beforeGetOptimalTestList(GetOptimalListDto getOptimalListDto){
        String traceId=String.valueOf(UUID.randomUUID());
        ddr.traceId(traceId);
        ddr.controllerMethod("getOptimalItem");

        if(getOptimalListDto==null){
            log.warn("RECEIVED NULL REQUEST IN /COUPON ENDPOINT traceId:{}",traceId);
        }else{
            log.info("RECEIVED REQUEST {} traceId:{}",getOptimalListDto.toString(),traceId);
            ddr.args(getOptimalListDto.toString());
        }

    }

    @AfterReturning(pointcut = "getOptimalItemList()",returning = "response")
    public void finishGetOptimalRequest(ResponseEntity<ResponseOptimalListDto>response){
        if(response!=null){
            ddr.statusResponse(response.getStatusCode()!=null?response.getStatusCode().value():-1);
        }

        if(response!=null && response.hasBody()){
            ddr.responseData(response.getBody()!=null?response.getBody().toString():"");

        }
        AnaliticsDdr ddrFinal=ddr.build();
        log.info("final Ddr: {}",ddrFinal);

    }


}
