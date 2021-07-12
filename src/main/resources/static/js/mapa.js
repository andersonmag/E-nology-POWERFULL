const txtLevel = document.querySelector('#txtLevel');
const txtTitle = document.querySelector('#txtTitle');
const txtContent = document.querySelector('#txtContent');
const lessonId = document.querySelector('#lessonId');

const fases = ["Ada Lovelace", "Partes do computador", "Bill Gates", "Javascript", "Informática Básica"];
const [txtOne, txtTwo, txtThree, txtFour, txtFive] = fases;

const titles = ["First Content", "Second Content", "Third Content", "Fourth Content", "Fifth Content"];
const [title1, title2, title3, title4, title5] = titles;

const contents = ["Nesta fase você aprenderá sobre uma das pessoas mais famosas da história da computação: Ada Lovelace!",
"Você conhece as partes do computador em inglês? Teste seus conhecimentos e descubra! ",
"Você conhece o Bill Gates? Ele criou na década de 1970 uma das empresas mais conhecidas da área de informática. Nesta fase você descobrirá várias novidades sobre ele! ",
"Você sabe programar usando JavaScript? Teste seus conhecimentos sobre os comandos de JavaScript em inglês!",
"Você conseguiria usar um editor de texto ou um navegador web em inglês? Teste seus conhecimentos e descubra!"];
const [content1, content2, content3, content4, content5] = contents;

const changeContent = (pode, id) => {
    console.log(pode)
    if (pode) {
        switch (id) {
            case 1:
                txtTitle.innerHTML = title1;
                txtLevel.innerHTML = txtOne;
                txtContent.innerHTML = content1;
                break;
            case 2:
                txtTitle.innerHTML = title2;
                txtLevel.innerHTML = txtTwo;
                txtContent.innerHTML = content2;
                break;
            case 3:
                txtTitle.innerHTML = title3;
                txtLevel.innerHTML = txtThree;
                txtContent.innerHTML = content3;
                break;
            case 4:
                txtTitle.innerHTML = title4;
                txtLevel.innerHTML = txtFour;
                txtContent.innerHTML = content4;
                break;
            case 5:
                txtTitle.innerHTML = title5;
                txtLevel.innerHTML = txtFive;
                txtContent.innerHTML = content5;
                break;
            default:
                break;
        }

        lessonId.href = `/studies/${id}/practice`;
        mostrarModal();
    }
}

const mostrarModal = () => {
    $("#exampleModalCenter").modal('toggle');
}

const acessarJogo = (pode, id) => {

    const jogo = document.querySelector(`#${id}`);
    if (pode) {
        switch (id) {
            case 'game1':
                jogo.href = '/games/mingle-ng';
                break;
            case 'game2':
                jogo.href = '/games/yamato-s-future';
                break;

            default:
                break;
        }
    }
}