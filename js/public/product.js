async function fetchProducts(type) {
    const response = await fetch("http://localhost:8080/findAvailableProducts?type=" + type);
    return await response.json();
}

export function showProducts() {
    fetchProducts("gadget").then(gadgets => {
        const productsDiv = document.getElementById("products");
        const rows = gadgets.map(gadget => `<div class="row"><div class="col bg-light text-black">${gadget.id}</div><div class="col bg-light text-black">${gadget.name}</div></div>`).join("\n");
        const header = '<div class="row"><div class="col bg-secondary text-white">Id</div><div class="col bg-secondary text-white">Name</div></div>';
        productsDiv.innerHTML = productsDiv.innerHTML + `${header}\n${rows}`
    });
}
