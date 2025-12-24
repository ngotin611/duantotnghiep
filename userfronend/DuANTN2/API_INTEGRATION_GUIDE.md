# Hướng dẫn tích hợp API với Spring Boot Backend

## Tổng quan

Dự án đã được tích hợp đầy đủ với Spring Boot backend thông qua Retrofit. Tất cả các API endpoints đã được định nghĩa và sẵn sàng sử dụng.

## Cấu hình

### 1. Cấu hình BASE_URL

File: `app/src/main/java/com/example/duantn/retrofit/RetrofitClient.java`

Thay đổi `BASE_URL` theo môi trường:

```java
// Emulator Android
private static final String BASE_URL = "http://10.0.2.2:8080";

// Device thật (thay [IP_MÁY_TÍNH] bằng IP thực tế)
private static final String BASE_URL = "http://192.168.1.100:8080";

// Server production
private static final String BASE_URL = "https://your-domain.com";
```

**Lưu ý:** 
- Đảm bảo Spring Boot backend đang chạy trên port 8080
- Nếu dùng device thật, đảm bảo Android device và máy tính cùng mạng WiFi
- Kiểm tra firewall không chặn port 8080

### 2. Permissions

File `AndroidManifest.xml` đã được cấu hình với:
- `INTERNET` permission
- `ACCESS_NETWORK_STATE` permission

## Các API đã tích hợp

### 1. ProductApi

**File:** `app/src/main/java/com/example/duantn/interface_api/ProductApi.java`

**Endpoints:**
- `GET /api/products` - Lấy danh sách sản phẩm có phân trang
- `GET /api/products/all` - Lấy tất cả sản phẩm
- `GET /api/products/{id}` - Lấy sản phẩm theo ID
- `GET /api/products/category/{categoryId}` - Lấy sản phẩm theo category
- `GET /api/products/search?name={name}` - Tìm kiếm sản phẩm
- `POST /api/products` - Tạo sản phẩm mới
- `PUT /api/products/{id}` - Cập nhật sản phẩm
- `DELETE /api/products/{id}` - Xóa sản phẩm

**Sử dụng:**
```java
ProductApi productApi = RetrofitClient.getRetrofitInstance().create(ProductApi.class);
Call<List<ResponseProduct>> call = productApi.getAllProducts();
call.enqueue(new Callback<List<ResponseProduct>>() {
    @Override
    public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
        if (response.isSuccessful()) {
            List<ResponseProduct> products = response.body();
            // Xử lý dữ liệu
        }
    }
    
    @Override
    public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
        // Xử lý lỗi
    }
});
```

### 2. CategoryApi

**File:** `app/src/main/java/com/example/duantn/interface_api/CategoryApi.java`

**Endpoints:**
- `GET /api/categories` - Lấy tất cả danh mục
- `GET /api/categories/{id}` - Lấy danh mục theo ID

### 3. OrderApi

**File:** `app/src/main/java/com/example/duantn/interface_api/OrderApi.java`

**Endpoints:**
- `POST /api/orders` - Tạo đơn hàng mới
- `GET /api/orders/{id}` - Lấy đơn hàng theo ID
- `GET /api/orders/orderId/{orderId}` - Lấy đơn hàng theo mã đơn
- `GET /api/orders` - Lấy tất cả đơn hàng (phân trang)
- `GET /api/orders/table/{tableNumber}` - Lấy đơn hàng theo bàn
- `GET /api/orders/status/{status}` - Lấy đơn hàng theo trạng thái
- `GET /api/orders/my-orders` - Lấy đơn hàng của người dùng

## Models

### Product Models
- `ResponseProduct` - Sản phẩm từ backend
- `CreateProduct` - Tạo sản phẩm mới
- `UpdateProduct` - Cập nhật sản phẩm

### Order Models
- `CreateOrderRequest` - Request tạo đơn hàng
- `ResponseOrder` - Response đơn hàng từ backend
- `OrderItemRequest` - Item trong đơn hàng (request)
- `OrderItemResponse` - Item trong đơn hàng (response)

### Other Models
- `CategoryDomain` - Danh mục
- `PageResponse<T>` - Response phân trang

## Helper Classes

### 1. ProductMapper

**File:** `app/src/main/java/com/example/duantn/helper/ProductMapper.java`

Chuyển đổi giữa `ResponseProduct` (API) và `FoodDomain` (local):

```java
// Chuyển đổi single product
FoodDomain food = ProductMapper.toFoodDomain(responseProduct);

// Chuyển đổi list products
List<FoodDomain> foods = ProductMapper.toFoodDomainList(responseProducts);
```

### 2. OrderMapper

**File:** `app/src/main/java/com/example/duantn/helper/OrderMapper.java`

Chuyển đổi giữa `OrderDomain` (local) và `CreateOrderRequest`/`ResponseOrder` (API):

```java
// Tạo request từ OrderDomain và cart items
CreateOrderRequest request = OrderMapper.toCreateOrderRequest(orderDomain, cartItems);

// Chuyển đổi response về OrderDomain
OrderDomain order = OrderMapper.toOrderDomain(responseOrder);
```

## Tích hợp trong Activities

### MainActivity

Đã tích hợp load products từ API:
- Load categories trước
- Load products và map sang FoodDomain
- Hiển thị loading indicator
- Fallback về dữ liệu mẫu nếu API thất bại

### PaymentActivity

Đã tích hợp gửi order lên backend:
- Tạo order request từ cart items
- Gửi lên backend khi thanh toán
- Lưu local nếu backend thất bại (fallback)

## Debugging

### Logging Interceptor

RetrofitClient đã được cấu hình với `HttpLoggingInterceptor` để log tất cả API calls:

```java
HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
```

Xem logs trong Logcat với tag: `OkHttp`

### Common Issues

1. **Connection refused**
   - Kiểm tra Spring Boot backend đang chạy
   - Kiểm tra BASE_URL đúng
   - Kiểm tra firewall/antivirus

2. **Timeout**
   - Tăng timeout trong RetrofitClient nếu cần
   - Kiểm tra kết nối mạng

3. **404 Not Found**
   - Kiểm tra endpoint path đúng với backend
   - Kiểm tra base URL có `/api` prefix

4. **400 Bad Request**
   - Kiểm tra request body format
   - Kiểm tra required fields

## Testing

### Test với Postman

Trước khi test trên Android, nên test API với Postman để đảm bảo backend hoạt động đúng.

### Test trên Emulator

1. Start Spring Boot backend
2. Chạy app trên emulator
3. Xem logs trong Logcat để debug

### Test trên Device thật

1. Đảm bảo device và máy tính cùng WiFi
2. Tìm IP máy tính: `ipconfig` (Windows) hoặc `ifconfig` (Mac/Linux)
3. Cập nhật BASE_URL với IP đó
4. Đảm bảo firewall cho phép kết nối

## Next Steps

1. Thêm authentication nếu cần (JWT token)
2. Thêm error handling tốt hơn
3. Thêm caching nếu cần
4. Thêm retry logic cho failed requests
5. Thêm pagination UI nếu cần

## Notes

- Tất cả API calls đều là asynchronous (sử dụng `enqueue`)
- Có fallback mechanism nếu API thất bại
- Logging được bật để dễ debug
- Timeout được set 30 giây

