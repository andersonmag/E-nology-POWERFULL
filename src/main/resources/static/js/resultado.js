var icone = document.getElementById("icone");
var pontos = document.getElementById("pontos").textContent;

if(pontos > 280){
    icone.classList.remove('fa-star-half');
    icone.classList.add('fa-star');
}