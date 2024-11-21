package com.coupons.sprgb.app.item.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class ItemDto {
    private String id;
    private Float price;
}
