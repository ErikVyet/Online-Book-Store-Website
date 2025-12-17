async function disableGetCode() {
	var email = document.getElementsByName("email")[0].value;
	const response = await fetch("./user?action=getCode&req=recovery", {
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
		var error = document.getElementById("error");
		error.textContent = "Invalid email";
	}
    
}

function enableGetCode() {
    var getCodeButton = document.getElementById("code");
    getCodeButton.disabled = false;
}