
var links = document.getElementsByName("link");

for (var i = 0; i < links.length; i++) {

    if (links[i].href == location.href + "#")
        links[i].classList.add('ativo');
}