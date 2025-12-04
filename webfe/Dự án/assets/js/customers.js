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
            <td>Nguyễn Văn Đồng Bằng</td>
            <td>vanbang@example.com</td>
            <td>0263485394</td>
            <td>123 Lê Lợi, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH002</td>
            <td>Phạm Quang Đạt</td>
            <td>dat01062004@example.com</td>
            <td>0359904840</td>
            <td>554 Điện Biên Phủ, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH003</td>
            <td>Nguyễn Văn Quang</td>
            <td>quang0862@example.com</td>
            <td>0862340391</td>
            <td>47/55 Huỳnh Ngọc Huệ, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH004</td>
            <td>Huỳnh Ngọc Vỹ</td>
            <td>vy2602@example.com</td>
            <td>0393983249</td>
            <td>108 Phạm Đình Hổ, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH005</td>
            <td>Nguyễn Thanh Thủy</td>
            <td>thuy2707@example.com</td>
            <td>0377476732</td>
            <td>210 Nguyễn Tất Thành, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH006</td>
            <td>Nguyễn Quốc Phú</td>
            <td>Phu2845@example.com</td>
            <td>074495838</td>
            <td>27 Mỹ An, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH007</td>
            <td>Phan Quốc Thắng</td>
            <td>qthang777@example.com</td>
            <td>0837492837</td>
            <td>09 Dũng Sĩ Thanh Khê, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH008</td>
            <td>Lê Nguyễn Bích Giao</td>
            <td>giao828@example.com</td>
            <td>0984374857</td>
            <td>104 Nguyễn Văn Linh, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>KH009</td>
            <td>Trần Thiện Hương</td>
            <td>huongtran2338@example.com</td>
            <td>0383749521</td>
            <td>54 Dương Bích Liên, Đà Nẵng</td>
            <td><span class="status success">Hoạt động</span></td>
            <td>
              <button class="btn-edit">Sửa</button>
              <button class="btn-delete">Xóa</button>
            </td>
          </tr>
          <tr>
            <td>...</td>
          </tr>
          <tr>
            <td>KH836</td>
            <td>Lê Tấn Huy</td>
            <td>huycris777@example.com</td>
            <td>0384475839</td>
            <td>748/12 Trần Cao Vân, Đà Nẵng</td>
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
