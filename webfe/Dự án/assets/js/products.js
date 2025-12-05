let products = [
  {
    id: 1,
    name: "Bánh mì xúc xích",
    category: "Bánh mặn",
    price: 15000,
    status: "stock",
    img: "../assets/img/BanhMyXucXich.jpg"
  },
  {
    id: 2,
    name: "Bánh mì chả",
    category: "Bánh mặn",
    price: 12000,
    status: "stock",
    img: "../assets/img/BanhMyCha.jpeg"
  },
  {
    id: 3,
    name: "Bánh mì gà",
    category: "Bánh mặn",
    price: 25000,
    status: "low",
    img: "../assets/img/BanhMyGa.jpeg"
  },
  {
    id: 4,
    name: "Bánh mì que pate",
    category: "Bánh mặn",
    price: 10000,
    status: "stock",
    img: "../assets/img/BanhMyPate.jpeg"
  },
  {
    id: 5,
    name: "Bánh mì thịt nướng",
    category: "Bánh mặn",
    price: 20000,
    status: "stock",
    img: "../assets/img/BanhMyThitNuong.jpeg"
  },
  {
    id: 6,
    name: "Bánh mì trứng",
    category: "Bánh mặn",
    price: 22000,
    status: "stock",
    img: "../assets/img/BanhMyTrung.jpeg"
  },
  {
    id: 7,
    name: "Hamburger bò",
    category: "Bánh mặn",
    price: 35000,
    status: "stock",
    img: "../assets/img/HamburgerBo.jpg"
  },
  {
    id: 8,
    name: "Hamburger cá",
    category: "Bánh mặn",
    price: 45000,
    status: "out",
    img: "../assets/img/HamburgerCa.jpg"
  },
  {
    id: 9,
    name: "Hamburger gà",
    category: "Bánh mặn",
    price: 30000,
    status: "low",
    img: "../assets/img/HamburgerGa.jpg"
  },
  {
    id: 10,
    name: "Hamburger phô mai",
    category: "Bánh mặn",
    price: 40000,
    status: "stock",
    img: "../assets/img/HamburgerPhoMai.jpg"
  },
  {
    id: 11,
    name: "Pizza bò",
    category: "Pizza",
    price: 120000,
    status: "stock",
    img: "../assets/img/PizzaBo.jpg"
  },
  {
    id: 12,
    name: "Pizza gà",
    category: "Pizza",
    price: 100000,
    status: "low",
    img: "../assets/img/PizzaGa.png"
  },
  {
    id: 13,
    name: "Pizza hải sản",
    category: "Pizza",
    price: 150000,
    status: "stock",
    img: "../assets/img/PizzaHaiSan.jpg"
  },
  {
    id: 14,
    name: "Pizza phô mai",
    category: "Pizza",
    price: 130000,
    status: "stock",
    img: "../assets/img/PizzaPhoMai.jpg"
  }
];


function renderProductsPage() {
  const content = document.getElementById("content");

  content.innerHTML = `
    <section class="products-page">
      <div class="products-header">
        <h1>🍰 Quản lý sản phẩm</h1>
        <div class="products-actions">
          <button class="btn-add" onclick="openAddProduct()">+ Thêm sản phẩm</button>
          <input type="text" id="searchProduct" placeholder="🔍 Tìm kiếm sản phẩm..." onkeyup="searchProduct()" class="search-box">
        </div>
      </div>

      <div class="products-table-container">
        <table class="products-table">
          <thead>
            <tr>
              <th>Hình ảnh</th>
              <th>Tên bánh</th>
              <th>Danh mục</th>
              <th>Giá bán</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            ${products.map(p => `
              <tr>
                <td><img src="${p.img}" alt="${p.name}"></td>
                <td>${p.name}</td>
                <td>${p.category}</td>
                <td>${p.price.toLocaleString()}đ</td>
                <td>
                  <span class="status ${p.status === 'stock' ? 'done' : 'pending'}">
                    ${p.status === "stock" ? "Còn hàng" :
                      p.status === "low" ? "Cần nhập hàng" : "Hết hàng"}
                  </span>
                </td>
                <td>
                  <button class="btn-edit" onclick="openEditProduct(${p.id})">Sửa</button>
                  <button class="btn-delete" onclick="deleteProduct(${p.id})">Xóa</button>
                </td>
              </tr>
            `).join("")}
          </tbody>
        </table>
      </div>
    </section>
  `;
}

// Thêm sản phẩm
function openAddProduct() {
  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Thêm sản phẩm</h2>

        <label>Tên bánh:</label>
        <input id="prod-name" type="text">

        <label>Danh mục:</label>
        <input id="prod-category" type="text" value="Bánh mặn">

        <label>Giá bán (VNĐ):</label>
        <input id="prod-price" type="number">

        <label>Trạng thái:</label>
        <select id="prod-status">
          <option value="stock">Còn hàng</option>
          <option value="low">Cần nhập hàng</option>
          <option value="out">Hết hàng</option>
        </select>

        <label>Hình ảnh (link hoặc đường dẫn):</label>
        <input id="prod-img" type="text" placeholder="../assets/img/xxx.jpg">

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveNewProduct()">Lưu</button>
        </div>
      </div>
    </div>
  `;
  document.body.insertAdjacentHTML("beforeend", popup);
}

// Lưu sản phẩm
function saveNewProduct() {
  const name = document.getElementById("prod-name").value;
  const category = document.getElementById("prod-category").value;
  const price = Number(document.getElementById("prod-price").value);
  const status = document.getElementById("prod-status").value;
  const img = document.getElementById("prod-img").value;

  if (!name || !price || !img) {
    alert("Vui lòng nhập đầy đủ thông tin!");
    return;
  }

  products.push({
    id: Date.now(),
    name,
    category,
    price,
    status,
    img
  });

  removePopup();
  renderProductsPage();
}


// Sửa sản phẩm
function openEditProduct(id) {
  const p = products.find(x => x.id === id);

  const popup = `
    <div class="popup-overlay" onclick="closePopup(event)">
      <div class="popup" onclick="event.stopPropagation()">
        <h2>Sửa sản phẩm</h2>

        <label>Tên bánh:</label>
        <input id="edit-name" type="text" value="${p.name}">

        <label>Danh mục:</label>
        <input id="edit-category" type="text" value="${p.category}">

        <label>Giá bán (VNĐ):</label>
        <input id="edit-price" type="number" value="${p.price}">

        <label>Trạng thái:</label>
        <select id="edit-status">
          <option value="stock" ${p.status === "stock" ? "selected" : ""}>Còn hàng</option>
          <option value="low" ${p.status === "low" ? "selected" : ""}>Cần nhập hàng</option>
          <option value="out" ${p.status === "out" ? "selected" : ""}>Hết hàng</option>
        </select>

        <label>Hình ảnh:</label>
        <input id="edit-img" type="text" value="${p.img}">

        <div class="popup-buttons">
          <button class="btn-cancel" onclick="removePopup()">Hủy</button>
          <button class="btn-save" onclick="saveEditProduct(${id})">Lưu</button>
        </div>
      </div>
    </div>
  `;
  document.body.insertAdjacentHTML("beforeend", popup);
}

// Lưu khi sửa sản phẩm
function saveEditProduct(id) {
  const p = products.find(x => x.id === id);

  p.name = document.getElementById("edit-name").value;
  p.category = document.getElementById("edit-category").value;
  p.price = Number(document.getElementById("edit-price").value);
  p.status = document.getElementById("edit-status").value;
  p.img = document.getElementById("edit-img").value;

  removePopup();
  renderProductsPage();
}

// Xóa sản phẩm
function deleteProduct(id) {
  if (!confirm("Bạn có chắc muốn xóa sản phẩm này?")) return;

  products = products.filter(p => p.id !== id);
  renderProductsPage();
}

// Hàm hiển thị danh sách
function renderProducts(list = products) {
  let table = document.getElementById("productTableBody");
  table.innerHTML = "";

  list.forEach((p) => {
    table.innerHTML += `
      <tr>
        <td>${p.id}</td>
        <td>${p.name}</td>
        <td>${p.price.toLocaleString()} đ</td>
        <td>${p.category}</td>
        <td>${p.stock}</td>
      </tr>
    `;
  });
}

// Hàm tìm kiếm sản phẩm
function searchProduct() {
  let keyword = document.getElementById("searchProduct").value.toLowerCase();

  let filtered = products.filter(p =>
    p.name.toLowerCase().includes(keyword) ||
    p.category.toLowerCase().includes(keyword) ||
    String(p.id).includes(keyword)
  );

  renderSearchProducts(filtered);
}

function renderSearchProducts(list) {
  const tbody = document.querySelector(".products-table tbody");

  tbody.innerHTML = list.map(p => `
    <tr>
      <td><img src="${p.img}" alt="${p.name}"></td>
      <td>${p.name}</td>
      <td>${p.category}</td>
      <td>${p.price.toLocaleString()}đ</td>
      <td>
        <span class="status ${p.status === 'stock' ? 'done' : 'pending'}">
          ${p.status === "stock" ? "Còn hàng" :
            p.status === "low" ? "Cần nhập hàng" : "Hết hàng"}
        </span>
      </td>
      <td>
        <button class="btn-edit" onclick="openEditProduct(${p.id})">Sửa</button>
        <button class="btn-delete" onclick="deleteProduct(${p.id})">Xóa</button>
      </td>
    </tr>
  `).join("");
}

