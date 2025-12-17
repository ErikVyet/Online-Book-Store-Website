async function disableGetCode() {
	var email = document.getElementsByName("email")[0].value;
	const response = await fetch("./user?action=getCode&req=register", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: email
	});
	const result = await response.json();

	if (result === true) {
		var getCodeButton = document.getElementById("code");
		getCodeButton.disabled = true;
		setTimeout(enableGetCode, 60000);
	}
	else {
		var message = document.getElementById("message");
		message.textContent = "Invalid email";
	}
}

function enableGetCode() {
    var getCodeButton = document.getElementById("code");
    getCodeButton.disabled = false;
}