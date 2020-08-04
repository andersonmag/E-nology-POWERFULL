let erroNome = document.getElementById("erroNome");
let erroSobrenome = document.getElementById("erroSobrenome");
let erroEmail = document.getElementById("erroEmail");
let erroSenha = document.getElementById("erroSenha");
let erroRepSenha = document.getElementById("erroRepSenha");
let erroEmailRep = document.getElementById("erroEmailRep");

if (erroEmailRep.textContent != "") {
    classColor();
}

function classColor() {
    let elemento = document.getElementById("erroAlert");

    if (erroNome.innerHTML == "" && erroSobrenome.innerHTML == ""
        && erroEmail.innerHTML == "" && erroSenha.innerHTML == ""
        && erroRepSenha.innerHTML == "" && erroEmailRep.innerHTML == "") {

        elemento.classList.remove('alert');
        elemento.classList.remove('alert-danger');
    } else {
        elemento.classList.add('alert');
        elemento.classList.add('alert-danger');
    }
}

function validarNome() {
    const nome = document.getElementById("nome").value;

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
    const sobrenome = document.getElementById("sobrenome").value;

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
    const email = document.getElementById("email").value;

    if (email.indexOf('@') == -1 || email.indexOf('.') == -1) {
        erroEmail.innerHTML = "Email inválido.";
        return false;
    } else {
        erroEmail.innerHTML = "";
        return true;
    }
}

function validarSenha() {
    const senha = document.getElementById("senha").value;

    if (verificarSePossuiMenosQueCincoCaracteres(senha)) {
        erroSenha.innerHTML = "A senha deve ter no mínimo 5 caracteres.";
        return false;
    } else {
        erroSenha.innerHTML = "";
        return true;
    }
}

function validarRepetirSenha() {
    const senha = document.getElementById("senha").value;
    const repSenha = document.getElementById("repSenha").value;

    if (senha != repSenha) {
        erroRepSenha.innerHTML = "As senhas estão diferentes!";
        return false;
    } else {
        erroRepSenha.innerHTML = "";
        return true;
    }
}

function validarNovaSenha() {
    const novaSenha = document.getElementById("novaSenha").value;
    const senhaAtual = document.getElementById("senhaAtual").value;

    if (senhaAtual != "" && novaSenha == "") {
        erroNovaSenha.innerHTML = "Digite a nova Senha!";
        return false;
    }
    else if (novaSenha != "" && senhaAtual == "") {
        erroSenhaAtual.innerHTML = "Digite a Senha!";
        return false;
    }
    else if(verificarSePossuiMenosQueCincoCaracteres){
        erroNovaSenha.innerHTML = "A nova senha precisa ter 5 caracteres!";
        return false;
    }
    else {
        erroNovaSenha.innerHTML = "";
        return true;
    }

}

function verificarSePossuiTresCaracteres(texto) {
    return texto.trim().length < 3;
}

function verificarSePossuiMenosQueCincoCaracteres(texto) {
    return texto.trim().length < 5;
}

function verificarSePossuiCaracteresInvalidos(texto) {
    const caracteresPermitidos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ";

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


function verificarCamposPerfil() {
    return validarNome() && validarSobrenome() && validarEmail() && validarNovaSenha();
}

function validaFormVerificacaoEmail(frm) {
    const codigo = document.getElementById("codigoVerificacao").value;
    let msgErroCod = document.getElementById("msgErroCod");
    const caracteresPermitidos = "0123456789";

    for (i = 0; i < codigo.length; i++) {
        if (caracteresPermitidos.indexOf(codigo.charAt(i)) == -1) {
            return false;
        }
    }

    if (codigo.trim().length < 5) {
        msgErroCod.innerHTML = "Código Inválido!";
        return false;
    }
}

function validaFormAlteracaoSenha(frm) {
    const senha = document.getElementById("senha").value;
    const repSenha = document.getElementById("repSenha").value;

    if (verificarSePossuiMenosQueCincoCaracteres(senha) || senha != repSenha) {
        return false;
    }

}

function liberarBotaoFormAlteracaoSenha() {
    const senha = document.getElementById("senha").value;
    const repSenha = document.getElementById("repSenha").value;

    if (senha == repSenha && !verificarSePossuiMenosQueCincoCaracteres(senha)) {
        document.getElementById("btnConfirmar").disabled = false;
    }
}

function alterarBtnModalEsqueciSenha() {
    let myBtn = document.getElementById("myBtn")

    myBtn.innerHTML = "Enviando .";
    myBtn.disabled = true;
    myBtn.innerHTML += ".";
}