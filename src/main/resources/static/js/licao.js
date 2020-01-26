
var tempo = new Number(50);

function mudarButao() {
    butao.disabled = false;
}

function timeout() {
    $('#exampleModalCenter').modal('show');
    document.getElementById("music").play();
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

