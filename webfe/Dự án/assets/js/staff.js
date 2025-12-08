// ===================== DATA MẪU NHÂN VIÊN =====================
let staff = [
    { 
        id: "NV001", 
        name: "Nguyễn Văn Minh", 
        role: "Thu ngân", 
        phone: "0912345678", 
        address: "Hải Châu, Đà Nẵng", 
        status: "active" 
    },
    { 
        id: "NV002", 
        name: "Trần Thị Hà", 
        role: "Nhân viên bếp", 
        phone: "0934567890", 
        address: "Thanh Khê, Đà Nẵng", 
        status: "active" 
    },
    { 
        id: "NV003", 
        name: "Phạm Quang Khải", 
        role: "Phục vụ", 
        phone: "0978123456", 
        address: "Liên Chiểu, Đà Nẵng", 
        status: "active" 
    }
];


// ===================== RENDER TRANG STAFF =====================
function renderStaffPage() {
  const content = document.getElementById("content");

  content.innerHTML = `
    <section class="customers">
      <h2>👥 Quản lý nhân viên</h2>

      <div class="actions">
        <button class="btn-add" onclick="openAddStaff()">+ Thêm nhân viên</button>
        <input 
          type="text" 
          id="staffSearch" 
          placeholder="Tìm theo tên, chức vụ hoặc số điện thoại..."
          oninput="handleStaffSearch()"
        >
      </div>
  
      <table class="table">
        <thead>
          <tr>
            <th>Mã NV</th>
            <th>Họ tên</th>
            <th>Chức vụ</th>
            <th>Số điện thoại</th>
            <th>Địa chỉ</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>

        <tbody id="staffTableBody"></tbody>

        <tbody>
          ${staff.map(s => `
            <tr>
              <td>${s.id}</td>
              <td>${s.name}</td>
              <td>${s.role}</td>
              <td>${s.phone}</td>
              <td>${s.address}</td>
              <td><span class="status success">Đang làm</span></td>
              <td>
                <button class="btn-edit" onclick="openEditStaff('${s.id}')">Sửa</button>
                <button class="btn-delete" onclick="deleteStaff('${s.id}')">Xóa</button>
              </td>
            </tr>
          `).join("")}
        </tbody>
      </table>
    </section>
  `;
}


// ===================== POPUP: THÊM NHÂN VIÊN =====================
function openAddStaff() {
  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Thêm nhân viên</h2>

        <label>Mã NV:</label>
        <input id="st-id" type="text" placeholder="VD: NV010">

        <label>Họ tên:</label>
        <input id="st-name" type="text">

        <label>Chức vụ:</label>
        <input id="st-role" type="text">

        <label>Số điện thoại:</label>
        <input id="st-phone" type="text">

        <label>Địa chỉ:</label>
        <input id="st-address" type="text">

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveNewStaff()">Lưu</button>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML("beforeend", popup);
}


// ===================== LƯU NHÂN VIÊN MỚI =====================
function saveNewStaff() {
  const id = document.getElementById("st-id").value;
  const name = document.getElementById("st-name").value;
  const role = document.getElementById("st-role").value;
  const phone = document.getElementById("st-phone").value;
  const address = document.getElementById("st-address").value;

  if (!id || !name || !role) {
    alert("Vui lòng nhập đầy đủ thông tin!");
    return;
  }

  staff.push({
    id,
    name,
    role,
    phone,
    address,
    status: "active"
  });

  removePopup();
  renderStaffPage();
}


// ===================== POPUP: SỬA NHÂN VIÊN =====================
function openEditStaff(id) {
  const s = staff.find(x => x.id === id);

  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Sửa nhân viên</h2>

        <label>Mã NV:</label>
        <input id="edit-id" type="text" value="${s.id}" disabled>

        <label>Họ tên:</label>
        <input id="edit-name" type="text" value="${s.name}">

        <label>Chức vụ:</label>
        <input id="edit-role" type="text" value="${s.role}">

        <label>Số điện thoại:</label>
        <input id="edit-phone" type="text" value="${s.phone}">

        <label>Địa chỉ:</label>
        <input id="edit-address" type="text" value="${s.address}">

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveEditStaff('${id}')">Lưu</button>
        </div>
      </div>
    </div>
  `;

  document.body.insertAdjacentHTML("beforeend", popup);
}


// ===================== LƯU SỬA NHÂN VIÊN =====================
function saveEditStaff(id) {
  const s = staff.find(x => x.id === id);

  s.name = document.getElementById("edit-name").value;
  s.role = document.getElementById("edit-role").value;
  s.phone = document.getElementById("edit-phone").value;
  s.address = document.getElementById("edit-address").value;

  removePopup();
  renderStaffPage();
}


// ===================== XÓA NHÂN VIÊN =====================
function deleteStaff(id) {
  if (!confirm("Bạn có chắc muốn xóa nhân viên này?")) return;

  staff = staff.filter(s => s.id !== id);

  renderStaffPage();
}


// ===================== TÌM KIẾM NHÂN VIÊN =====================
function handleStaffSearch() {
  let keyword = document.getElementById("staffSearch").value.toLowerCase();

  let filteredStaff = staff.filter(s =>
      s.name.toLowerCase().includes(keyword) ||
      s.role.toLowerCase().includes(keyword) ||
      s.phone.includes(keyword)
  );

  renderStaff(filteredStaff);
}

function renderStaff(list) {
  let tableBody = document.getElementById("staffTableBody");
  tableBody.innerHTML = "";

  list.forEach(s => {
      let row = `
          <tr>
              <td>${s.id}</td>
              <td>${s.name}</td>
              <td>${s.role}</td>
              <td>${s.phone}</td>
              <td>${s.address}</td>
              <td><span class="status success">Đang làm</span></td>
              <td>
                  <button class="btn-edit" onclick="openEditStaff('${s.id}')">Sửa</button>
                  <button class="btn-delete" onclick="deleteStaff('${s.id}')">Xóa</button>
              </td>
          </tr>
      `;
      tableBody.innerHTML += row;
  });
}


// ===================== ĐÓNG POPUP =====================
function removePopup() {
  const popup = document.querySelector(".popup-overlay");
  if (popup) popup.remove();
}

function closePopup(event) {
  if (event.target.classList.contains("popup-overlay")) {
    removePopup();
  }
}
