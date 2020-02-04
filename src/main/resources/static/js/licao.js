
var tempo = new Number(50);

function init(rt, r) {
    var opcoes = document.getElementsByName("palavra");

    for (var i = 0; i <= opcoes.length; i++) {
        if (opcoes[i].value == r) {
            if (rt == r) {
                opcoes[i].checked = true;
                $('#modalAc').modal('show');
                document.getElementById("ac").play();
            }
            else {
                opcoes[i].checked = true;
                $('#modalEr').modal('show');
                document.getElementById("er").play();
            }
        }
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

        if (tempo < 10) {
            relogioDoFaustao.innerText = '00:0' + tempo;
            setTimeout('iniciarCronometro()', 1000);
        }
        else {
            relogioDoFaustao.innerText = '00:' + tempo;
            setTimeout('iniciarCronometro()', 1000);
        }
    }
    else {
        timeout();
    }
}

