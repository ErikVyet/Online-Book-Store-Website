var cartId = null;

async function deleteCart(id, button) {
	if (confirm("Are you sure you want to delete this cart?")) {
		const response = await fetch("./cart?action=delete", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: id
		});
		const result = await response.json();
		
		if (result === true) {
			const table = document.getElementsByTagName("table")[0];
			const column = button.parentElement;
			const row = column.parentElement;
			table.deleteRow(row.rowIndex);
		}
		else {
			alert("Something went wrong, please retry later");
		}
	}
}

function resetPaymentControls() {
    document.getElementsByName('method')[0].value = '1';
    document.getElementsByName('visa-number')[0].value = '';
    document.getElementsByName('visa-expiry')[0].value = '';
    document.getElementsByName('visa-code')[0].value = '';
	document.getElementsByName('transport').forEach(option => { option.value = '1' });
    switchMethod(1);
}

function showPaymentDialog(id) {
    const dialog = document.getElementsByClassName('payment-container')[0];
    dialog.style.animation = 'show 0.3s forwards';
    dialog.style.translate = '0%';
    dialog.style.opacity = '1';
	cartId = id;
	switchMethod(1);
}

function hidePaymentDialog() {
    const dialog = document.getElementsByClassName('payment-container')[0];
    dialog.style.animation = 'hide 0.3s forwards';
    dialog.style.translate = '100%';
    dialog.style.opacity = '0';
    resetPaymentControls();
}

function switchMethod(index) {
    const cod = document.getElementsByClassName('cod-container')[0];
    const visa = document.getElementsByClassName('visa-container')[0];
    if (index == 1) {
        cod.style.display = 'inline-flex';
        visa.style.display = 'none';
		cod.action = `./order?action=create&payment=1&cartId=${cartId}`;
    }
    else if (index == 2) {
        cod.style.display = 'none';
        visa.style.display = 'inline-flex';
		visa.action = `./order?action=create&payment=2&cartId=${cartId}`;
    }
}

function checkOnlyNumberInput(evt) {
    let ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
}