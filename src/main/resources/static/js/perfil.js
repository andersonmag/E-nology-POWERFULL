
if (document.getElementById("erroSenhaAtual").textContent != "") {

    document.getElementById("nav1").style.display = "none";
    document.getElementById("nav2").style.display = "block";
}

function mudarNav() {

    if (document.getElementById("nav1").style.display == "block") {

        document.getElementById("nav1").style.display = "none";
        document.getElementById("nav2").style.display = "block";
    }

    else {

        document.getElementById("nav2").style.display = "none";
        document.getElementById("nav1").style.display = "block";
    }
}