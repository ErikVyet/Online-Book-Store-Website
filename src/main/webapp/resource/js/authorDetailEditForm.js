var nameInput = null;
var bioInput = null;
var birthInput = null;
var countryInput = null;

function enableControls() {
    const authorName = document.getElementsByName('name')[0];
    const authorBio = document.getElementsByName('bio')[0];
    const authorBirth = document.getElementsByName('birth')[0];
    const authorCountry = document.getElementsByName('country')[0];

    authorName.readOnly = false;
    authorBio.readOnly = false;
    authorBirth.readOnly = false;
    authorCountry.readOnly = false;

    nameInput = authorName.value;
    bioInput = authorBio.value;
    birthInput = authorBirth.value;
    countryInput = authorCountry.value;
}

function disableControls() {
    const authorName = document.getElementsByName('name')[0];
    const authorBio = document.getElementsByName('bio')[0];
    const authorBirth = document.getElementsByName('birth')[0];
    const authorCountry = document.getElementsByName('country')[0];

    authorName.readOnly = true;
    authorBio.readOnly = true;
    authorBirth.readOnly = true;
    authorCountry.readOnly = true;

    authorName.value = nameInput;
    authorBio.value = bioInput;
    authorBirth.value = birthInput;
    authorCountry.value = countryInput;
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