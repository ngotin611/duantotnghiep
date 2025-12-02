function renderHelpPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <section class="help-section">
      <h2>❓ Trung tâm trợ giúp</h2>
      <p class="desc">Nếu bạn gặp sự cố hoặc cần hỗ trợ, hãy xem hướng dẫn hoặc liên hệ với đội ngũ kỹ thuật.</p>

      <div class="help-grid">
        <div class="help-card">
          <h3>📘 Hướng dẫn sử dụng</h3>
          <p>Xem chi tiết cách quản lý đơn hàng, thêm sản phẩm, thống kê doanh thu...</p>
          <button class="btn">Xem hướng dẫn</button>
        </div>

        <div class="help-card">
          <h3>💬 Gửi phản hồi</h3>
          <p>Cho chúng tôi biết ý kiến của bạn để cải thiện hệ thống ngày càng tốt hơn.</p>
          <button class="btn">Gửi phản hồi</button>
        </div>

        <div class="help-card">
          <h3>📞 Liên hệ kỹ thuật</h3>
          <p>Bạn có thể gửi email hoặc liên hệ hotline khi cần hỗ trợ khẩn cấp.</p>
          <button class="btn">Liên hệ ngay</button>
        </div>
      </div>

      <div class="faq-section">
        <h3>🧠 Câu hỏi thường gặp</h3>
        <ul>
          <li><strong>1. Làm sao để thêm sản phẩm mới?</strong> → Vào menu "Products" và chọn “+ Thêm sản phẩm”.</li>
          <li><strong>2. Tôi quên mật khẩu đăng nhập?</strong> → Chọn “Quên mật khẩu” ở màn hình đăng nhập để đặt lại.</li>
          <li><strong>3. Làm sao để xuất báo cáo doanh thu?</strong> → Vào mục “Reports” và nhấn “Xuất file Excel”.</li>
        </ul>
      </div>
    </section>
  `;
}
