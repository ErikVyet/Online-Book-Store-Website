function showRatingDialog() {
    var ratingDialog = document.getElementsByClassName('rating-dialog')[0];
    ratingDialog.style.animation = 'fadeIn 0.15s ease-in-out';
    ratingDialog.style.visibility = 'visible';

    var putInCartDialog = document.getElementsByClassName('put-in-cart-dialog')[0];
    if (putInCartDialog.style.visibility === 'visible') {
        hideConfirmDialog();
    }
}

function hideRatingDialog() {
    var dialog = document.getElementsByClassName('rating-dialog')[0];
    dialog.style.animation = 'fadeOut 0.15s ease-in-out';
    dialog.style.visibility = 'hidden';
}

function showConfirmDialog() {
    var putInCartDialog = document.getElementsByClassName('put-in-cart-dialog')[0];
    putInCartDialog.style.animation = 'fadeIn 0.15s ease-in-out';
    putInCartDialog.style.visibility = 'visible';
    
    var ratingDialog = document.getElementsByClassName('rating-dialog')[0];
    if (ratingDialog.style.visibility === 'visible') {
        hideRatingDialog();
    }
}

function hideConfirmDialog() {
    var dialog = document.getElementsByClassName('put-in-cart-dialog')[0];
    dialog.style.animation = 'fadeOut 0.15s ease-in-out';
    dialog.style.visibility = 'hidden';
}

function alertGuest() {
	if (confirm("Login to use this feature")) {
		window.location.href = './login.jsp';
	}
}

async function submitRating(bookId, userId) {
	const point = document.getElementsByName("rate")[0].value;
	
	const response = await fetch(`./book?action=rating&rate=${point}`, {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			bookId: bookId,
			userId: userId
		})
	});
	const newRating = await response.json();
	
	document.getElementById("point").textContent = newRating;
	
	hideRatingDialog();
}

async function addItemToCart(bookId, userId) {
	const cartId = document.getElementsByName("cart")[0].value;
	const quantity = document.getElementsByName("quantity")[0].value;
	if (cartId == 0) {
		const response = await fetch(`./cart?action=create&userId=${userId}&bookId=${bookId}`, {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: quantity
		});
		const result = await response.json();
		if (result === true) {
			alert("Successfully added item to a new created cart");
		}
		else {
			alert("Something went wrong, please retry later");
		}
	}
	else {
		const response = await fetch(`./cart?action=add&quantity=${quantity}`, {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: JSON.stringify({
				cartId: cartId,
				bookId: bookId,
			})
		});
		const result = await response.json();
		if (result === true) {
			alert("Successfully added item to existed cart");
		}
		else {
			alert("Something went wrong, please retry later");
		}
	}
	hideConfirmDialog();
	window.location.href = "./cart.jsp";
}