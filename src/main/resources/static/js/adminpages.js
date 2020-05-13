
function alterarSidebarr(){
    if (document.getElementById("sidenav").style.width == "200px") {
        document.getElementById("sidenav").style.width = "0";
        document.getElementById("sidebar").style.width = "0";
    }
    else {
        document.getElementById("sidenav").style.width = "200px";
    }
}

var ano = document.getElementById("ano");

ano.value = '' + new Date().getFullYear();