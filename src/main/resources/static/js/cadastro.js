var erroNome = document.getElementById("erroNome");
var erroSobrenome = document.getElementById("erroSobrenome");
var erroEmail = document.getElementById("erroEmail");
var erroSenha = document.getElementById("erroSenha");
var erroRepSenha = document.getElementById("erroRepSenha");

function classColor() {
    var elemento = document.getElementById("erroAlert");

    if (erroNome.innerHTML == "" && erroSobrenome.innerHTML == "" && erroEmail.innerHTML == "" && erroSenha.innerHTML == "" && erroRepSenha.innerHTML == "") {
        
        elemento.classList.remove('alert');
        elemento.classList.remove('alert-danger');
    }

    else {

        elemento.classList.add('alert');
        elemento.classList.add('alert-danger');
    }
}

function validarNome() {
    var nome = document.getElementById("nome").value;

    if (verificarSePossuiCaracteresInvalidos(nome)) {
        erroNome.innerHTML = "O nome não pode ter caracteres inválidos."
        return false;
    } else {
        erroNome.innerHTML = "";

        if (verificarSePossuiTresCaracteres(nome)) {
            erroNome.innerHTML = "O nome precisa ter no mínimo 3 letras!";
            return false;
        }
        return true;
    }
}

function validarSobrenome() {
    var sobrenome = document.getElementById("sobrenome").value;

    if (verificarSePossuiCaracteresInvalidos(sobrenome)) {
        erroSobrenome.innerHTML = "O sobrenome não pode ter caracteres inválidos."
        return false;
    } else {
        erroSobrenome.innerHTML = "";

        if (verificarSePossuiTresCaracteres(sobrenome)) {
            erroSobrenome.innerHTML = "O sobrenome precisa ter no mínimo 3 letras!";
            return false;
        }
        return true;
    }
}

function validarEmail() {
    var email = document.getElementById("email").value;


    if (email.indexOf('@') == -1 || email.indexOf('.') == -1) {
        erroEmail.innerHTML = "Email inválido.";
        return false;
    } else {
        erroEmail.innerHTML = "";
        return true;
    }
}

function validarSenha() {
    var senha = document.getElementById("senha").value;


    if (verificarSePossuiCincoCaracteres(senha)) {
        erroSenha.innerHTML = "A senha deve ter no mínimo 5 caracteres.";
        return false;
    } else {
        erroSenha.innerHTML = "";
        return true;
    }
}

function validarRepetirSenha() {
    var senha = document.getElementById("senha").value;
    var repSenha = document.getElementById("repSenha").value;


    if (senha != repSenha) {
        erroRepSenha.innerHTML = "As senhas estão diferentes!";
        return false;
    } else {
        erroRepSenha.innerHTML = "";
        return true;
    }
}


function verificarSePossuiTresCaracteres(texto) {
    return texto.trim().length < 3;
}

function verificarSePossuiCincoCaracteres(texto) {
    return texto.trim().length < 5;
}

function verificarSePossuiCaracteresInvalidos(texto) {
    var caracteresPermitidos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ";

    for (i = 0; i < texto.length; i++) {
        if (caracteresPermitidos.indexOf(texto.charAt(i)) == -1) {
            return true;
        }
    }

    return false;
}

function verificarCampos() {
    return validarNome() && validarSobrenome() && validarEmail() && validarSenha() && validarRepetirSenha();
}