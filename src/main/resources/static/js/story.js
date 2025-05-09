function populateStory(data) {
    const storyImage = document.querySelector('.story-image');
    const title = document.querySelector('.title');
    const authorInfo = document.querySelector('.author-info');
    const locationDate = document.querySelector('.location-date');
    const tags = document.querySelector('.tags');
    const storyText = document.querySelector('.story-text');

    storyImage.src = '/story/image/' + data.id;
    title.textContent = data.head;

    authorInfo.innerHTML = `Автор: <a href="/profile?id=${data.author.id}" style="color:#e8e561; text-decoration:none;">${data.author.name}</a>`;

    const date = new Date(data.date);
    const formattedDate = `${date.toLocaleDateString('ru-RU')}`;

    locationDate.textContent = `${data.city} — ${formattedDate}`;

    tags.innerHTML = data.usersTag.map(user => `<span><a href="/profile?id=${user.id}" target="_blank" rel="noopener noreferrer">${user.name}</a></span>`).join('\n');

    storyText.textContent = data.text;
}

function addComment(commentData) {
    // Находим контейнер для комментариев
    const commentsContainer = document.querySelector('.comments');
    if (!commentsContainer) {
        console.error('Контейнер с классом "comments" не найден');
        return;
    }

    // Создаем основной блок комментария
    const comment = document.createElement('div');
    comment.className = 'comment';

    // Создаем ссылку с иконкой пользователя (href — на полный размер, src — на превью)
    const userLink = document.createElement('a');
    userLink.href = `img/${commentData.writer.icon}`; // ссылка на оригинальное изображение
    userLink.target = '_blank';
    userLink.rel = 'noopener noreferrer';

    const userIcon = document.createElement('img');
    userIcon.src = `img/${commentData.writer.icon}`; // иконка пользователя
    userIcon.className = 'user-icon';

    userLink.appendChild(userIcon);
    comment.appendChild(userLink);

    // Создаем блок с содержимым комментария
    const commentContent = document.createElement('div');
    commentContent.className = 'comment-content';

    // Заголовок комментария (имя и дата)
    const commentHeader = document.createElement('div');
    commentHeader.className = 'comment-header';

    const profileLink = document.createElement('a');
    profileLink.href = `/profile?id=${commentData.writer.id}`;
    profileLink.target = '_blank';
    profileLink.rel = 'noopener noreferrer';
    profileLink.textContent = commentData.writer.name;

    const timeElem = document.createElement('time');
    timeElem.className = 'comment-date';
    timeElem.dateTime = commentData.date;

    // Форматируем дату в виде "16 апреля 2025"
    const dateObj = new Date(commentData.date);
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    timeElem.textContent = dateObj.toLocaleDateString('ru-RU', options);

    commentHeader.appendChild(profileLink);
    commentHeader.appendChild(timeElem);

    // Текст комментария
    const commentText = document.createElement('div');
    commentText.className = 'comment-text';
    commentText.textContent = commentData.text;

    // Собираем всё вместе
    commentContent.appendChild(commentHeader);
    commentContent.appendChild(commentText);
    comment.appendChild(commentContent);

    // Добавляем комментарий в контейнер
    commentsContainer.appendChild(comment);
}

function addDeleteIcon() {
    // Находим контейнер с заголовком
    const container = document.querySelector('.title-container');
    if (!container) return;

    // Проверяем, чтобы иконка не добавилась повторно
    if (container.querySelector('.delete-icon')) return;

    // Создаем SVG элемент с нужными атрибутами
    const svgNS = "http://www.w3.org/2000/svg";
    const svg = document.createElementNS(svgNS, 'svg');
    svg.setAttribute('class', 'delete-icon');
    svg.setAttribute('viewBox', '0 0 24 24');
    svg.setAttribute('role', 'button');
    svg.setAttribute('tabindex', '0');
    svg.setAttribute('title', 'Удалить историю');
    svg.setAttribute('aria-label', 'Удалить историю');
    svg.style.cursor = 'pointer';
    svg.addEventListener('click', handleDeleteStory);

    // Создаем path
    const path = document.createElementNS(svgNS, 'path');
    path.setAttribute('d', 'M3 6h18v2H3V6zm2 3h14l-1.5 12.5a1 1 0 0 1-1 .5H8a1 1 0 0 1-1-.5L5 9zm5-5h4v2h-4V4z');

    svg.appendChild(path);

    // Добавляем SVG в контейнер
    container.appendChild(svg);
}

async function loadStoryInfo() {
    let token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = "/login";
        return;
    }

    let response = await fetch("/profile/user", {
        headers: { "Authorization": "Bearer " + token }
    });

    if (response.status === 403) {
        // Токен истёк или недействителен - пробуем обновить
        const newToken = await refreshAccessToken();
        if (!newToken) return; // если не удалось обновить - выход
        loadStoryInfo();
        return;
    }

    let user = await response.json();

    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    response = await fetch("/story/" + id, {
        headers: { "Authorization": "Bearer " + token }
    });

    if (response.status === 403) {
        // Токен истёк или недействителен - пробуем обновить
        const newToken = await refreshAccessToken();
        if (!newToken) return; // если не удалось обновить - выход
        loadStoryInfo();
    }
    if (response.ok) {
        let story = await response.json();
        if (user.id == story.author.id) {
            addDeleteIcon();
        }
        populateStory(story);
        response = await fetch("/story/" + id + "/comment", {
            headers: { "Authorization": "Bearer " + token }
        });

        let comments = await response.json();

        if (response.status === 403) {
            // Токен истёк или недействителен - пробуем обновить
            const newToken = await refreshAccessToken();
            if (!newToken) return; // если не удалось обновить - выход
            loadStoryInfo();
        }
        if (response.ok) {
            const commentsContainer = document.querySelector('.comments');
            commentsContainer.innerHTML = ''
            comments.forEach(commentData => {
                addComment(commentData)
            });
        }
    }
}

window.onload = loadStoryInfo;

document.getElementById('comment-form').addEventListener('submit', async function(event) {
    event.preventDefault(); // предотвращаем стандартную отправку формы

    const textarea = this.querySelector('#comment-text');
    const commentText = textarea.value.trim();

    if (!commentText) {
        alert('Пожалуйста, введите комментарий');
        return;
    }

    let token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = "/login";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    try {
        let response = await fetch("/story/" + id + "/addComment", {
            method: 'POST',
            headers: {
                "Content-Type": "text/plain",
                "Authorization": "Bearer " + token
            },
            body: commentText
        });

        if (response.status === 403) {
            // Токен истёк или недействителен - пробуем обновить
            const newToken = await refreshAccessToken();
            if (!newToken) return; // если не удалось обновить - выход
            response = await fetch("/story/" + id + "/addComment", {
                method: 'POST',
                headers: {
                    "Content-Type": "text/plain",
                    "Authorization": "Bearer " + newToken
                },
                body: commentText
            });
        }
        if (response.ok) {
            const result = await response.json();
            addComment(result);
            textarea.value = '';
        } else {
            throw new Error(`Ошибка сервера: ${response.status}`);
        }

    } catch (error) {
        console.error('Ошибка при отправке комментария:', error);
    }
});

async function handleDeleteStory() {
    if (!confirm('Вы уверены, что хотите удалить эту историю?')) return;

    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');
    const token = localStorage.getItem('accessToken');
    if (!id || !token) {
        alert('Ошибка авторизации или отсутствует id истории.');
        return;
    }
    try {
        const response = await fetch("story/" + id + "/delete", {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.status === 403) {
            // Токен истёк или недействителен - пробуем обновить
            const newToken = await refreshAccessToken();
            if (!newToken) return; // если не удалось обновить - выход
            response = await fetch("story/" + id + "/delete", {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
        }
        if (response.ok) {
            alert('История успешно удалена.');
            window.history.back();
        }
    } catch (error) {
        console.error(error);
    }
}