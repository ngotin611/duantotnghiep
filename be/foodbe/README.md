# FoodBe - Food Shop App

**FoodBe** là một ứng dụng quản lý thực phẩm cho phép người dùng tìm kiếm, lưu trữ thực phẩm và tạo các thực đơn dinh dưỡng cho các bữa ăn hàng ngày. Ứng dụng sử dụng **Spring Boot**, **Spring Security**, và **JWT** cho bảo mật.

## Mục Lục
1. [Công Nghệ Sử Dụng](#công-nghệ-sử-dụng)
2. [Cài Đặt và Chạy Ứng Dụng](#cài-đặt-và-chạy-ứng-dụng)
3. [Cấu Hình Môi Trường](#cấu-hình-môi-trường)
4. [Bảo Mật với JWT](#bảo-mật-với-jwt)
5. [API Endpoints](#api-endpoints)
6. [Hướng Dẫn Sử Dụng](#hướng-dẫn-sử-dụng)
7. [Các Yêu Cầu Hệ Thống](#các-yêu-cầu-hệ-thống)
8. [Giới Thiệu Dự Án](#giới-thiệu-dự-án)

## Công Nghệ Sử Dụng
- **Spring Boot**: Framework chính cho phát triển backend.
- **Spring Security**: Cung cấp bảo mật cho ứng dụng.
- **JWT (JSON Web Token)**: Cơ chế bảo mật để xác thực người dùng.
- **MySQL**: Cơ sở dữ liệu lưu trữ thông tin người dùng và thực phẩm.
- **Maven**: Công cụ quản lý và xây dựng dự án.
- **Lombok**: Giúp giảm boilerplate code.
- **H2 Database**: Cơ sở dữ liệu nhẹ để phục vụ trong quá trình phát triển (có thể thay bằng MySQL trong môi trường sản xuất).

## Cài Đặt và Chạy Ứng Dụng

### Bước 1: Clone Dự Án
Clone repository về máy:
```bash
git clone https://github.com/username/foodbe.git
....