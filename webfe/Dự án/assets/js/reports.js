function renderReportsPage() {
  const content = document.getElementById("content");
  content.innerHTML = `
    <section class="reports">
      <h2>📊 Báo cáo doanh thu</h2>

      <div class="report-summary">
        <div class="report-card">
          <h3>Tổng doanh thu</h3>
          <p>194,040,000đ</p>
        </div>
        <div class="report-card">
          <h3>Đơn hàng trong tháng</h3>
          <p>7470 đơn</p>
        </div>
        <div class="report-card">
          <h3>Khách hàng mới</h3>
          <p>184 người</p>
        </div>
      </div>

      <div class="chart-container">
        <canvas id="salesChart"></canvas>
      </div>

      <table class="table">
        <thead>
          <tr>
            <th>Tháng</th>
            <th>Doanh thu</th>
            <th>Đơn hàng</th>
            <th>Khách hàng mới</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Tháng 1</td>
            <td>18,000,000đ</td>
            <td>120</td>
            <td>20</td>
          </tr>
          <tr>
            <td>Tháng 2</td>
            <td>22,500,000đ</td>
            <td>135</td>
            <td>15</td>
          </tr>
          <tr>
            <td>Tháng 3</td>
            <td>28,000,000đ</td>
            <td>150</td>
            <td>21</td>
          </tr>
          <tr>
            <td>Tháng 4</td>
            <td>30,500,000đ</td>
            <td>160</td>
            <td>18</td>
          </tr>
        </tbody>
      </table>
    </section>
  `;

  // Dữ liệu biểu đồ
  const ctx = document.getElementById("salesChart").getContext("2d");
  new Chart(ctx, {
    type: "bar",
    data: {
      labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4"],
      datasets: [{
        label: "Doanh thu (VNĐ)",
        data: [18000000, 22500000, 28000000, 30500000],
        backgroundColor: "#ffcc80"
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: { display: false },
        title: {
          display: true,
          text: "Doanh thu theo tháng",
          font: { size: 16 }
        }
      },
      scales: {
        y: { beginAtZero: true }
      }
    }
  });
}
