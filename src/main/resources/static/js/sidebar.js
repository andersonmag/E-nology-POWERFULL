
var links = document.getElementsByName("link");

for (var i = 0; i < links.length; i++) {

    if (links[i].href == location.href + "#"){
        links[i].classList.add('ativo');
        break;
    }
        
}

function esconderSidebar() {
    if (document.getElementById("sidebar").style.width == "200px") {

        document.getElementById("sidebar").style.width = "0";
    }

}

function alterarSidebar() {

    if (document.getElementById("sidebar").style.width == "200px") {
        document.getElementById("sidebar").style.width = "0";
    }
    else {

        document.getElementById("sidebar").style.width = "200px";
    }
}

