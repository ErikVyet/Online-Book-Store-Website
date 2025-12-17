function changeImage(element) {
    const file = element.files[0];
    if (file) {
        const url = URL.createObjectURL(file);
        document.getElementById('image-display-frame').src = url;
    }
}