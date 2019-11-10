function validarNome(){
    var nome = document.getElementById("nome").value;
    var erroNome = document.getElementById("erroNome");

    if(verificarSePossuiCaracteresInvalidos(nome)){
        erroNome.innerHTML = "O nome não pode ter caracteres inválidos."
        return false;
    }else{
        erroNome.innerHTML = "";

        if(verificarSePossuiTresCaracteres(nome)){
            erroNome.innerHTML = "O nome precisa ter no mínimo 3 letras!";
            return false;
        }
        return true;
    }
}

function validarSobrenome(){
    var sobrenome = document.getElementById("sobrenome").value;
    var erroSobrenome = document.getElementById("erroSobrenome");

    if(verificarSePossuiCaracteresInvalidos(sobrenome)){
        erroSobrenome.innerHTML = "O sobrenome não pode ter caracteres inválidos."
        return false;
    }else{
        erroSobrenome.innerHTML = "";

        if(verificarSePossuiTresCaracteres(sobrenome)){
            erroSobrenome.innerHTML = "O sobrenome precisa ter no mínimo 3 letras!";
            return false;
        }
        return true;
    }
}

function validarEmail(){
    var email = document.getElementById("email").value;
    var erroEmail = document.getElementById("erroEmail");

    if(email.indexOf('@') == -1 || email.indexOf('.') == -1){
        erroEmail.innerHTML = "Email inválido.";
        return false;
    }else{
        erroEmail.innerHTML = "";
        return true;
    }
}

function verificarSePossuiTresCaracteres(texto){
    return texto.trim().length < 3;
}

function verificarSePossuiCaracteresInvalidos(texto){
    var caracteresPermitidos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ";
    
    for(i = 0; i < texto.length; i++){
        if(caracteresPermitidos.indexOf(texto.charAt(i)) == -1){
            return true;
        }
    }

    return false;
}


function verificarCampos(){
    return validarNome() && validarSobrenome() && validarEmail();
}