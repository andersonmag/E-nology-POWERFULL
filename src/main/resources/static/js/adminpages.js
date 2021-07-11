
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


function buscarAlunos() {
    var input = document.getElementById("inputBusca");
    var table = document.getElementById("tableAlunos");
    var tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        var td = tr[i].getElementsByTagName("td")[1];

        if (td) {
            var nome = td.textContent || td.innerText;
            if (nome.toUpperCase().indexOf(input.value.toUpperCase()) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }

    }
}


function verificarCampos() {
    return validarAlunosSelecionados() && validaSemestre() && validarNome();
}
const erroListaVazia = document.getElementById("erroListaAlunos")
const erroSemestre = document.getElementById("erroSemestre")
const erroNome = document.getElementById("erroNome")

function validarAlunosSelecionados() {
    const usuariosCheck = document.getElementsByName("usuarios")

    for (let i = 0; i < usuariosCheck.length; i++) {
        if (usuariosCheck[i].checked) {
            erroListaVazia.innerHTML = ""
            return true;
        }
    }

    erroListaVazia.innerHTML = "Selecione o(s) aluno(s)"
    return false;
}

function validaSemestre() {
    const semestre = document.getElementById("semestre").value

    if (semestre == "") {
        erroSemestre.innerHTML = "Selecione o semestre da turma"
        return false;
    }

    erroSemestre.innerHTML = "";
    return true;
}

function validarNome() {
    const nome = document.getElementById("nome").value

    if (nome == "") {
        erroNome.innerHTML = "Informe o nome da turma"
        return false;
    }

    erroNome.innerHTML = "";
    return true;
}


function classColor() {
    let elemento = document.getElementById("erroAlert");

    if (erroListaVazia.innerHTML == "" && erroNome.innerHTML == ""
        && erroSemestre.innerHTML == "") {

        elemento.classList.remove('alert');
        elemento.classList.remove('alert-danger');
    } else {
        elemento.classList.add('alert');
        elemento.classList.add('alert-danger');
    }
}