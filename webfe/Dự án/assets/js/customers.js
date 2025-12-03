function renderCustomersPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <section class="customers">
      <h2>👥 Quản lý khách hàng</h2>

      <div class="actions">
        <button class="btn-add">+ Thêm khách hàng</button>
        <input type="text" placeholder="Tìm kiếm khách hàng..." />
      </div>

      <table class="table">
        <thead>
          <tr>
            <th>Mã KH</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Số điện thoại</th>
            <th>Địa chỉ</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>KH001</td>
            <td>Nguyễn Văn A</td>
            <td>vana@example.com</td>
            <td>0901234567</td>
            <td>123 Lê Lợi, TP.HCM</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH002</td>
            <td>Trần Thị B</td>
            <td>thib@example.com</td>
            <td>0934567890</td>
            <td>45 Nguyễn Huệ, TP.HCM</td>
            <td><span class="status warning">Tạm khóa</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Lê Văn C</td>
            <td>vanc@example.com</td>
            <td>0987654321</td>
            <td>89 Hai Bà Trưng, Hà Nội</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  `;
}
