// index.js — điều hướng giữa các trang
function showPage(page) {
  const content = document.getElementById("content");

  switch (page) {
    case "dashboard":
      renderDashboardPage();
      break;
    case "orders":
      renderOrdersPage();
      break;
    case "products":
      renderProductsPage();
      break;
    case "customers":
      renderCustomersPage();
      break;
    case "reports":
      renderReportsPage();
      break;
    case "discounts":
      renderDiscountsPage();
      break;
    case "integrations":
      renderIntegrationsPage();
      break;
    case "help":
      renderHelpPage();
      break;
    case "settings":
      renderSettingsPage();
      break;
    case "staff":
      renderStaffPage();
      break;
      
    default:
      content.innerHTML = `<h2>${page} đang được phát triển...</h2>`;
  }
}

// Tự động mở dashboard khi tải trang
document.addEventListener("DOMContentLoaded", () => {
  showPage("dashboard");
});
