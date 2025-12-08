function renderDiscountsPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <section class="discounts">
      <h2>🎁 Chương trình giảm giá</h2>

      <div class="discount-summary">
        <div class="discount-card highlight">
          <h3>Mã phổ biến</h3>
          <p>1 mã đang hoạt động</p>
        </div>
        <div class="discount-card">
          <h3>Tổng lượt sử dụng</h3>
          <p>210 lượt</p>
        </div>
        <div class="discount-card">
          <h3>Giảm tối đa</h3>
          <p>50,000đ</p>
        </div>
      </div>

      <table class="table">
        <thead>
          <tr>
            <th>Mã giảm giá</th>
            <th>Mô tả</th>
            <th>Phần trăm giảm</th>
            <th>Ngày hết hạn</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><strong>GIAM10</strong></td>
            <td>Giảm 10% cho đơn trên 50,000đ</td>
            <td>10%</td>
            <td>01/01/2026</td>
            <td><span class="status active">Chưa áp dụng</span></td>
          </tr>
          <tr>
            <td><strong>FREESHIP</strong></td>
            <td>Miễn phí giao hàng nội thành</td>
            <td>-</td>
            <td>01/01/2026</td>
            <td><span class="status active">Chưa áp dụng</span></td>
          </tr>
          <tr>
            <td><strong>VIP20</strong></td>
            <td>Giảm 20% cho khách VIP</td>
            <td>20%</td>
            <td>15/12/2025</td>
            <td><span class="status soon">Sắp hết hạn</span></td>
          </tr>
          <tr>
            <td><strong>NEW5</strong></td>
            <td>Giảm 5% cho khách hàng mới</td>
            <td>5%</td>
            <td>01/01/2026</td>
            <td><span class="status active">Chưa áp dụng</span></td>
          </tr>
        </tbody>
      </table>
    </section>
  `;
}
