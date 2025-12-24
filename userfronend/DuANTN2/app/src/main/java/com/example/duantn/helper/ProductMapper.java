package com.example.duantn.helper;

import com.example.duantn.models.FoodDomain;
import com.example.duantn.models.product.ResponseProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class để chuyển đổi giữa ResponseProduct (từ API) và FoodDomain (dùng trong app)
 */
public class ProductMapper {

    /**
     * Chuyển đổi ResponseProduct sang FoodDomain
     */
    public static FoodDomain toFoodDomain(ResponseProduct product) {
        if (product == null) {
            return null;
        }

        // Chuyển đổi price từ BigDecimal sang double
        double price = product.getPrice() != null ? product.getPrice().doubleValue() : 0.0;

        // Tạo FoodDomain với productId
        // Lưu ý: category sẽ được set sau nếu có thông tin từ Category API
        FoodDomain foodDomain = new FoodDomain(
                product.getId(), // productId từ backend
                product.getName() != null ? product.getName() : "",
                product.getImgProduct() != null ? product.getImgProduct() : "",
                product.getDescription() != null ? product.getDescription() : "",
                price,
                "" // category sẽ được set sau
        );

        return foodDomain;
    }

    /**
     * Chuyển đổi danh sách ResponseProduct sang danh sách FoodDomain
     */
    public static List<FoodDomain> toFoodDomainList(List<ResponseProduct> products) {
        List<FoodDomain> foodList = new ArrayList<>();
        if (products != null) {
            for (ResponseProduct product : products) {
                FoodDomain food = toFoodDomain(product);
                if (food != null) {
                    foodList.add(food);
                }
            }
        }
        return foodList;
    }

    /**
     * Chuyển đổi FoodDomain sang ResponseProduct (nếu cần)
     */
    public static ResponseProduct toResponseProduct(FoodDomain food) {
        if (food == null) {
            return null;
        }

        ResponseProduct product = new ResponseProduct();
        product.setName(food.getTitle());
        product.setImgProduct(food.getPic());
        product.setDescription(food.getDescription());
        product.setPrice(BigDecimal.valueOf(food.getFee()));

        return product;
    }
}

