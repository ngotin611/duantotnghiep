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
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><img src="../assets/img/BanhMyXucXich.jpg" alt="Bánh mì xúc xích"></td>
              <td>Bánh mì xúc xích</td>
              <td>Bánh mặn</td>
              <td>15.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/BanhMyCha.jpeg" alt="Bánh mì chả"></td>
              <td>Bánh mì chả</td>
              <td>Bánh mặn</td>
              <td>12.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/BanhMyGa.jpeg" alt="Bánh mì gà"></td>
              <td>Bánh mì gà</td>
              <td>Bánh mặn</td>
              <td>25.000đ</td>
              <td><span class="status done">Cần nhập hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/BanhMyPate.jpeg" alt="Bánh mì que pate"></td>
              <td>Bánh mì que pate</td>
              <td>Bánh mặn</td>
              <td>10.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/BanhMyThitNuong.jpeg" alt="Bánh mì thịt nướng"></td>
              <td>Bánh mì thịt nướng</td>
              <td>Bánh mặn</td>
              <td>20.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/BanhMyTrung.jpeg" alt="Bánh mì trứng"></td>
              <td>Bánh mì trứng</td>
              <td>Bánh mặn</td>
              <td>22.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/HamburgerBo.jpg" alt="Hamburger bò"></td>
              <td>Hamburger bò</td>
              <td>Bánh mặn</td>
              <td>35.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/HamburgerCa.jpg" alt="Hamburger cá"></td>
              <td>Hamburger cá</td>
              <td>Bánh mặn</td>
              <td>45.000đ</td>
              <td><span class="status done">Hết hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/HamburgerGa.jpg" alt="Hamburger gà"></td>
              <td>Hamburger gà</td>
              <td>Bánh mặn</td>
              <td>30.000đ</td>
              <td><span class="status done">Cần nhập hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/HamburgerPhoMai.jpg" alt="Hamburger phô mai"></td>
              <td>Hamburger phô mai</td>
              <td>Bánh mặn</td>
              <td>40.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/PizzaBo.jpg" alt="Pizza bò"></td>
              <td>Pizza bò</td>
              <td>Bánh mặn</td>
              <td>120.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/PizzaGa.png" alt="Pizza gà"></td>
              <td>Pizza gà</td>
              <td>Bánh mặn</td>
              <td>100.000đ</td>
              <td><span class="status done">Cần nhập hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/PizzaHaiSan.jpg" alt="Pizza hải sản"></td>
              <td>Pizza hải sản</td>
              <td>Bánh mặn</td>
              <td>150.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td><img src="../assets/img/PizzaPhoMai.jpg" alt="Pizza phô mai"></td>
              <td>Pizza phô mai</td>
              <td>Bánh mặn</td>
              <td>130.000đ</td>
              <td><span class="status done">Còn hàng</span></td>
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
