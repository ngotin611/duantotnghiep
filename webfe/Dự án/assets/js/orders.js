function renderOrdersPage() {
  const content = document.getElementById("content");

  content.innerHTML = `
    <section class="orders-page">
      <div class="orders-header">
        <h1>🍞 Quản lý đơn hàng</h1>
        <div class="orders-actions">
          <button class="btn-add">+ Thêm đơn hàng</button>
          <input type="text" placeholder="🔍 Tìm kiếm đơn hàng..." class="search-box">
        </div>
      </div>

      <div class="orders-summary">
        <div class="summary-card">
          <h3>Tổng đơn</h3>
          <p>42</p>
        </div>
        <div class="summary-card">
          <h3>Đang xử lý</h3>
          <p>8</p>
        </div>
        <div class="summary-card">
          <h3>Hoàn thành</h3>
          <p>34</p>
        </div>
      </div>

      <div class="orders-table-container">
        <table class="orders-table">
          <thead>
            <tr>
              <th>Mã đơn</th>
              <th>Khách hàng</th>
              <th>Ngày đặt</th>
              <th>Tổng tiền</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>DH001</td>
              <td>Nguyễn Văn A</td>
              <td>12/10/2025</td>
              <td>350.000đ</td>
              <td><span class="status done">Hoàn thành</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td>DH002</td>
              <td>Trần Thị B</td>
              <td>13/10/2025</td>
              <td>180.000đ</td>
              <td><span class="status pending">Đang xử lý</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td>DH002</td>
              <td>Trần Thị B</td>
              <td>13/10/2025</td>
              <td>180.000đ</td>
              <td><span class="status pending">Đang xử lý</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td>DH002</td>
              <td>Trần Thị B</td>
              <td>13/10/2025</td>
              <td>180.000đ</td>
              <td><span class="status pending">Đang xử lý</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td>DH002</td>
              <td>Trần Thị B</td>
              <td>13/10/2025</td>
              <td>180.000đ</td>
              <td><span class="status pending">Đang xử lý</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td>DH002</td>
              <td>Trần Thị B</td>
              <td>13/10/2025</td>
              <td>180.000đ</td>
              <td><span class="status pending">Đang xử lý</span></td>
              <td>
                <button class="btn-edit">Sửa</button>
                <button class="btn-delete">Xóa</button>
              </td>
            </tr>
            <tr>
              <td>DH002</td>
              <td>Trần Thị B</td>
              <td>13/10/2025</td>
              <td>180.000đ</td>
              <td><span class="status pending">Đang xử lý</span></td>
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
