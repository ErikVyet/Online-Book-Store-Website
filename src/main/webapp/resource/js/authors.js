async function deleteAuthor(tr, authorId) {
	if (confirm(`Are you sure you want to delete this author (id: ${authorId})?`)) {
		const response = await fetch("./author?action=delete", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: authorId
		});
		const result = await response.json();
		
		if (result === true) {
			const table = document.getElementsByClassName("author-manager-table")[0];
			table.deleteRow(tr.rowIndex);		
		}
		else {
			alert("Something went wrong, please try again later");
		}
	}
	
}

async function searchAuthors() {
	const search = document.getElementsByName("search")[0];
	const content = search.value;
	const response = await fetch("./author?action=search", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			content: content
		})
	});
	const authors = await response.json();
	
	const table = document.getElementsByClassName("author-manager-table")[0];
	var tbody = table.getElementsByTagName("tbody")[0];
	table.removeChild(tbody);
	tbody = document.createElement("tbody");
	
	for (let i = 0; i < authors.length; i++) {
		const tr = document.createElement("tr");
		const cells = [];
		for (let j = 0; j < 6; j++) {
			const cell = tr.insertCell(j);
			cells.push(cell);
		}
		
		cells[0].textContent = authors[i].id;
		cells[1].textContent = authors[i].name;
		cells[2].textContent = authors[i].bio;
		const date = new Date(authors[i].birth);
		const formatDate = date.getFullYear() + "-" + String(date.getMonth() + 1).padStart(2, "0") + "-" + String(date.getDate()).padStart(2, "0");
		cells[3].textContent = formatDate;
		cells[4].textContent = authors[i].country;
		
		const aDetail = document.createElement("a");
		aDetail.href = `./author?action=detail&id=${authors[i].id}`;
		aDetail.textContent = "Detail";
		const aDelete = document.createElement("a");
		aDelete.onclick = () => { deleteAuthor(tr, authors[i].id) };
		aDelete.textContent = "Delete";
		cells[5].appendChild(aDetail);
		cells[5].innerHTML += " ";
		cells[5].appendChild(aDelete); 
		
		tbody.appendChild(tr);
	}
	table.appendChild(tbody);
	search.value = "";
}