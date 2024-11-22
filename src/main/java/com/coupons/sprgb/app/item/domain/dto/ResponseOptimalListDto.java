package com.coupons.sprgb.app.item.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class ResponseOptimalListDto {
    @JsonProperty("item_ids")
    private List<String> itemIds;
    private Float total;

}
