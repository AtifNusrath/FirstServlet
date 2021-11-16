const name = document.getElementById('#name');
let textError = document.getElementsByClassName('.text-error');
name.addEventListener('input',function(){
    let nameRegex = RegExp('^[A-Z][a-z]{3,}$');
    if(nameRegex.test(name)){
        textError.textContent="";
        return;
    }
    else textError.textContent='Name is Incorrect!';
});