async function deleteCategory(tr, categoryId) {
	if (confirm(`Are you sure you want to delete this category (id: ${categoryId})?`)) {
		const response = await fetch("./category?action=delete", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: categoryId
		});
		const result = await response.json();
		
		if (result === true) {
			const table = document.getElementsByClassName("category-manager-table")[0];
			table.deleteRow(tr.rowIndex);
		}
		else {
			alert("Something went wrong, please try again later");
		}
	}
}

async function searchCategories() {
	const search = document.getElementsByName("search")[0];
	const content = search.value;
	const response = await fetch("./category?action=search", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			content: content
		})
	});
	const categories = await response.json();
	
	const table = document.getElementsByClassName("category-manager-table")[0];
	var tbody = table.getElementsByTagName("tbody")[0];
	table.removeChild(tbody);
	tbody = document.createElement("tbody");
	
	for (let i = 0; i < categories.length; i++) {
		const tr = document.createElement("tr");
		const cells = [];
		for (let j = 0; j < 4; j++) {
			const cell = tr.insertCell(j);
			cells.push(cell);
		}
		
		cells[0].textContent = categories[i].id;
		cells[1].textContent = categories[i].name;
		cells[2].textContent = categories[i].description;
		
		const aDetail = document.createElement("a");
		aDetail.href = `./category?action=detail&id=${categories[i].id}`;
		aDetail.textContent = "Detail";
		const aDelete = document.createElement("a");
		aDelete.onclick = () => { deleteCategory(tr, categories[i].id) };
		aDelete.textContent = "Delete";
		
		cells[3].appendChild(aDetail);
		cells[3].innerHTML += " ";
		cells[3].appendChild(aDelete);
		
		tbody.appendChild(tr);
	}
	table.appendChild(tbody);
	search.value = "";
}