let customers = [
  { id: "KH001", name: "Nguyễn Văn Đồng Bằng", email: "vanbang@example.com", phone: "0263485394", address: "123 Lê Lợi, Đà Nẵng", status: "active" },
  { id: "KH002", name: "Phạm Quang Đạt", email: "dat01062004@example.com", phone: "0359904840", address: "554 Điện Biên Phủ, Đà Nẵng", status: "active" },
  { id: "KH003", name: "Nguyễn Văn Quang", email: "quang0862@example.com", phone: "0862340391", address: "47/55 Huỳnh Ngọc Huệ, Đà Nẵng", status: "active" },
  { id: "KH004", name: "Huỳnh Ngọc Vỹ", email: "vy2602@example.com", phone: "0393983249", address: "108 Phạm Đình Hổ, Đà Nẵng", status: "active" },
  { id: "KH005", name: "Nguyễn Thanh Thủy", email: "thuy2707@example.com", phone: "0377476732", address: "210 Nguyễn Tất Thành, Đà Nẵng", status: "active" },

  { id: "KH006", name: "Nguyễn Quốc Phú", email: "phu2845@example.com", phone: "074495838", address: "27 Mỹ An, Đà Nẵng", status: "active" },
  { id: "KH007", name: "Phan Quốc Thắng", email: "qthang777@example.com", phone: "0837492837", address: "09 Dũng Sĩ Thanh Khê, Đà Nẵng", status: "active" },
  { id: "KH008", name: "Lê Nguyễn Bích Giao", email: "giao828@example.com", phone: "0984374857", address: "104 Nguyễn Văn Linh, Đà Nẵng", status: "active" },
  { id: "KH009", name: "Trần Thiện Hương", email: "huongtran2338@example.com", phone: "0383749521", address: "54 Dương Bích Liên, Đà Nẵng", status: "active" },
  { id: "KH010", name: "Lê Tấn Huy", email: "huycris777@example.com", phone: "0384475839", address: "748/12 Trần Cao Vân, Đà Nẵng", status: "active" },

  { id: "KH011", name: "Ngô Minh Tài", email: "taingominh@example.com", phone: "0912234875", address: "12 Trưng Nữ Vương, Đà Nẵng", status: "active" },
  { id: "KH012", name: "Hoàng Hải Đăng", email: "danghoang@example.com", phone: "0903459832", address: "45 Nguyễn Văn Thoại, Đà Nẵng", status: "active" },
  { id: "KH013", name: "Phạm Minh Hảo", email: "haominh@example.com", phone: "0935728493", address: "239 Núi Thành, Đà Nẵng", status: "active" },
  { id: "KH014", name: "Lê Khánh Chi", email: "khanchi@example.com", phone: "0987384920", address: "76 Hồ Xuân Hương, Đà Nẵng", status: "active" },
  { id: "KH015", name: "Đoàn Trần Vy", email: "vydoan@gmail.com", phone: "0837483284", address: "301 Tôn Đức Thắng, Đà Nẵng", status: "active" },

  { id: "KH016", name: "Nguyễn Nhật Hào", email: "hao0401@example.com", phone: "0912834752", address: "33 Nguyễn Hữu Thọ, Đà Nẵng", status: "active" },
  { id: "KH017", name: "Trần Thị Mỹ Duyên", email: "duyenmy@example.com", phone: "0974839223", address: "14 Tiểu La, Đà Nẵng", status: "active" },
  { id: "KH018", name: "Phạm Công Minh", email: "minh.cong@example.com", phone: "0983427344", address: "58 Thanh Long, Đà Nẵng", status: "active" },
  { id: "KH019", name: "Lê Bảo Trâm", email: "tram.tram@example.com", phone: "0367328402", address: "66 Hà Huy Tập, Đà Nẵng", status: "active" },
  { id: "KH020", name: "Hoàng Gia Huy", email: "giahuy@example.com", phone: "0905324873", address: "21 Ông Ích Khiêm, Đà Nẵng", status: "active" }
];

function renderCustomersPage() {
  const content = document.getElementById("content");

  content.innerHTML = `
    <section class="customers">
      <h2>👥 Quản lý khách hàng</h2>

      <div class="actions">
        <button class="btn-add" onclick="openAddCustomer()">+ Thêm khách hàng</button>
        <input 
          type="text" 
          id="customerSearch" 
          placeholder="Tìm kiếm theo tên, số điện thoại hoặc email..."
          oninput="handleCustomerSearch()"
        >
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
        
        <tbody id="customerTableBody"></tbody>

        <tbody>
          ${customers.map(c => `
            <tr>
              <td>${c.id}</td>
              <td>${c.name}</td>
              <td>${c.email}</td>
              <td>${c.phone}</td>
              <td>${c.address}</td>
              <td><span class="status success">Hoạt động</span></td>
              <td>
                <button class="btn-edit" onclick="openEditCustomer('${c.id}')">Sửa</button>
                <button class="btn-delete" onclick="deleteCustomer('${c.id}')">Xóa</button>
              </td>
            </tr>
          `).join("")}
        </tbody>
      </table>
    </section>
  `;
}

// Thêm khách hàng
function openAddCustomer() {
  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Thêm khách hàng</h2>

        <label>Mã KH:</label>
        <input id="cus-id" type="text" placeholder="VD: KH010">

        <label>Họ tên:</label>
        <input id="cus-name" type="text">

        <label>Email:</label>
        <input id="cus-email" type="email">

        <label>Số điện thoại:</label>
        <input id="cus-phone" type="text">

        <label>Địa chỉ:</label>
        <input id="cus-address" type="text">

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveNewCustomer()">Lưu</button>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML("beforeend", popup);
}

// Lưu khách hàng
function saveNewCustomer() {
  const id = document.getElementById("cus-id").value;
  const name = document.getElementById("cus-name").value;
  const email = document.getElementById("cus-email").value;
  const phone = document.getElementById("cus-phone").value;
  const address = document.getElementById("cus-address").value;

  if (!id || !name || !email) {
    alert("Vui lòng nhập đầy đủ thông tin!");
    return;
  }

  customers.push({
    id,
    name,
    email,
    phone,
    address,
    status: "active"
  });

  removePopup();
  renderCustomersPage();
}

// Sửa khách hàng
function openEditCustomer(id) {
  const c = customers.find(x => x.id === id);

  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Sửa khách hàng</h2>

        <label>Mã KH:</label>
        <input id="edit-id" type="text" value="${c.id}" disabled>

        <label>Họ tên:</label>
        <input id="edit-name" type="text" value="${c.name}">

        <label>Email:</label>
        <input id="edit-email" type="email" value="${c.email}">

        <label>Số điện thoại:</label>
        <input id="edit-phone" type="text" value="${c.phone}">

        <label>Địa chỉ:</label>
        <input id="edit-address" type="text" value="${c.address}">

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveEditCustomer('${id}')">Lưu</button>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML("beforeend", popup);
}

// Lưu khi sửa khách hàng
function saveEditCustomer(id) {
  const c = customers.find(x => x.id === id);

  c.name = document.getElementById("edit-name").value;
  c.email = document.getElementById("edit-email").value;
  c.phone = document.getElementById("edit-phone").value;
  c.address = document.getElementById("edit-address").value;

  removePopup();
  renderCustomersPage();
}

// Xóa khách hàng
function deleteCustomer(id) {
  if (!confirm("Bạn có chắc muốn xóa khách hàng này?")) return;

  customers = customers.filter(c => c.id !== id);

  renderCustomersPage();
}

// Tìm kiếm
function handleCustomerSearch() {
    let keyword = document.getElementById("customerSearch").value.toLowerCase();

    let filteredCustomers = customers.filter(cus =>
        cus.name.toLowerCase().includes(keyword) ||
        cus.phone.includes(keyword) ||
        cus.email.toLowerCase().includes(keyword)
    );

    renderCustomers(filteredCustomers);
}

// Chưa có thì thêm
function renderCustomers(list) {
    let tableBody = document.getElementById("customerTableBody");
    tableBody.innerHTML = "";

    list.forEach(cus => {
        let row = `
            <tr>
                <td>${cus.id}</td>
                <td>${cus.name}</td>
                <td>${cus.email}</td>
                <td>${cus.phone}</td>
                <td>${cus.address}</td>
                <td>
                    <span class="status ${cus.status}">
                        ${cus.status === "active" ? "Đang hoạt động" : "Không hoạt động"}
                    </span>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

