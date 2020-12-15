let classId = "";
let schoolId = "";

let activeSchoolButton;
let activeClassButton;
let activeBookButton;

let arrayOfBooks;

let booksContainer = $("#books");

let bookExtension = $("#book_extension");
let bookName = $("#book_name");
let bookSize = $("#book_size");
let bookLocation = $("#book_location");
let bookVisibility = $("#book_visibility");
let bookSubmit = $("#book_save");

let buttonCountBooks = $("#count_books");

let counterCont = $('#counter');

let bookModel = {
    id: "",
    extension: "",
    isVisible: false,
    location: "",
    name: "",
    size: ""
};

let bookSelected = Object.assign({}, bookModel);

let db = firebase.firestore();

const getWebSite = function(url){
    let l = url.substr(url.indexOf("://")+3, url.length);
    return l.substr(0, l.indexOf("/"));
};

const init = ()=>{
    arrayOfBooks = [];
    if(classId !== "" && schoolId !== "" ){
        db.collection(`shcools/${schoolId}/classes/${classId}/books`).orderBy("name").get().then(data=>{
            let view = `<h4>Всего учебников: ${data.size}</h4>`;
            let index = 0;
            data.forEach((item) => {
                let i = item.data();
                view += `<div class="card">
                            <div class="card-body book-click" data-id="${index++}">
                                ${i.name}
                                 <br>
                                 <i>
                                 <small style="font-weight: bold">
                                    ${i.size} | 
                                    ${i.extension} | 
                                    ${(i.isVisible)? '<span style="color: green">опубликован</span>' : '<span style="color: red">черновик</span>'} | 
                                    ${getWebSite(i.location)}
                                </small></i></div></div><br>`;
                i.id = item.id;
                arrayOfBooks.push(i);
            });
            booksContainer.html(view);
            initBookClickable();

        });
    }
};
buttonCountBooks.click(e => {
    let counter = 0;
    db.collection("shcools").get().then(data=>{
        data.forEach(item =>{
            for (let i = 1; i <= 11; i++){
                db.collection(`shcools/${item.id}/classes/${i}/books`).get().then(e =>{
                    counter += e.size;
                    counterCont.html(counter);
                    console.log(counter);
                });
            }
        })
    });
});
const select = book => {
    bookSelected = Object.assign({}, book);
    bookExtension.val(book.extension);
    bookLocation.val(book.location);
    bookName.val(book.name);
    bookSize.val(book.size);
    bookVisibility.prop("checked", book.isVisible)
};
$(".card-click-school").click(e=>{
    try{
        activeSchoolButton.className = activeSchoolButton.className.replace(" active", "");
    }catch (e){}
    schoolId = e.target.dataset.id;
    activeSchoolButton = e.target;
    e.target.className = e.target.className + " active";
    init();
});
$(".card-click-class").click(e=>{
    try{
        activeClassButton.className = activeClassButton.className.replace(" active", "");
    }catch (e){}
    classId = e.target.innerHTML.trim();
    activeClassButton = e.target;
    e.target.className = e.target.className + " active";
    init();
});
const initBookClickable = ()=>{
    $(".book-click").click(e=>{
        try{
            activeBookButton.className = activeBookButton.className.replace(" active", "");
        }catch (e){}
        activeBookButton = e.target;
        e.target.className = e.target.className + " active";
        select(arrayOfBooks[e.target.dataset.id]);
    });
};
$("#clear_form").click(() => {
    select(bookModel)
});
$("#delete_form").click(() => {
    let r = confirm("Удалить?");
    if(bookSelected.id !== "" && r){
        db.collection(`shcools/${schoolId}/classes/${classId}/books`).doc(bookSelected.id).delete().then(e=>{
            init();
            toastr.success("успешно удалено");
            select(bookModel);
        });
    }
});
bookSubmit.click(() => {
    try{
        bookSelected.extension = bookExtension.val();
        bookSelected.location = bookLocation.val();
        bookSelected.name = bookName.val();
        bookSelected.size = bookSize.val();
        bookSelected.isVisible = bookVisibility.prop("checked");
        toastr.warning("обработка ...");
        if(bookSelected.id === ""){
            db.collection(`shcools/${schoolId}/classes/${classId}/books`).add(bookSelected).then(e => {
                init();
                toastr.success("Добавлена новая книга");
            }).catch(function () {
                toastr.error("Не добавлено");
            });
        }else{
            db.collection(`shcools/${schoolId}/classes/${classId}/books`).doc(bookSelected.id).set(bookSelected).then(e => {
                init();
                toastr.success("Книга обновлена");
            }).catch(function () {
                toastr.error("Не добавлено");
            });
        }
    }catch (e) {}
});
$("#clear_name").click(function () {
    bookName.val(clearText(bookName.val()))
});
$("#clear_size").click(function () {
    bookSize.val(clearText(bookSize.val()))
});
$("#put_pdf").click(function () {
    bookExtension.val("pdf");
});
const clearText = (text)=>{
    return text.substr(0, text.indexOf("Подробнее https"));
};

