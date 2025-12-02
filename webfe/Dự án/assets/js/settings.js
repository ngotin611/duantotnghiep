function renderSettingsPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <section class="settings">
      <h2>⚙️ Cài đặt tài khoản</h2>
      <p class="desc">Tại đây bạn có thể chỉnh sửa thông tin cá nhân, đổi mật khẩu hoặc đăng xuất khỏi hệ thống.</p>

      <div class="settings-container">
        <!-- Cập nhật thông tin -->
        <div class="settings-card">
          <h3>👤 Thông tin tài khoản</h3>
          <form id="profileForm">
            <label>Họ và tên</label>
            <input type="text" id="name" value="Admin Bakery" />

            <label>Email</label>
            <input type="email" id="email" value="admin@bakery.vn" />

            <label>Số điện thoại</label>
            <input type="text" id="phone" value="0123 456 789" />

            <button type="submit" class="btn save">💾 Lưu thay đổi</button>
          </form>
        </div>

        <!-- Đổi mật khẩu -->
        <div class="settings-card">
          <h3>🔒 Đổi mật khẩu</h3>
          <form id="passwordForm">
            <label>Mật khẩu hiện tại</label>
            <input type="password" id="oldPass" />

            <label>Mật khẩu mới</label>
            <input type="password" id="newPass" />

            <label>Xác nhận mật khẩu</label>
            <input type="password" id="confirmPass" />

            <button type="submit" class="btn change">🔄 Đổi mật khẩu</button>
          </form>
        </div>

        <!-- Đăng xuất -->
        <div class="settings-card logout-card">
          <h3>🚪 Đăng xuất</h3>
          <p>Rời khỏi tài khoản quản trị hiện tại.</p>
          <button class="btn logout" onclick="logout()">Đăng xuất</button>
        </div>
      </div>
    </section>
  `;

  // Xử lý các sự kiện form
  document.getElementById("profileForm").addEventListener("submit", (e) => {
    e.preventDefault();
    alert("✅ Thông tin đã được cập nhật!");
  });

  document.getElementById("passwordForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const newPass = document.getElementById("newPass").value;
    const confirmPass = document.getElementById("confirmPass").value;

    if (newPass === confirmPass && newPass.length >= 6) {
      alert("🔒 Mật khẩu đã được thay đổi thành công!");
    } else {
      alert("⚠️ Mật khẩu không khớp hoặc quá ngắn!");
    }
  });
}

function logout() {
  alert("🚪 Đã đăng xuất thành công!");
  window.location.href = "../screen/login.html"; // hoặc đổi sang trang đăng nhập thật
}
