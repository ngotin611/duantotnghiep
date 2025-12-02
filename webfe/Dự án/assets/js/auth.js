// Xử lý đăng ký
const registerForm = document.getElementById("registerForm");
if (registerForm) {
  registerForm.addEventListener("submit", function (e) {
    e.preventDefault();
    const username = document.getElementById("regUsername").value;
    const password = document.getElementById("regPassword").value;

    if (localStorage.getItem(username)) {
      document.getElementById("regMsg").innerText = "Tên đăng nhập đã tồn tại!";
    } else {
      localStorage.setItem(username, password);
      document.getElementById("regMsg").innerText = "Đăng ký thành công! Chuyển hướng...";
      setTimeout(() => {
        window.location.href = "login.html";
      }, 1500);
    }
  });
}

// Xử lý đăng nhập
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", function (e) {
    e.preventDefault();
    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;
    const storedPassword = localStorage.getItem(username);

    if (storedPassword && storedPassword === password) {
      document.getElementById("loginMsg").style.color = "green";
      document.getElementById("loginMsg").innerText = "Đăng nhập thành công!";
      setTimeout(() => {
        window.location.href = "index.html";
      }, 1000);
    } else {
      document.getElementById("loginMsg").innerText = "Sai tài khoản hoặc mật khẩu!";
    }
  });
}
