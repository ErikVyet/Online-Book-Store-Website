var nameValue = null;
var descriptionValue = null;

function enableControls() {
    const nameInput = document.getElementsByName('name')[0];
    const descriptionInput = document.getElementsByName('description')[0];

    nameInput.readOnly = false;
    descriptionInput.readOnly = false;

    nameValue = nameInput.value;
    descriptionValue = descriptionInput.value;
}

function disableControls() {
    const nameInput = document.getElementsByName('name')[0];
    const descriptionInput = document.getElementsByName('description')[0];

    nameInput.readOnly = true;
    descriptionInput.readOnly = true;

    nameInput.value = nameValue;
    descriptionInput.value = descriptionValue;
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