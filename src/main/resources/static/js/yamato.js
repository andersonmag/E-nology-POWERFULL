const startScreen = document.querySelector("#start");
const tutorialScreen = document.querySelector("#tutorial");
const gameScreen = document.querySelector("#game_question");
const resumeScreen = document.querySelector("#resume");
const questionScreen = document.querySelector("#question");
const gameChoiceScreen = document.querySelector("#game_choice");
const attentionScreen = document.querySelector("#attention");
const background = document.querySelector("#background");
const blueTitles = document.querySelectorAll(".title-blue");
const sidebar = document.querySelector("#sidebar");
const sidebarItens = document.querySelectorAll(".fonte");
const textPrimary = document.querySelectorAll(".text-primary");
const audioIcon = document.querySelector("#audioIcon");
const audio = document.querySelector("#audio");
const alreadyPlayed = document.querySelector("#alreadyPlayed");

let currentChoice = '';
let currentQuestion = 0;

const screen = {
    changeClassList: (item, prev, cur) => {
        item.classList.remove(prev);
        item.classList.add(cur);
    },
    changeCardImage: (item, img) => {
        item.src = img;
    },
    changeText: (item, message) => {
        item.innerHTML = message;
    },
    changeButtonValue: (btn, txt) => {
        btn.value = txt;
    }
}

window.onload = iniciarCronometro();

function hideAttention() {
    screen.changeClassList(attentionScreen, 'show', 'hide');
    screen.changeClassList(background, 'attention-background', 'body-background');
    blueTitles.forEach(e => screen.changeClassList(e, 'title-blue', 'text-white'));
    sidebarItens.forEach(item => item.classList.add("txt-light-blue"));
    textPrimary.forEach(item => screen.changeClassList(item, "text-primary", "text-white"));
    screen.changeClassList(sidebar, "bg-white", "bg-black");
    screen.changeClassList(startScreen, 'hide', 'show');
}

function showTutorial() {
    const startTxt = document.querySelector("#startTxt").value;

    if (startTxt.toLowerCase().includes("start")) {
        screen.changeClassList(startScreen, 'show', 'hide');
        screen.changeClassList(tutorialScreen, 'hide', 'show');
    }
}

function startGame() {
    screen.changeClassList(tutorialScreen, 'show', 'hide');
    continueStory(choices.get('start'));
    screen.changeClassList(gameScreen, 'hide', 'show');
}

function checkAnswer() {
    const answer = document.querySelector("#answerTxt");

    if (answer.value.toLowerCase().includes(questions[currentQuestion].questionAnswer)) {
        showMessage(true)
        screen.changeClassList(questionScreen, 'show', 'hide');
        screen.changeButtonValue(answer, '[Hack@Har ~]$ ')

        showStory(choices.get(currentChoice))
        currentQuestion++;
    }
    else {
        showMessage(false)
    }
}

function chooseOption(opt) {
    const played = alreadyPlayed.innerText == 'false' ? false : true;
    
    if (!played) {
        if (choices.get(opt).continueTitle === 'exit') {
            window.location.href = `/games/yamato-s-future/exit/check/`;
        } else if (choices.get(opt).continueTitle === 'restart') {
            window.location.href = `/games/yamato-s-future/restart/check/`;
        }
    } else {
        if (choices.get(opt).continueTitle === 'exit') {
            window.history.back();
        } else if (choices.get(opt).continueTitle === 'restart') {
            location.reload();
        }
    }
    continueStory(choices.get(opt));
    screen.changeClassList(gameChoiceScreen, 'show', 'hide');
    screen.changeClassList(resumeScreen, 'hide', 'show')
    currentChoice = opt;
}

function continueStory(story) {
    const continueTitle = document.querySelector("#continueTitle");
    const continueText = document.querySelector("#continueText");
    const continueImage = document.querySelector("#continueImage");
    const questionMessage = document.querySelector("#questionMessage");
    const questionImage = document.querySelector("#questionImage");

    screen.changeText(questionMessage, questions[currentQuestion].questionMessage);
    screen.changeCardImage(questionImage, questions[currentQuestion].questionImage);
    screen.changeCardImage(continueImage, story.continueImage);
    screen.changeText(continueTitle, story.continueTitle);
    screen.changeText(continueText, story.continueText)
}

function showStory(story) {
    const cardTitle = document.querySelector("#card-title");
    const cardText = document.querySelector("#card-text");
    const cardImage = document.querySelector('#card-image');
    const btn1 = document.querySelector("#btn1");
    const btn2 = document.querySelector("#btn2");

    screen.changeClassList(gameChoiceScreen, 'hide', 'show')
    screen.changeCardImage(cardImage, story.cardImg);
    screen.changeText(cardTitle, story.cardTitle);
    screen.changeButtonValue(btn1, story.opt1);
    screen.changeButtonValue(btn2, story.opt2);
    screen.changeText(cardText, story.cardTxt);
}

function showMessage(acertou) {
    const result = document.querySelector("#result");
    const message = acertou ? 'Acertou! xD' : 'Errou! :P';

    screen.changeClassList(result, 'hide', 'show');
    screen.changeText(result, message);

    setTimeout(screen.changeClassList, 2000, result, 'show', 'hide');
}

function hideResume() {
    screen.changeClassList(resumeScreen, 'show', 'hide');
    screen.changeClassList(questionScreen, 'hide', 'show');
}

function playSong() {
    screen.changeClassList(audioIcon, "fa-volume-off", "fa-volume-up");
    audio.play();
}

function pauseSong() {
    screen.changeClassList(audioIcon, "fa-volume-up", "fa-volume-off");
    audio.pause();
}

function changeSoundIcon() {
    if (audioIcon.classList.contains("fa-volume-off")) {
        playSong();
    } else {
        pauseSong();
    }
}

function up() {
    window.scrollTo(0, 0);
};