
var links = document.getElementsByName("link");
for (var it = 0; it < links.length; it++) {
    if (links[it].href == location.href){
        links[it].classList.add('ativo');
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

