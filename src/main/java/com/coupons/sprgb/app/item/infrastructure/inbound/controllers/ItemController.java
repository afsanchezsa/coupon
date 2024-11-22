package com.coupons.sprgb.app.item.infrastructure.inbound.controllers;

import com.coupons.sprgb.app.item.application.caseuse.ItemCaseUse;
import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.GetOptimalListDto;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import com.coupons.sprgb.app.item.domain.dto.ResponseOptimalListDto;
import com.coupons.sprgb.app.item.infrastructure.outbound.external.MLIFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemCaseUse itemCaseUse;
    @PostMapping("/item/coupon")
    public ResponseEntity<ResponseOptimalListDto>getOptimalItemList(@RequestBody GetOptimalListDto getOptimalListDto){
        ResponseOptimalListDto response= itemCaseUse.getOptimalList(getOptimalListDto.getItemIds(), getOptimalListDto.getAmount());
                if(response.getItemIds().isEmpty())
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseOptimalListDto> responseActuator(){
        return ResponseEntity.ok(null);
    }

}
