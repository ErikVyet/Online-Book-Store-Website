var title = null;
var category = null;
var author = null;
var publisher = null;
var quantity = null;
var price = null;
var description = null;
var publication = null;
var image = null;

function changeImage(element) {
    const file = element.files[0];
    if (file) {
        const url = URL.createObjectURL(file);
        document.getElementById('image-display-frame').src = url;
    }
}

function enableControls() {
    const titleInput = document.getElementsByName('title')[0];
    const categoryInput = document.getElementsByName('category')[0];
    const authorInput = document.getElementsByName('author')[0];
    const publisherInput = document.getElementsByName('publisher')[0];
    const quantityInput = document.getElementsByName('quantity')[0];
    const priceInput = document.getElementsByName('price')[0];
    const descriptionInput = document.getElementsByName('description')[0];
    const publicationInput = document.getElementsByName('publication')[0];
    const imageInput = document.getElementsByName('image')[0];
    const imageDisplay = document.getElementById('image-display-frame');

    title = titleInput.value;
    category = categoryInput.value;
    author = authorInput.value;
    publisher = publisherInput.value;
    quantity = quantityInput.value;
    price = priceInput.value;
    description = descriptionInput.value;
    publication = publicationInput.value;
    image = imageDisplay.src;
    
    titleInput.readOnly = false;
    categoryInput.disabled = false;
    authorInput.disabled = false;
    publisherInput.disabled = false;
    quantityInput.readOnly = false;
    priceInput.readOnly = false;
    descriptionInput.readOnly = false;
    publicationInput.readOnly = false;
    imageInput.disabled = false;
}

function disableControls() {
    const titleInput = document.getElementsByName('title')[0];
    const categoryInput = document.getElementsByName('category')[0];
    const authorInput = document.getElementsByName('author')[0];
    const publisherInput = document.getElementsByName('publisher')[0];
    const quantityInput = document.getElementsByName('quantity')[0];
    const priceInput = document.getElementsByName('price')[0];
    const descriptionInput = document.getElementsByName('description')[0];
    const publicationInput = document.getElementsByName('publication')[0];
    const imageInput = document.getElementsByName('image')[0];
    const imageDisplay = document.getElementById('image-display-frame');

    titleInput.value = title;
    categoryInput.value = category;
    authorInput.value = author;
    publisherInput.value = publisher;
    quantityInput.value = quantity;
    priceInput.value = price;
    descriptionInput.value = description;
    publicationInput.value = publication;
    imageInput.value = "";
    imageDisplay.src = image;

    titleInput.readOnly = true;
    categoryInput.disabled = true;
    authorInput.disabled = true;
    publisherInput.disabled = true;
    quantityInput.readOnly = true;
    priceInput.readOnly = true;
    descriptionInput.readOnly = true;
    publicationInput.readOnly = true;
    imageInput.disabled = true;
}

function editMode() {
    const btnSave = document.getElementById('btnSave');
    const btnCancel = document.getElementById('btnCancel');
    const btnEdit = document.getElementById('btnEdit');
    const btnBack = document.getElementById('btnBack');

    btnSave.style.display = 'inline-block';
    btnCancel.style.display = 'inline-block';
    btnEdit.style.display = 'none';
    btnBack.style.display = 'none';

    enableControls();
}

function viewMode() {
    const btnSave = document.getElementById('btnSave');
    const btnCancel = document.getElementById('btnCancel');
    const btnEdit = document.getElementById('btnEdit');
    const btnBack = document.getElementById('btnBack');

    btnSave.style.display = 'none';
    btnCancel.style.display = 'none';
    btnEdit.style.display = 'inline-block';
    btnBack.style.display = 'inline-block';

    disableControls();
}