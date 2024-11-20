package com.coupons.sprgb.app.item.application.service;

import java.util.List;
import java.util.Map;

public interface ItemService {
    List<String> calculate(Map<String,Float> items, Float amount);
}
