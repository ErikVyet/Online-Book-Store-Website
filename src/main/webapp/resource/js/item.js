var preQuantity =  null;
var index = null;

function itemViewMode() {
    const btnEdits = document.getElementsByName("btnEdit");
    const btnSave = document.getElementsByName("btnSave")[index];
    const btnCancel = document.getElementsByName("btnCancel")[index];
    const btnDeletes = document.getElementsByName("btnDelete");
    const quantity = document.getElementsByName("quantity")[index];

    btnEdits.forEach(button => {
        button.style.display = "inline-block";
    });
    btnDeletes.forEach(button => {
        button.style.display = "inline-block";
    });
    btnSave.style.display = "none";
    btnCancel.style.display = "none";

    quantity.readOnly = true;
}

function itemEditMode(parentNode) {
    const row = parentNode.parentNode;
    index = row.rowIndex - 1;
    const btnEdits = document.getElementsByName("btnEdit");
    const btnSave = document.getElementsByName("btnSave")[index];
    const btnCancel = document.getElementsByName("btnCancel")[index];
    const btnDeletes = document.getElementsByName("btnDelete");
    const quantity = document.getElementsByName("quantity")[index];

    btnEdits.forEach(button => {
        button.style.display = "none";
    });

    btnDeletes.forEach(button => {
        button.style.display = "none";
    });

    btnSave.style.display = "inline-block";
    btnCancel.style.display = "inline-block";

    quantity.readOnly = false;
    preQuantity = quantity.value;
}

function cancelChanges(parentNode) {
	const row = parentNode.parentNode;
	index = row.rowIndex - 1;
	const quantity = document.getElementsByName("quantity")[index];
	quantity.value = preQuantity;
	itemViewMode();
}

async function saveChanges(cartId) {
    const quantity = document.getElementsByName("quantity")[index].value;
    const bookId = document.getElementsByName("book")[index].value;
	const response = await fetch("./cart?action=updateItem", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			id: { cartId, bookId },
			quantity: quantity
		})
	});
	const result = await response.json();
	
	if (result != -1) {
		const total = document.getElementById("total");
		total.textContent = result + " VNĐ";
	}
	else {
		alert("Something went wrong, please try again later");
	}
    itemViewMode();
}

async function deleteItem(cartId, bookId, row) {
	if(confirm("Are you sure you want to remove this item from your cart?")) {
		const response = await fetch("./cart?action=deleteItem", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: JSON.stringify({
				cartId: cartId,
				bookId: bookId
			})
		});
		const result = await response.json();
			
		if (result != -1) {
			const table = document.getElementsByTagName("table")[0];
			const quantity = document.getElementById("quantity");
			const total = document.getElementById("total");
			table.deleteRow(row.rowIndex);
			if (table.rows.length == 1) {
				const response2 = await fetch("./cart?action=delete", {
					method: "POST",
					headers: { "Content-Type" : "application/json" },
					body: cartId
				});
				const result2 = await response2.json();
				if (result2 === true) {
					window.location.replace("./cart.jsp");
				}
				else {
					alert("Something went wrong, please relog");
					window.location.replace("./login.jsp");
				}
			}
			else {
				quantity.textContent = table.rows.length - 1;
				total.textContent = result + " VNĐ";
			}
		}
		else {
			alert("Something went wrong, please try again later");	
		}
	}
}