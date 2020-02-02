var icone = document.getElementById("icone");
var pontos = document.getElementById("pontos").textContent;

if(pontos / 2 > 500){
    icone.classList.remove('fa-star-half');
    icone.classList.add('fa-star');
}