
function mudarDiv() {
    const attention = document.querySelector("#principal");
    const mingle = document.querySelector("#game")

    attention.classList.add("d-none")
    mingle.classList.remove("d-none")
}

var tempo = new Number(6);
function iniciarCronometro() {
    tempo = tempo - 1;

    if (tempo < 2) {
        butao.disabled = false;
        butao.textContent = "Aceito os termos.";
        return true;
    }
    setTimeout('iniciarCronometro()', 1000);
}