var tempo = new Number(61);

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
}

function mudarButao() {
    butao.disabled = false;
}

function timeout() {
    $('#exampleModalCenter').modal('show');
    document.getElementById("timeout").play();
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

        if (!$('#modalEr').hasClass('show') && !$('#modalAc').hasClass('show')) {
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
