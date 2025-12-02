function renderIntegrationsPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <section class="integrations">
      <h2>🔗 Tích hợp hệ thống</h2>
      <p class="desc">Kết nối các dịch vụ bên ngoài để hỗ trợ cửa hàng hoạt động hiệu quả hơn.</p>

      <div class="integration-grid">
        <div class="integration-card active">
          <img src="../assets/img/momo.png" alt="MoMo">
          <h3>MoMo Payment</h3>
          <p>Cổng thanh toán điện tử phổ biến tại Việt Nam.</p>
          <button class="btn disconnect">Ngắt kết nối</button>
        </div>

        <div class="integration-card">
          <img src="../assets/img/vnpay.png" alt="VNPay">
          <h3>VNPay</h3>
          <p>Tích hợp thanh toán qua mã QR và thẻ ngân hàng.</p>
          <button class="btn connect">Kết nối</button>
        </div>

        <div class="integration-card active">
          <img src="../assets/img/ghn.png" alt="GHN">
          <h3>Giao Hàng Nhanh</h3>
          <p>Tự động tạo đơn vận chuyển sau khi đặt hàng.</p>
          <button class="btn disconnect">Ngắt kết nối</button>
        </div>

        <div class="integration-card">
          <img src="../assets/img/mailchimp.png" alt="Mailchimp">
          <h3>Mailchimp</h3>
          <p>Gửi email marketing tự động cho khách hàng thân thiết.</p>
          <button class="btn connect">Kết nối</button>
        </div>
      </div>
    </section>
  `;
}
