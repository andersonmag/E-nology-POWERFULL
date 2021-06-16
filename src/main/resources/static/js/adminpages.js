
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

function changeSelection() {
    let opt = document.getElementById("selection").value;
    let fases = document.getElementById("fases");
    let ranking = document.getElementById("ranking");

    if (opt == "fases") {
        changeClassList(ranking, "show", "hide");
        changeClassList(fases, "hide", "show");
    } else if (opt == "ranking") {
        changeClassList(fases, "show", "hide");
        changeClassList(ranking, "hide", "show");
    }
}

function changeClassList(item, prev, cur) {
    item.classList.remove(prev);
    item.classList.add(cur);
}