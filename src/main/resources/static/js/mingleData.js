
const words = [
    { id: 2, portuguese: 'Placa Mãe', ingles: 'Mother-Board' },
    { id: 3, portuguese: 'Disco Rígido', ingles: 'Hard Disk' },
    { id: 4, portuguese: 'Computador', ingles: 'Computer' },
    { id: 5, portuguese: 'Teclado', ingles: 'Keyboard' },
    { id: 6, portuguese: 'Para', ingles: 'For' },
    { id: 7, portuguese: 'Se', ingles: 'IF' },
    { id: 8, portuguese: 'Até', ingles: 'Until' }
]

var newArray = words.map(word => word.portuguese)
newArray = newArray.concat(words.map(word => word.ingles))

const shuffledIng = newArray.sort(() => Math.random() - 0.5)
const restante = []

shuffledIng.map((el, index) => {
    if (restante.length <= 3) {
        restante.push(el)
        shuffledIng.splice(index, 1)
    }
})