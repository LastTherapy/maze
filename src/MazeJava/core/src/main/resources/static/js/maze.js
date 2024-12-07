function loadMaze(file) {
    let formData = new FormData();
    formData.append("file", file);
    fetch('api/maze/upload', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(
        data => {
            console.log(data)
            renderMaze(data)
            printFormattedMaze(data)
        })
        .catch(error => console.error('Error:', error));
}

function generateMaze() {
    let rows = parseInt(document.getElementById("rows").value, 10);
    let columns = parseInt(document.getElementById("columns").value, 10);
    // Проверяем корректность ввода
    if (isNaN(rows) ||  rows <= 0) {
        rows = 10;
    }
    if (isNaN(columns) || columns <= 0) {
        columns = 10;
    }
    const url = `api/maze/generate?width=${columns}&height=${rows}`; // Параметры передаются через URL
    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            renderMaze(data);
            printFormattedMaze(data);
        })
        .catch(error => console.error('Error:', error));
}

function renderMaze(maze) {
    const wallSize = 2;
    let canvas = document.getElementById("canvas");
    let ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.beginPath();
    let cellWidth = canvas.width / maze.width;
    let cellHeight = canvas.height / maze.height;
    for (let i = 0; i < maze.height; i++) {
        for (let j = 0; j < maze.width; j++) {
            // Определяем координаты верхнего левого угла клетки
            let x = j * cellWidth;
            let y = i * cellHeight;

            // Рисуем правую стену, если matrixA[i][j] == 1
            if (maze.matrixA[i][j] == 1 && j < maze.width - 1) {
                ctx.fillStyle = "black";
                ctx.fillRect(x + cellWidth - wallSize, y, wallSize, cellHeight);  // Правая стена
            }

            // Рисуем нижнюю стену, если matrixB[i][j] == 1
            if (maze.matrixB[i][j] == 1 && i < maze.height - 1) {
                ctx.fillStyle = "black";
                ctx.fillRect(x, y + cellHeight - wallSize, cellWidth, wallSize);  // Нижняя стена
            }

            // Если нет стен, рисуем пустую ячейку (фон)
            if (maze.matrixA[i][j] !== 1 && maze.matrixB[i][j] !== 1) {
                ctx.fillStyle = "whitesmoke";
                ctx.fillRect(x, y, cellWidth, cellHeight);  // Пустая ячейка
            }
        }
    }
}



// function printFormattedJson(data) {
//     data.matrixA = data.matrixA.map(row => row.join(' '));
//     data.matrixB = data.matrixB.map(row => row.join(' '));
//     const formattedJson = JSON.stringify(data, null, 4); // 4 - это количество пробелов для отступов
//     document.getElementById('dataOutput').textContent = formattedJson;
// }

function printFormattedMaze(data) {
    // Инициализация строки результата с шириной и высотой
    let result = `${data.width} ${data.height}\n`;
    // Добавляем matrixA в строку
    result += data.matrixA.map(row => row.join(' ')).join('\n') + '\n\n';
    // Добавляем matrixB в строку
    result += data.matrixB.map(row => row.join(' ')).join('\n');
    // Вывод результата в элемент с id "dataOutput"
    document.getElementById('dataOutput').textContent = result;
}


document.getElementById("uploadButton").onchange = function () {
    loadMaze(this.files[0]);
}

document.getElementById("generateButton").onclick = function () {
    generateMaze();
}

document.getElementById("downloadButton").onclick = function () {
    fetch('api/download-maze')
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Ошибка скачивания файла');
            }
            return response.blob();
        })
        .then(function (blob) {
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = "maze.txt"; // Имя файла для скачивания
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        })
        .catch(function (error) {
            console.error('Ошибка при скачивании файла:', error);
        });
};

// Открытие модального окна
document.getElementById("settingsGenerate").onclick = function () {
    document.getElementById("settingsModal").style.display = "flex";
};

// Закрытие модального окна
document.getElementById("closeModal").onclick = function () {
    document.getElementById("settingsModal").style.display = "none";
};

// Обработка формы и генерация лабиринта
document.getElementById("settingsForm").onsubmit = function (event) {
    event.preventDefault(); // Предотвращаем перезагрузку страницы
    generateMaze();
    // Закрываем модальное окно
    document.getElementById("settingsModal").style.display = "none";
};


window.onload = function () {
    generateMaze(10, 10);
}