let orders = [
  {
    id: "DH001",
    customer: "Nguyễn Văn A",
    phone: "0987654321",
    date: "12/10/2025",
    total: 350000,
    status: "done"
  },
  {
    id: "DH002",
    customer: "Trần Thị B",
    phone: "0912345678",
    date: "13/10/2025",
    total: 180000,
    status: "pending"
  }
];


function renderOrdersPage() {
  const content = document.getElementById("content");

  // Tính toán tổng
  const totalOrders = orders.length;
  const processing = orders.filter(o => o.status === "pending").length;
  const done = orders.filter(o => o.status === "done").length;

  // Hàm render từng dòng
  const rowsHTML = orders.map(order => `
      <tr>
        <td>${order.id}</td>
        <td>${order.customer}</td>
        <td>${order.phone}</td>
        <td>${order.date}</td>
        <td>${order.total.toLocaleString("vi-VN")}đ</td>
        <td>
          <span class="status ${order.status === "done" ? "done" : "pending"}">
            ${order.status === "done" ? "Hoàn thành" : "Đang xử lý"}
          </span>
        </td>
        <td>
          <button class="btn-edit" onclick="editOrder('${order.id}')">Sửa</button>
          <button class="btn-delete" onclick="deleteOrder('${order.id}')">Xóa</button>
        </td>
      </tr>
  `).join("");

  // Giao diện chính
  content.innerHTML = `
    <section class="orders-page">
      <div class="orders-header">
        <h1>🍞 Quản lý đơn hàng</h1>
        <div class="orders-actions">
          <button class="btn-add" onclick="openAddOrderForm()">+ Thêm đơn hàng</button>
          <input type="text" id="orderSearch" placeholder="🔍 Tìm kiếm đơn hàng..." class="search-box" oninput="handleOrderSearch()">
        </div>
      </div>

      <div class="orders-summary">
        <div class="summary-card"><h3>Tổng đơn</h3><p>${totalOrders}</p></div>
        <div class="summary-card"><h3>Đang xử lý</h3><p>${processing}</p></div>
        <div class="summary-card"><h3>Hoàn thành</h3><p>${done}</p></div>
      </div>

      <div class="orders-table-container">
        <tbody id="orderTableBody"></tbody>
        <table class="orders-table">
          <thead>
            <tr>
              <th>Mã đơn</th>
              <th>Khách hàng</th>
              <th>Số điện thoại</th>
              <th>Ngày đặt</th>
              <th>Tổng tiền</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            ${rowsHTML}
          </tbody>
        </table>
      </div>
    </section>
  `;
}

function deleteOrder(id) {
  if (!confirm("Bạn có chắc muốn xóa đơn này?")) return;

  orders = orders.filter(order => order.id !== id);

  renderOrdersPage();
}

// Chức năng sửa đơn
function editOrder(id) {
  const order = orders.find(o => o.id === id);

  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Sửa đơn hàng</h2>

        <label>Mã đơn:</label>
        <input id="edit-id" type="text" value="${order.id}" disabled>

        <label>Khách hàng:</label>
        <input id="edit-customer" type="text" value="${order.customer}">

        <label>Số điện thoại:</label>
        <input id="edit-phone" type="text" value="${order.phone}">

        <label>Ngày đặt:</label>
        <input id="edit-date" type="date" value="${convertToDate(order.date)}">

        <label>Tổng tiền (VNĐ):</label>
        <input id="edit-total" type="number" value="${order.total}">

        <label>Trạng thái:</label>
        <select id="edit-status">
          <option value="pending" ${order.status === "pending" ? "selected":""}>Đang xử lý</option>
          <option value="done" ${order.status === "done" ? "selected":""}>Hoàn thành</option>
        </select>

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveEditOrder('${id}')">Lưu</button>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML("beforeend", popup);
}

// Chức năng lưu khi sửa đơn
function saveEditOrder(id) {
  const order = orders.find(o => o.id === id);
  order.customer = document.getElementById("edit-customer").value;
  order.phone = document.getElementById("edit-phone").value;
  order.date = document.getElementById("edit-date").value.split("-").reverse().join("/");
  order.total = Number(document.getElementById("edit-total").value);
  order.status = document.getElementById("edit-status").value;

  removePopup();
  renderOrdersPage();
}


// Chuyển 12/10/2025 → 2025-10-12
function convertToDate(d) {
  const [day, month, year] = d.split("/");
  return `${year}-${month}-${day}`;
}


// Form thêm đơn hàng
function openAddOrderForm() {
  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Thêm đơn hàng</h2>

        <label>Mã đơn:</label>
        <input id="add-id" type="text" placeholder="VD: DH003">

        <label>Khách hàng:</label>
        <input id="add-customer" type="text" placeholder="Tên khách hàng">

        <label>Số điện thoại:</label>
        <input id="add-phone" type="text" placeholder="Ví dụ: 0912345678">

        <label>Ngày đặt:</label>
        <input id="add-date" type="date">

        <label>Tổng tiền (VNĐ):</label>
        <input id="add-total" type="number" placeholder="180000">

        <label>Trạng thái:</label>
        <select id="add-status">
          <option value="pending">Đang xử lý</option>
          <option value="done">Hoàn thành</option>
        </select>

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveNewOrder()">Lưu</button>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML("beforeend", popup);
}

// Form lưu đơn hàng
function saveNewOrder() {
  const id = document.getElementById("add-id").value;
  const customer = document.getElementById("add-customer").value;
  const phone = document.getElementById("add-phone").value;
  const date = document.getElementById("add-date").value.split("-").reverse().join("/");
  const total = Number(document.getElementById("add-total").value);
  const status = document.getElementById("add-status").value;

  if (!id || !customer || !date || !total) {
    alert("Vui lòng nhập đầy đủ thông tin!");
    return;
  }

  orders.push({
    id,
    customer,
    phone,
    date,
    total,
    status
  });

  removePopup();
  renderOrdersPage();
}

// Chức năng hủy
function closePopup(e) {
  if (e.target.classList.contains("popup-overlay")) {
    removePopup();
  }
}

function removePopup() {
  document.querySelector(".popup-overlay")?.remove();
}

// Hàm search 
function handleOrderSearch() {
    let keyword = document.getElementById("orderSearch").value.toLowerCase();

    let filteredOrders = orders.filter(order =>
        order.id.toLowerCase().includes(keyword) ||
        order.customer.toLowerCase().includes(keyword) ||
        order.phone.toLowerCase().includes(keyword) ||
        order.status.toLowerCase().includes(keyword)
    );

    renderOrders(filteredOrders); // cập nhật bảng Orders
}

function renderOrders(list) {
    let table = document.querySelector(".orders-table tbody");
    table.innerHTML = "";

    list.forEach(order => {
        table.innerHTML += `
            <tr>
                <td>${order.id}</td>
                <td>${order.customer}</td>
                <td>${order.phone}</td>
                <td>${order.date}</td>
                <td>${order.total.toLocaleString("vi-VN")}đ</td>
                <td>
                    <span class="status ${order.status === "done" ? "done" : "pending"}">
                        ${order.status === "done" ? "Hoàn thành" : "Đang xử lý"}
                    </span>
                </td>
                <td>
                    <button class="btn-edit" onclick="editOrder('${order.id}')">Sửa</button>
                    <button class="btn-delete" onclick="deleteOrder('${order.id}')">Xóa</button>
                </td>
            </tr>
        `;
    });
}
