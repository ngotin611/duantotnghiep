// dashboard.js — hiển thị Dashboard
function renderDashboardPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <h1>📊 Dashboard Tổng Quan</h1>
    <h1> Hi Admin</h1>

    <div class="stats-cards">
      <div class="card"><h3>Doanh thu hôm nay</h3><p>7.452.000</p></div>
      <div class="card"><h3>Đơn hàng</h3><p>278</p></div>
      <div class="card"><h3>Khách hàng</h3><p>89</p></div>
      <div class="card"><h3>Sản phẩm</h3><p>46</p></div>
    </div>

    <div class="chart-container">
      <canvas id="revenueChart" height="120"></canvas>
    </div>

    <div class="table-container">
      <h3>Top sản phẩm bán chạy</h3>
      <table>
        <thead><tr><th>Sản phẩm</th><th>Đã bán</th><th>Doanh thu</th></tr></thead>
        <tbody>
          <tr><td>Bánh mì xúc xích</td><td>120</td><td>1.800.000₫</td></tr>
          <tr><td>Bánh chả</td><td>86</td><td>1.032.000₫</td></tr>
          <tr><td>Pizza hải sản</td><td>14</td><td>2.100.000₫</td></tr>
          <tr><td>Pizza bò</td><td>11</td><td>1.320.000₫</td></tr>
          <tr><td>Hamburger thịt nướng</td><td>34</td><td>680.000₫</td></tr>
          <tr><td>Hamburger phô mai</td><td>13</td><td>520.000₫</td></tr>
        </tbody>
      </table>
    </div>
  `;

  // Biểu đồ Chart.js
  const ctx = document.getElementById("revenueChart");
  if (!ctx) return;
  new Chart(ctx, {
    type: "line",
    data: {
      labels: ["T1", "T2", "T3", "T4", "T5", "T6"],
      datasets: [{
        label: "Doanh thu (₫)",
        data: [2000000, 3500000, 5000000, 4500000, 6000000, 8000000],
        borderColor: "#ff8c00",
        backgroundColor: "rgba(255,140,0,0.2)",
        tension: 0.3,
        fill: true
      }]
    },
    options: { responsive: true, plugins: { legend: { display: false } }, scales: { y: { beginAtZero: true } } }
  });
}
