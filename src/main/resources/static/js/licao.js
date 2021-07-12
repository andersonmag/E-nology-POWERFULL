var tempo = new Number(61);
let text = document.querySelector("#texto");
let fontSize = 16;

window.onload = function () {
    alterarIconeBateria();
    iniciarCronometro();
};

if (!localStorage.modal) {
    $('#modalLongo').modal('show');
}
localStorage.setItem("modal", "hide");

function init(rt) {
    var opcoes = document.getElementsByName("palavra");

    for (var i = 0; i <= opcoes.length; i++) {
        if (opcoes[i].checked == true) {
            if (opcoes[i].value == rt) {
                $('#modalAc').modal('show');
                document.getElementById("ac").play();
            }
            else {
                $('#modalEr').modal('show');
                document.getElementById("er").play();
            }
        }
    }
}

function alterarIconeBateria() {
    var pontuacao = document.getElementById("pontuacao").textContent;
    var bateria = document.getElementById("bateria");

    switch (parseInt(pontuacao)) {
        case 1:
            bateria.classList.remove('fa-battery-0');
            bateria.classList.add('fa-battery-1');
            break;
        case 2:
            bateria.classList.remove('fa-battery-1');
            bateria.classList.add('fa-battery-2');
            break;
        case 3:
            bateria.classList.remove('fa-battery-2');
            bateria.classList.add('fa-battery-3');
            break;
        default:
            bateria.classList.add('fa-battery-0');
            break;
    }

    // switch (parseInt(pontuacao)) {
    //     case 1:
    //     case 2:
    //     case 3:
    //         bateria.classList.remove('fa-battery-0');
    //         bateria.classList.add('fa-battery-1');
    //         break;
    //     case 4:
    //     case 5:
    //     case 6:
    //         bateria.classList.remove('fa-battery-1');
    //         bateria.classList.add('fa-battery-2');
    //         break;
    //     case 7:
    //     case 8:
    //     case 9:
    //     case 10:
    //         bateria.classList.remove('fa-battery-2');
    //         bateria.classList.add('fa-battery-3');
    //         break;
    //     default:
    //         bateria.classList.add('fa-battery-0');
    //         break;
    // }
}

function mudarButao() {
    butao.disabled = false;
}

function timeout() {
    $('#modalTout').modal('show');
    document.getElementById("timeout").play();
}

function continuarCronometro() {
    $('#modalLongo').modal('hide');
    tempo = tempo + 2;
    iniciarCronometro();
}

function iniciarCronometro() {
    var ampulheta = document.getElementById("ampulheta");

    if ((tempo - 1) >= 0) {
        tempo = tempo - 1;

        switch (tempo) {
            case 25:
                ampulheta.classList.remove('fa-hourglass-1');
                ampulheta.classList.add('fa-hourglass-2');
                break;
            case 1:
                ampulheta.classList.remove('fa-hourglass-2');
                ampulheta.classList.add('fa-hourglass-3');
                break;
        }
        if (!$('#modalEr').hasClass('show') && !$('#modalAc').hasClass('show') && !$('#modalLongo').hasClass('show')) {
            if (tempo < 10) {
                relogioDoFaustao.innerText = '00:0' + tempo;
                setTimeout('iniciarCronometro()', 1000);
            }
            else {
                relogioDoFaustao.innerText = '00:' + tempo;
                setTimeout('iniciarCronometro()', 1000);
            }
        }
    }

    else {
        timeout();
    }
}

function changeFontSize(choice) {
    if(choice == "bigger"){
        if(fontSize < 24) {
            fontSize += 1;
        }
    } else{
        if(fontSize >= 11) {
           fontSize -= 1;
        }
    }
        text.style.fontSize = `${fontSize}pt`;
}
