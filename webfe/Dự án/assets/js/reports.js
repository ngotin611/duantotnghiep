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
            <th>Tuần</th>
            <th>Doanh thu</th>
            <th>Đơn hàng</th>
            <th>Khách hàng mới</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Tuần 1</td>
            <td>43,210,000đ</td>
            <td>1532</td>
            <td>20</td>
          </tr>
          <tr>
            <td>Tuần 2</td>
            <td>52,146,000đ</td>
            <td>1954</td>
            <td>15</td>
          </tr>
          <tr>
            <td>Tuần 3</td>
            <td>57,683,000đ</td>
            <td>2248</td>
            <td>21</td>
          </tr>
          <tr>
            <td>Tuần 4</td>
            <td>41,001,000đ</td>
            <td>1736</td>
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
