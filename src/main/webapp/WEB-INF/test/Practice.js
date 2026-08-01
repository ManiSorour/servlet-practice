let products = [{
    id: 1, name: "iphone12", code: "ip12", category: "phone", quantity: "10", purchasePrice: "1000", sellPrice: "1200"
},
    {id: 2, name: "iphone13", code: "ip13", category: "phone", quantity: "10", purchasePrice: "1000", sellPrice: "1200"}
];

const tableBody = document.getElementById("productTableBody");

const searchInput = document.getElementById("searchInput");
const searchBtn = document.getElementById("searchBtn");
const deleteBtn = document.getElementById("deleteBtn");

const modal = document.getElementById("productModal");
modal.style.display = "none";
const modalTitle = document.getElementById("modalTitle");
const productForm = document.getElementById("productForm");


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


function renderTable() {
    tableBody.innerHTML = "";


    for (let i = 0; i < products.length; i++) {
        const row = document.createElement("tr");
        const product = products[i];

        row.innerHTML = `
    <td>${product.id}</td>
    <td>${product.name}</td>
    <td>${product.code}</td>
    <td>${product.category}</td>
    <td>${product.quantity}</td>
    <td>${product.purchasePrice}</td>
    <td>${product.sellPrice}</td>
    <td class="row-actions">
        <button class="btn btn-ghost btn-small" data-action="edit" data-id="${product.id}">ویرایش</button>
        <button class="btn btn-danger btn-small" data-action="delete" data-id="${product.id}">حذف</button>
    </td>
`;
        tableBody.appendChild(row);
    }
}



function generateNewId() {
    if (products.length === 0) return 1;
    const maxId = Math.max(...products.map(p => p.id));
    return maxId + 1;
}



function openModal(mode, product = null) {

    productForm.reset();


    if (mode === "add") {
        modalTitle.textContent = "add product";
        productIdField.value = "";
    } else {
        modalTitle.textContent = "edit Product";
        productIdField.value = product.id;
        nameField.value = product.name;
        codeField.value = product.code;
        categoryField.value = product.category;
        quantityField.value = product.quantity;
        purchasePriceField.value = product.purchasePrice;
        sellPriceField.value = product.sellPrice;
    }
    modal.style.display = "flex";
}

function closeModal() {
    modal.style.display = "none";}


openAddFormBtn.addEventListener("click", () => openModal("add"));
closeModalBtn.addEventListener("click", () => closeModal());
cancelFormBtn.addEventListener("click", () => closeModal());

productForm.addEventListener("submit", (event) => {
    event.preventDefault();


    const name = nameField.value.trim();
    const code = codeField.value.trim();
    const category = categoryField.value.trim();
    const quantity = Number(quantityField.value);
    const purchasePrice = Number(purchasePriceField.value);
    const sellPrice = Number(sellPriceField.value)

    const isEditMode = productIdField.value !== "";

    if (isEditMode) {
        updateProduct(Number(productIdField.value), {name, code, category, quantity, purchasePrice, sellPrice});
    } else {
        addProduct({name, code, category, quantity, purchasePrice, sellPrice});
    }

    closeModal();
    renderTable(products);
});

function addProduct(data){
    const newProduct = {id: generateNewId() , ...data};
    products.push(newProduct);
}

function updateProduct(id, data){
    const product = products.find(p=> p.id === id);
    if (!product){
        console.log("product not found")
    }
    Object.assign(product, data);

}

function deleteProduct(id){
    products = products.filter(p=> p.id !== id);
}

tableBody.addEventListener("click", (event)=>{

    const button = event.target.closest("button[data-action]");
    if (!button) return;

    const id = Number(button.dataset.id);
    const action = button.dataset.action;

    if (action === "edit"){
        const product = products.find(p=> p.id === id);
        if (product)
        {  openModal("edit" , product)  }
    }

    if (action === "delete"){
        const product = products.find(p => p.id === id);
        const confirmed = confirm(`آیا از حذف «${product.name}» مطمئنید؟`);
        if (confirmed) {
            deleteProduct(id);
            renderTable(products);
        }
    }

});

renderTable(products);