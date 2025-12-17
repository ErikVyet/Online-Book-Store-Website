async function deleteBook(tr, bookId) {
	if (confirm(`Are you sure you want to delete this book (id: ${bookId})?`)) {
		const response = await fetch(`./book?action=delete&id=${bookId}`, {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
		});
		const result = await response.json();
			
		if (result === true) {
			const table = document.getElementsByClassName("book-manager-table")[0];
			table.deleteRow(tr.rowIndex);
		}
		else {
			alert("Something went wrong, please try again later");
		}
	}
}

async function searchBooks() {
	const search = document.getElementsByName("search")[0];
	const content = search.value;
	const response = await fetch("./book?action=search", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			content: content
		})
	});
	const books = await response.json();
	
	const table = document.getElementsByClassName("book-manager-table")[0];
	var tbody = table.getElementsByTagName("tbody")[0];
	table.removeChild(tbody);
	tbody = document.createElement("tbody");
	
	for (let i = 0; i < books.length; i++) {
		var row = document.createElement("tr");
		var columns = [];
		for (let j = 0; j < 6; j++) {
			var column = row.insertCell(j);
			columns.push(column);
		}
		
		columns[0].textContent = books[i].id;
		columns[1].textContent = books[i].title;
		columns[2].textContent = books[i].price + " VNĐ";
		columns[3].textContent = books[i].quantity;
		const publication = new Date(books[i].publication);
		const year = publication.getFullYear();
		const month = publication.getMonth() + 1;
		const day = publication.getDate();
		var formatDate = year + "-" + String(month).padStart(2, "0") + "-" + String(day).padStart(2, "0");
		columns[4].textContent = formatDate;
		
		const aDetail = document.createElement("a");
		aDetail.href = `./book?action=detail&id=${books[i].id}`;
		aDetail.textContent = "Detail";
		const aDelete = document.createElement("a");
		aDelete.onclick = () => { deleteBook(row, books[i].id) };
		aDelete.textContent = "Delete";
		columns[5].appendChild(aDetail);
		columns[5].innerHTML += " ";
		columns[5].appendChild(aDelete);
		
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	search.value = "";
}

async function filterBooks() {
	const filter = document.getElementsByName("filter")[0];
	const filterOption = filter.value;
	const filterOrder = document.getElementsByName("sort")[0].checked;
	const response = await fetch(`./book?action=filter&option=${filterOption}`, {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: filterOrder
	});
	const books = await response.json();
		
	const table = document.getElementsByClassName("book-manager-table")[0];
	var tbody = table.getElementsByTagName("tbody")[0];
	table.removeChild(tbody);
	tbody = document.createElement("tbody");
		
	for (let i = 0; i < books.length; i++) {
		var row = document.createElement("tr");
		var columns = [];
		for (let j = 0; j < 6; j++) {
			var column = row.insertCell(j);
			columns.push(column);
		}
			
		columns[0].textContent = books[i].id;
		columns[1].textContent = books[i].title;
		columns[2].textContent = books[i].price + " VNĐ";
		columns[3].textContent = books[i].quantity;
		const publication = new Date(books[i].publication);
		const year = publication.getFullYear();
		const month = publication.getMonth() + 1;
		const day = publication.getDate();
		var formatDate = year + "-" + String(month).padStart(2, "0") + "-" + String(day).padStart(2, "0");
		columns[4].textContent = formatDate;
			
		const aDetail = document.createElement("a");
		aDetail.href = `./book?action=detail&id=${books[i].id}`;
		aDetail.textContent = "Detail";
		const aDelete = document.createElement("a");
		aDelete.onclick = () => { deleteBook(row, books[i].id) };
		aDelete.textContent = "Delete";
		columns[5].appendChild(aDetail);
		columns[5].innerHTML += " ";
		columns[5].appendChild(aDelete);
			
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	filter.value = "id";
}