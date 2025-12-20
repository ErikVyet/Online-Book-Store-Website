function alertGuest() {
	if (confirm("Login to use this feature")) {
		window.location.href = './login.jsp';
	}
}

async function changeActiveBookFromRecommended(index) {
	var books = document.getElementsByClassName('discover-recommended-book');
	var image = books[index].children[0];

	const response = await fetch("book?action=getBookById", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: image.alt
	});
	const book = await response.json();

	const activeImage = document.getElementById('active-book-image');
	const activeTitle = document.getElementById('active-book-title');
	const activeAuthor = document.getElementById('active-book-author');
	const activeRating = document.getElementById('active-book-rating');
	const activeDescription = document.getElementById('active-book-description');
	const activeBookDetail = document.getElementById('active-book-detail');

	activeImage.alt = book.id;
	activeImage.src = book.image;
	activeTitle.textContent = book.title;
	activeAuthor.textContent = book.author;
	activeRating.textContent = book.rating;
	activeDescription.value = book.description;
	activeBookDetail.href = './book?action=detail&id=' + book.id;
}

async function changeActiveBookFromCategory(index) {
	var books = document.getElementsByClassName('discover-category-book');
	var image = books[index].children[0];
	
	const response = await fetch("book?action=getBookById", {
		method: "POST",
		headers: { "Content-Type" : "application/json" },
		body: image.alt
	});
	const book = await response.json();
	
	const activeImage = document.getElementById('active-book-image');
	const activeTitle = document.getElementById('active-book-title');
	const activeAuthor = document.getElementById('active-book-author');
	const activeRating = document.getElementById('active-book-rating');
	const activeDescription = document.getElementById('active-book-description');
	const activeBookDetail = document.getElementById('active-book-detail');
	
	activeImage.alt = book.id;
	activeImage.src = book.image;
	activeTitle.textContent = book.title;
	activeAuthor.textContent = book.author;
	activeRating.textContent = book.rating;
	activeDescription.value = book.description;
	activeBookDetail.href = './book?action=detail&id=' + book.id;
	
}

async function chooseCategory(button, id) {
	event.preventDefault();
	var response;
	if (id != -1) {
		response = await fetch("book?action=getBooksByCategory", {
			method: "POST",
			headers: { "Content-Type" : "application/json" },
			body: id
		});
	}
	else {
		response = await fetch("book?action=getAllBook", {
			method: "POST"
		});
	}
	const books = await response.json();
	
	const main = document.getElementsByClassName('discover-categories-container')[0];
	document.getElementsByClassName('discover-category-book-list')[0].remove();
	
	var view = document.createElement('div');
	view.className = 'discover-category-book-list';
	main.appendChild(view);
	
	for (let i = 0; i < books.length; i++) {
		const container = document.createElement('div');
		container.className = 'discover-category-book';
		container.title = books[i].title;
		container.onclick = () => { changeActiveBookFromCategory(i) };
		
		var image = document.createElement('img');
		image.src = books[i].image;
		image.alt = books[i].id;
		container.appendChild(image);
		
		var title = document.createElement('p');
		title.textContent = books[i].title;
		container.appendChild(title);
		
		var author = document.createElement('p');
		author.textContent = books[i].author;
		container.appendChild(author);	
		
		view.appendChild(container);
	}
	
	var buttons = document.getElementsByClassName('discover-categories-option');
	for (let j = 0; j < buttons.length; j++) {
		buttons[j].style.color = 'rgb(0, 0, 87)';
		buttons[j].style.backgroundColor = '#c8ddff';
	}
	
	button.style.color = 'white';
	button.style.backgroundColor = '#0d6efd';
	
}