
let quantidade_selecionados = 0
let primeiroElement
let i = 0
var elapsedTime = 0
var pauseTime = 0
var continua = true
var startTime
var timenew = 0
var interval

function kaka() {
    clearInterval(this.interval)
    this.pauseTime = Date.now()

    if (confirm("Seu progresso será perdido. Deseja mesmo sair?")) {
        window.location.href = "/";
    }
    else {
        this.pauseTime = Date.now()
        this.continua = true
        this.timenew = this.elapsedTime
        countTime()
    }

}

function countTime(e) {
    this.startTime = Date.now();

    this.interval = setInterval(function () {
        this.elapsedTime = Date.now() - startTime + this.timenew
        document.getElementById("time").innerHTML = (this.elapsedTime / 1000).toFixed(1) + " seconds";

        if (this.elapsedTime >= 59999) {
            clearInterval(this.interval)
            alert("Timeout, você excedeu o tempo limite: de 1 minuto")
        }


    }, 100);

}

// SOMENTE SERA EXECUTADO POR AÇÕES EXTERNAS

shuffledIng.forEach(word => {

    var wordItem = document.querySelector('#word').cloneNode(true)

    wordItem.classList.remove('d-none')
    wordItem.classList.add('d-flex')

    var para = document.createElement("h4")
    para.classList.add('text-center')
    para.classList.add('mt-auto')
    para.textContent = word

    wordItem.appendChild(para)

    document.querySelector('#words').append(wordItem)

})

if (words.length == 0) {
    alert("voce ganhou")
}

function main(element) {

    if (element != primeiroElement) {

        if (quantidade_selecionados < 1) {
            element.classList.add("ativo")
            primeiroElement = element
            quantidade_selecionados++
        }

        else {

            let word1 = String(element.textContent)
            let word2 = String(primeiroElement.textContent)

            const var1 = words.findIndex(word => { return (word.ingles == word1.trim() || word.portuguese == word1.trim()) && (word.ingles == word2.trim() || word.portuguese == word2.trim()) })

            if (var1 > -1) {

                primeiroElement.classList.add("acertou")
                element.classList.add("acertou")
                element.classList.add("w3-animate-fading")
                primeiroElement.classList.add("w3-animate-fading")
                let text = String(element.textContent)
                text = text.trim()

                var index = words.findIndex(word => word.ingles == text || word.portuguese == text)

                setTimeout(() => {
                    words.splice(index, 1)
                    removerElemento(element)
                    removerElemento(primeiroElement)

                    var i = 0

                    while (restante.length > 0 && i < 2) {
                        var wordItem = document.querySelector('#word').cloneNode(true)

                        wordItem.classList.remove('d-none')
                        wordItem.classList.add('d-flex')

                        var para = document.createElement("h4")
                        para.classList.add('text-center')
                        para.classList.add('mt-auto')
                        para.textContent = restante[0]
                        restante.splice(0, 1)

                        wordItem.appendChild(para)

                        document.querySelector('#words').append(wordItem)

                        i++
                    }

                    primeiroElement = null

                    if (words.length == 0) {
                        clearInterval(this.interval)
                        document.getElementById("mytime").textContent = (this.elapsedTime / 1000).toFixed(1) + " segundos!";

                        if (elapsedTime > 20000) {
                            document.getElementById("msg").classList.add("text-info")
                            document.getElementById("msg").textContent = "Ainda dá para melhorar"
                        }

                        else if (elapsedTime > 40000) {
                            document.getElementById("msg").style.color = "#0062cc"
                            document.getElementById("msg").textContent = "Continue tentando fazer tempos melhores!"
                        }


                        $('#exampleModal').modal('show')
                    }
                }, 400);

            }

            else {
                element.classList.add("erro")
                element.classList.add('animation-invert')

                primeiroElement.classList.remove("ativo")
                primeiroElement.classList.add("erro")
                primeiroElement.classList.add('animation-invert')

                setTimeout(() => {
                    primeiroElement.classList.remove("erro")
                    element.classList.remove("erro")
                    primeiroElement = null
                }, 200);


            }

            quantidade_selecionados = 0
        }

    }

    else {
        primeiroElement.classList.remove("ativo")
        primeiroElement = null
        quantidade_selecionados = 0
    }

}

function removerElemento(element) {
    element.remove()
}



