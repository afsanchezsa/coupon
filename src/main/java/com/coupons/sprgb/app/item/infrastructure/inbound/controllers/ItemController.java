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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemCaseUse itemCaseUse;
    @GetMapping("/coupon")
    public ResponseEntity<ResponseOptimalListDto>getOptimalItemList(@RequestBody GetOptimalListDto getOptimalListDto){
        ResponseOptimalListDto response= itemCaseUse.getOptimalList(getOptimalListDto.getItemIds(), getOptimalListDto.getAmount());
                if(response.getItemIds().isEmpty())
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }
}
