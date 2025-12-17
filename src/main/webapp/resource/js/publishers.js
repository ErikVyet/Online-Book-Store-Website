async function deletePublisher(tr, publisherId) {
	if (confirm(`Are you sure you want to delete this publisher (id: ${publisherId})?`)) {
		const response = await fetch("./publisher?action=delete", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: publisherId
		});
		const result = await response.json();
		
		if (result === true) {
			const table = document.getElementsByClassName("publisher-manager-table")[0];
			table.deleteRow(tr.rowIndex);
		}
		else {
			alert("Something went wrong, please try again later");
		}
	}
}

async function searchPublishers() {
	const search = document.getElementsByName("search")[0];
	const content = search.value;
	const response = await fetch("./publisher?action=search", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: JSON.stringify({
			content: content
		})
	});
	const publishers = await response.json();
	const table = document.getElementsByClassName("publisher-manager-table")[0];
	var tbody = table.getElementsByTagName("tbody")[0];
	table.removeChild(tbody);
	tbody = document.createElement("tbody");
	
	for (let i = 0; i < publishers.length; i++) {
		const tr = document.createElement("tr");
		const cells = [];
		for (let j = 0; j < 6; j++) {
			const cell = tr.insertCell(j);
			cells.push(cell);
		}
		
		cells[0].textContent = publishers[i].id;
		cells[1].textContent = publishers[i].name;
		cells[2].textContent = publishers[i].address;
		cells[3].textContent = publishers[i].phone;
		cells[4].textContent = publishers[i].email;
		
		const aDetail = document.createElement("a");
		aDetail.href = `./publisher?action=detail&id=${publishers[i].id}`;
		aDetail.textContent = "Detail";
		const aDelete = document.createElement("a");
		aDelete.onclick = () => { deletePublisher(tr, publishers[i].id) };
		aDelete.textContent = "Delete";
		
		cells[5].appendChild(aDetail);
		cells[5].innerHTML += " ";
		cells[5].appendChild(aDelete);
		
		tbody.appendChild(tr);
	}
	table.appendChild(tbody);
	search.value = "";
}