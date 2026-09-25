package com.fooddelivery.factory;

import com.fooddelivery.repository.FoodDeliveryRepository;
import com.fooddelivery.service.FoodDeliveryService;
import com.fooddelivery.controller.FoodDeliveryController;

public class FoodDeliveryFactory {
    public static FoodDeliveryController getController() {
    return FoodDeliveryController.getInstance();
}

    public static FoodDeliveryService getService() {
        return FoodDeliveryService.getInstance();
    }

    public static FoodDeliveryRepository getRepository() {
        return FoodDeliveryRepository.getInstance();
    }
}