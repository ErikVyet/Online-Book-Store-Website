var publisherName = null;
var publisherAddress = null;
var publisherPhone = null;
var publisherEmail = null;

function enableControls() {
    const nameInput = document.getElementsByName('name')[0];
    const addressInput = document.getElementsByName('address')[0];
    const phoneInput = document.getElementsByName('phone')[0];
    const emailInput = document.getElementsByName('email')[0];

    nameInput.readOnly = false;
    addressInput.readOnly = false;
    phoneInput.readOnly = false;
    emailInput.readOnly = false;

    publisherName = nameInput.value;
    publisherAddress = addressInput.value;
    publisherPhone = phoneInput.value;
    publisherEmail = emailInput.value;
}

function disableControls() {
    const nameInput = document.getElementsByName('name')[0];
    const addressInput = document.getElementsByName('address')[0];
    const phoneInput = document.getElementsByName('phone')[0];
    const emailInput = document.getElementsByName('email')[0];

    nameInput.readOnly = true;
    addressInput.readOnly = true;
    phoneInput.readOnly = true;
    emailInput.readOnly = true;

    nameInput.value = publisherName;
    addressInput.value = publisherAddress;
    phoneInput.value = publisherPhone;
    emailInput.value = publisherEmail;
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