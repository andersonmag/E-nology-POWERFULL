const txtLevel = document.querySelector('#txtLevel');
const txtTitle = document.querySelector('#txtTitle');
const txtContent = document.querySelector('#txtContent');
const lessonId = document.querySelector('#lessonId');

const fases = ["Introdução", "Fase 2!", "Fase 3!", "Fase 4!", "Fase 5!"];
const [txtOne, txtTwo, txtThree, txtFour, txtFive] = fases;

const titles = ["First Content", "Sisthgfgfh", "Neque porro quisquam", "est qui dolorem ipsum", "LALALALA"];
const [title1, title2, title3, title4, title5] = titles;

const contents = ["Aqui você aprenderá mais sobre JavaScript e poderá exercitar sua leitura " +
    "do inglês e o conteudo aprendido.",
"Chegou ao nível 2! Aqui você aprenderá blá blá blá e mais alguma coisa " +
"bem difícil! Espero que erre tudo :)",
"Lorem ipsum dolor sit amet, consectetur adipiscing " +
"elit. Cras nec elit vulputate, suscipit purus id, rutrum nibh",
"Mauris suscipit, magna sit amet volutpat euismod, leo ligula luctus nunc, a vulputate risus est " +
"at nisi. Phasellus ",
    "Maecenas vulputate, tellus eget aliquet bibendum, lacus turpis cursus orci, et dictum lacus"];
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