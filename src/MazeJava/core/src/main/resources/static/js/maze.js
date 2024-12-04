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
            printFormattedJson(data)
        })
        .catch(error => console.error('Error:', error));
}

function generateMaze(width, height) {
    const url = `api/maze/generate?width=${width}&height=${height}`; // Параметры передаются через URL
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
            printFormattedJson(data);
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

function printFormattedJson(data) {
    data.matrixA = data.matrixA.map(row => row.join(' '));
    data.matrixB = data.matrixB.map(row => row.join(' '));
    const formattedJson = JSON.stringify(data, null, 4); // 4 - это количество пробелов для отступов
    document.getElementById('dataOutput').textContent = formattedJson;
}

document.getElementById("uploadButton").onchange = function () {
    loadMaze(this.files[0]);
}
    document.getElementById("generateButton").onclick = function () {
        generateMaze(10, 10);
    }