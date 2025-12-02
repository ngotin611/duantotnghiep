function renderProductsPage() {
  const content = document.getElementById("content");

  content.innerHTML = `
    <section class="products-page">
      <div class="products-header">
        <h1>🍰 Quản lý sản phẩm</h1>
        <div class="products-actions">
          <button class="btn-add">+ Thêm sản phẩm</button>
          <input type="text" placeholder="🔍 Tìm kiếm sản phẩm..." class="search-box">
        </div>
      </div>

      <div class="products-table-container">
        <table class="products-table">
          <thead>
            <tr>
              <th>Hình ảnh</th>
              <th>Tên bánh</th>
              <th>Danh mục</th>
              <th>Giá bán</th>
              <th>Tồn kho</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><img src="../assets/images/bread1.jpg" alt="Bánh mì bơ tỏi"></td>
              <td>Bánh mì bơ tỏi</td>
              <td>Bánh mặn</td>
              <td>25.000đ</td>
              <td>24</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/images/cake1.jpg" alt="Bánh kem dâu"></td>
              <td>Bánh kem dâu</td>
              <td>Bánh ngọt</td>
              <td>120.000đ</td>
              <td>8</td>
              <td><span class="status pending">Sắp hết</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/images/cake1.jpg" alt="Bánh kem dâu"></td>
              <td>Bánh kem xoài</td>
              <td>Bánh ngọt</td>
              <td>110.000đ</td>
              <td>13</td>
              <td><span class="status pending">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/images/cake1.jpg" alt="Bánh kem dâu"></td>
              <td>Bánh valentine</td>
              <td>Bánh ngọt</td>
              <td>150.000đ</td>
              <td>0</td>
              <td><span class="status pending">Hết hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  `;
}
