var fullnameValue = document.getElementsByName("fullname")[0].value;
var phoneValue = document.getElementsByName("phone")[0].value;
var addressValue = document.getElementsByName("address")[0].value;
var usernameValue = document.getElementsByName("username")[0].value;

function enableEditMode() {
    const fullname = document.getElementsByName("fullname")[0];
    const phone = document.getElementsByName("phone")[0];
    const address = document.getElementsByName("address")[0];
    const username = document.getElementsByName("username")[0];

    fullname.removeAttribute("readonly");
    phone.removeAttribute("readonly");
    address.removeAttribute("readonly");
    username.removeAttribute("readonly");
}

function disableEditMode() {
    const fullname = document.getElementsByName("fullname")[0];
    const phone = document.getElementsByName("phone")[0];
    const address = document.getElementsByName("address")[0];
    const username = document.getElementsByName("username")[0];

    fullname.setAttribute("readonly", "readonly");
    phone.setAttribute("readonly", "readonly");
    address.setAttribute("readonly", "readonly");
    username.setAttribute("readonly", "readonly");
}

function rollbackValue() {
    const fullname = document.getElementsByName("fullname")[0];
    const phone = document.getElementsByName("phone")[0];
    const address = document.getElementsByName("address")[0];
    const username = document.getElementsByName("username")[0];

    fullname.value = fullnameValue;
    phone.value = phoneValue;
    address.value = addressValue;
    username.value = usernameValue;
}

async function saveButton(button, id) {
    const username = document.getElementsByName("username")[0].value;
    const password = document.getElementsByName("password")[0].value;
    const fullname = document.getElementsByName("fullname")[0].value;
    const email = document.getElementsByName("email")[0].value;
    const phone = document.getElementsByName("phone")[0].value;
    const address = document.getElementsByName("address")[0].value;
    const role = "customer";
    const created = document.getElementsByName("created")[0].value;

    const response = await fetch("./user?action=edit", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			id: id,
			username: username,
			password: password,
			fullname: fullname,
			email: email,
			phone: phone,
			address: address,
			role: role,
			created: created
		})
	});
	const result = await response.json();

    if (result === true) {
		const editBtn = document.getElementById("edit");
		const cancelBtn = document.getElementById("cancel");

		editBtn.style.visibility = "visible";
		cancelBtn.style.visibility = "hidden";
		button.style.visibility = "hidden";

		fullnameValue = document.getElementsByName("fullname")[0].value;
		phoneValue = document.getElementsByName("phone")[0].value;
		addressValue = document.getElementsByName("address")[0].value;
		usernameValue = document.getElementsByName("username")[0].value;

		disableEditMode();
		
		alert("Successfully updated information");
	}
	else {
		const error = document.getElementById("error");
		error.textContent = "Something went wrong, please retry later";
	}
}

function cancelButton(button) {
    const editBtn = document.getElementById("edit");
    const saveBtn = document.getElementById("save");

    editBtn.style.visibility = "visible";
    saveBtn.style.visibility = "hidden";
    button.style.visibility = "hidden";

    rollbackValue();
    disableEditMode();
}

function editButton(button) {
    const saveBtn = document.getElementById("save");
    const cancelBtn = document.getElementById("cancel");

    saveBtn.style.visibility = "visible";
    cancelBtn.style.visibility = "visible";
    button.style.visibility = "hidden";

    enableEditMode();
}