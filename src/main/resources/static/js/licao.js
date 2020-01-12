
var tempo = new Number(60);

function iniciarCronometro() {

    if ((tempo - 1) >= 0) {

        tempo = tempo - 1;

        if (tempo < 10) {
            relogioDoFaustao.innerText = '00:0' + tempo;
            setTimeout('iniciarCronometro()', 1000);

        } else {
            relogioDoFaustao.innerText = '00:' + tempo;
            setTimeout('iniciarCronometro()', 1000);

        }
    }
}