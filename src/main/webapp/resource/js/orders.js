var button = null;
var tableRow = null;

function dragUpdateDialog() {
    const dialog = document.getElementsByClassName("order-update-form")[0];
    let isDragging = false;
    let offsetX, offsetY;

    dialog.addEventListener("mousedown", (e) => {
        isDragging = true;
        offsetX = e.clientX - dialog.offsetLeft;
        offsetY = e.clientY - dialog.offsetTop;
        dialog.style.cursor = "grabbing";
    });

    document.addEventListener("mouseup", () => {
        isDragging = false;
        dialog.style.cursor = "grab";
    });

    document.addEventListener("mousemove", (e) => {
        if (isDragging) {
            dialog.style.left = `${e.clientX - offsetX}px`;
            dialog.style.top = `${e.clientY - offsetY}px`;
        }
    });
}

function hideUpdateDialog() {
    const dialog = document.getElementsByClassName("order-update-form")[0];
    dialog.style.display = "none";
}

async function showUpdateDialog(btn, orderId) {
	const response = await fetch("./order?action=getInfo", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: orderId
	});
	const order = await response.json();
	
	const idInput = document.getElementsByName("order-id")[0];
	idInput.value = order.id;
	const statusInput = document.getElementsByName("order-status")[0];
	statusInput.value = order.status;
	
    const dialog = document.getElementsByClassName("order-update-form")[0];
    dialog.style.display = "flex";
	
	button = btn;
	tableRow = btn.parentNode.parentNode;
}

async function updateOrderStatus() {
	const orderId = document.getElementsByName("order-id")[0].value;
	const orderStatus = document.getElementsByName("order-status")[0].value;
	if (orderStatus != "Pending") {
		if (confirm(`Are you sure you want to update order' status to ${orderStatus}?`)) {
			const response = await fetch(`./order?action=updateStatus&orderId=${orderId}`, {
				method: "POST",
				headers: { "Content-Type" : "application/json" },
				body: orderStatus
			});
			const result = await response.json();
			
			if (result == true) {
				button.setAttribute("disabled", "disabled");
				tableRow.cells[3].textContent = orderStatus;
				alert(`Successfully updated order' status to ${orderStatus}`);
				hideUpdateDialog();
			}
			else {
				alert("Something went wrong, please try again later");
			}
		}
		return;
	}
	hideUpdateDialog();
}