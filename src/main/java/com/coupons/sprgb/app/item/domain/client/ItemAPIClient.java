package com.coupons.sprgb.app.item.domain.client;

import com.coupons.sprgb.app.item.domain.dto.ItemDto;

import java.util.Optional;

public interface ItemAPIClient {
    Optional<ItemDto> getItemData(String itemId);
}
