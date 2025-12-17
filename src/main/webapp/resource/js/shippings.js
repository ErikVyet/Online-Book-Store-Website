var tableRow = null;
var btnUpdate = null;
var tempStatus = null;

function dragUpdateDialog() {
    const dialog = document.getElementsByClassName("shipping-update-form")[0];
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
    const dialog = document.getElementsByClassName("shipping-update-form")[0];
    dialog.style.display = "none";
}

function showUpdateDialog(button, shippingId) {
	tableRow = button.parentNode.parentNode;
	const shippingStatus = tableRow.cells[3].textContent;
	const shippingDate = tableRow.cells[5].textContent;
	
	document.getElementsByName("shipping-id")[0].value = shippingId;
	document.getElementsByName("shipping-status")[0].value = shippingStatus;
	if (shippingStatus != "Delivered" && shippingStatus != "Cancelled") {
		document.getElementsByClassName("shipping-info-group")[2].style.visibility = "visible";
	}
	document.getElementsByName("shipping-date")[0].value = shippingDate;
	
    const dialog = document.getElementsByClassName("shipping-update-form")[0];
    dialog.style.display = "flex";
	
	tempStatus = shippingStatus;
	btnUpdate = button;
}

function shippingStatusChanges() {
	const shippingDateControl = document.getElementsByClassName("shipping-info-group")[2];
	
	const shippingStatus = document.getElementsByName("shipping-status")[0].value;
	if (shippingStatus == "Cancelled") {
		shippingDateControl.style.visibility = "hidden";
	}
	else {
		shippingDateControl.style.visibility = "visible";
	}
}

async function updateShippingStatus() {
	const shippingId = document.getElementsByName("shipping-id")[0].value;
	const shippingStatus = document.getElementsByName("shipping-status")[0].value;
	const shippingDate = document.getElementsByName("shipping-date")[0].value;
	
	if (shippingStatus != "Pending") {
		if (shippingDate == "" && shippingStatus != "Cancelled") {
			alert("Please fill estimated date");
			return;
		}
		var date = new Date(shippingDate);
		var currentDate = new Date();
		if (currentDate > date && shippingStatus != "Cancelled") {
			alert("Please fill a valid estimated date");
			return;
		}
		
		let shipping = {
			id: shippingId,
			orderId: null,
			method: null,
			status: shippingStatus,
			shipped_date: shippingDate == "" ? null : shippingDate
		};
		
		const response = await fetch("./shipping?action=updateStatus", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: JSON.stringify(shipping)
		});
		const result = await response.json();
		if (result == true) {
			tableRow.cells[3].textContent = shippingStatus;
			tableRow.cells[5].textContent = shippingDate == "" ? "NULL" : shippingDate;
			if (shippingStatus == "Cancelled" || shippingStatus == "Delivered") {
				btnUpdate.setAttribute("disabled", "disabled");
			}
			alert("Successfully updated shipping' status");
			hideUpdateDialog();
		}
		else {
			alert("Something went wrong, please try again later");	
		}
	}
	else {
		document.getElementsByName("shipping-status")[0].value = tempStatus;
		alert("Can't rollback to Pending");
	}
}