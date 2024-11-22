package com.coupons.sprgb.app.item.application.caseuse;

import com.coupons.sprgb.app.item.domain.dto.ResponseOptimalListDto;

import java.util.List;
import java.util.Map;

public interface ItemCaseUse {
    public ResponseOptimalListDto getOptimalList(List<String> itemIds, Float amount);

}
