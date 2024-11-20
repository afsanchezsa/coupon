package com.coupons.sprgb.app.item.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
@Getter
public class GetOptimalListDto {
    @JsonProperty("item_ids")
    List<String> itemIds;
    @JsonProperty("amount")
    Float amount;
}
