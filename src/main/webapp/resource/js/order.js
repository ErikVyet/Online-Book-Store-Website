async function cancelOrder(orderId) {
    if (confirm("Are you sure you want to delete this order?")) {
		const response = await fetch(`./order?action=cancel&orderId=${orderId}`, {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
		});
		const result = await response.json();
		
		if (result === true) {
			window.location.reload();
		}
		else {
			alert("Something went wrong, please try again later");
		}
    }
}