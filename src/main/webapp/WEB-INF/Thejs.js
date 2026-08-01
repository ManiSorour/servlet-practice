let products = [
    { id: 1, name: "لپ‌تاپ لنوو", code: "P-1001", category: "الکترونیک", quantity: 12, purchasePrice: 25000000, sellPrice: 29500000 },
    { id: 2, name: "ماوس بی‌سیم", code: "P-1002", category: "الکترونیک", quantity: 0, purchasePrice: 350000, sellPrice: 480000 },
    { id: 3, name: "دفتر یادداشت", code: "P-1003", category: "لوازم تحریر", quantity: 45, purchasePrice: 20000, sellPrice: 35000 },
    { id: 4, name: "خودکار آبی", code: "P-1004", category: "لوازم تحریر", quantity: 120, purchasePrice: 3000, sellPrice: 6000 },
];

const tableBody = document.getElementById("productTableBody");
// const emptyState = document.getElementById("emptyState");

const searchInput = document.getElementById("searchInput")
const searchBtn = document.getElementById("searchBtn")
const clearSearchBtn = document.getElementById("clearSearchBtn");

const modal = document.getElementById("productModal");
const modalTitle = document.getElementById("modalTitle");
const productForm = document.getElementById("productForm");
// const formError = document.getElementById("formError");

const openAddFormBtn = document.getElementById("openAddFormBtn");
const closeModalBtn = document.getElementById("closeModalBtn");
const cancelFormBtn = document.getElementById("cancelFormBtn");

const productIdField = document.getElementById("productId");
const nameField = document.getElementById("nameField");
const codeField = document.getElementById("codeField");
const categoryField = document.getElementById("categoryField");
const quantityField = document.getElementById("quantityField");
const purchasePriceField = document.getElementById("purchasePriceField");
const sellPriceField = document.getElementById("sellPriceField");


function renderTable(list) {
    tableBody.innerHTML = "";

    if (list.length === 0) {
        emptyState.hidden = false;
        return;
    }
    emptyState.hidden = true;

    for (const product of list) {
        const row = document.createElement("tr")

        const isAvailable = product.quantity > 0;
        const statusClass = isAvailable ? "status-available" : "status-outofstock";
        const statusLabel = isAvailable ? "موجود " : "ناموجود ";

        row.innerHTML = `
        <td>${product.id}</td>
        <td>${(product.name)}</td>
        <td>${(product.code)}</td>
        <td>${(product.category)}</td>
        <td>${product.quantity}</td>
        <td><span class="status-badge" ${statusClass}>${statusLabel}</span></td>
        <td>${formatPrice(product.purchasePrice)}</td>
        <td>${formatPrice(product.sellPrice)}</td>
        <td class="row-actions">
         <button class="btn btn-ghost btn-small" data-action="edit" data-id="${product.id}">ویرایش</button>
                <button class="btn btn-danger btn-small" data-action="delete" data-id="${product.id}">حذف</button>
        </td>
        `;
        tableBody.appendChild(row);
    }
}

function formatPrice(value) {
    return Number(value).toLocaleString("fa-IR")
}

function searchByCategory() {

    const matn = searchInput.value.trim().toLowerCase()

    if (matn === "") {
        renderTable(products)
        return;
    }

    const results = products.filter(p =>
        p.category.toLowerCase().includes(matn));

    renderTable(results);
}
    searchBtn.addEventListener("click", searchByCategory);

    searchInput.addEventListener("keydown", (event) => {

        if (event.key === "Enter") {
            event.preventDefault();
            searchByCategory()
        }
    });

    clearSearchBtn.addEventListener("click", () => {
        searchInput.value = "";
        renderTable(products);
    });

    function generateNewId() {
        if (products.length === 0) return 1;
        const maxId = Math.max(...products.map(p => p.id));
        return maxId + 1;
    }



function openModal(mode, product = null) {

    productForm.reset();

    if (mode === "add") {
        modalTitle.textContent = "افزودن کالای جدید";
        productIdField.value = "";

    } else {
        modalTitle.textContent = "ویرایش کالا";
        productIdField.value = product.id;
        nameField.value = product.name;
        codeField.value = product.code;
        categoryField.value = product.category;
        quantityField.value = product.quantity;
        purchasePriceField.value = product.purchasePrice;
        sellPriceField.value = product.sellPrice;
    }
    modal.hidden = false;

}

function closeModal() {
    modal.hidden = true;
}

openAddFormBtn.addEventListener("click", () => openModal("add"));
closeModalBtn.addEventListener("click", closeModal);
cancelFormBtn.addEventListener("click", closeModal);

productForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const name = nameField.value.trim();
    const code = codeField.value.trim();
    const category = categoryField.value.trim();
    const quantity = Number(quantityField.value);
    const purchasePrice = Number(purchasePriceField.value);
    const sellPrice = Number(sellPriceField.value);

    // if (!name || !code || !category) {
    //     showFormError("fill all fields")
    //     return;
    // }

    // if (quantity < 0 || purchasePrice < 0 || sellPrice < 0) {
    //     showFormError("number can't be negative")
    //     return;
    // }

    const isEditMode = productIdField.value !== "";

    if (isEditMode) {
        updateProduct(Number(productIdField.value), {name, code, category, quantity, purchasePrice, sellPrice});

    } else {
        addProduct({name, code, category, quantity, purchasePrice, sellPrice});
    }

    closeModal();
    renderTable(products);

});

// function showFormError(mesage) {
//     formError.textContent = mesage;
//     formError.hidden = false;
// }


function addProduct(data) {
    const newProduct = {id: generateNewId(), ...data};
    products.push(newProduct);
}

function updateProduct(id, data) {
    const index = products.findIndex(p => p.id === id);
    if (index === -1) return;
    products[index] = {id, ...data};
}

function deleteProduct(id) {
    products = products.filter(p => p.id !== id);
}

tableBody.addEventListener("click", (event) => {
    const button = event.target.closest("button[data-action]");
    if (!button) return;

    const id = Number(button.dataset.id);
    const action = button.dataset.action;

    if (action === "edit") {
        const product = products.find(p => p.id === id);
        if (product) openModal("edit", product);
    }

    if (action === "delete") {
        const product = products.find(p => p.id === id);
        const confirmed = confirm(`آیا از حذف «${product.name}» مطمئنید؟`);
        if (confirmed) {
            deleteProduct(id);
            renderTable(products);
        }
    }
});


renderTable(products);































