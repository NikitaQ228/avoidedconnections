async function loadProfileInfo() {
    let token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = "/login";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');
    const url1 = id ? "/profile/user/" + id : "/profile/user";

    if (!id) {
        const svg = document.querySelector('svg.settings-icon');
        svg.setAttribute('viewBox', '0 0 24 24');
        svg.setAttribute('onclick', 'openPasswordModal()');

        // Создаем элемент <path>
        const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        path.setAttribute('d', 'M12 15.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7zm7.43-2.5a7.93 7.93 0 0 0-.16-1.17l2.11-1.65-2-3.46-2.49 1a7.88 7.88 0 0 0-2.03-1.17l-.38-2.65h-4l-.38 2.65a7.88 7.88 0 0 0-2.03 1.17l-2.49-1-2 3.46 2.11 1.65a7.93 7.93 0 0 0-.16 1.17c0 .4.05.8.16 1.17l-2.11 1.65 2 3.46 2.49-1a7.88 7.88 0 0 0 2.03 1.17l.38 2.65h4l.38-2.65a7.88 7.88 0 0 0 2.03-1.17l2.49 1 2-3.46-2.11-1.65c.11-.37.16-.77.16-1.17z');

        // Вставляем path внутрь svg
        svg.appendChild(path);
    }

    let response = await fetch(url1, {
        headers: { "Authorization": "Bearer " + token }
    });

    if (response.status === 403) {
        // Токен истёк или недействителен - пробуем обновить
        const newToken = await refreshAccessToken();
        if (!newToken) return; // если не удалось обновить - выход
        loadProfileInfo();
    }
    if (response.ok) {
        let user = await response.json();
        updateProfile(user)

        const url2 = id ? "/profile/story/" + id : "/profile/story";
        response = await fetch(url2, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (response.status === 403) {
            // Токен истёк или недействителен - пробуем обновить
            const newToken = await refreshAccessToken();
            if (!newToken) return; // если не удалось обновить - выход
            loadProfileInfo();
        }

        let result = await response.json();
        const container1 = document.getElementById('posts-container1');
        if (response.ok) {
            if (result.length === 0) {
                container1.innerHTML = '<p style="color: white; font-size: 48px; font-weight: bold; text-align: center; margin: 0; padding: 20px;">У вас нет историй</p>';
            } else {
                container1.innerHTML = '';
                result.forEach(story => {
                    createStory(story, container1)
                });
            }
        } else {
            console.error('Ошибка:', error);
        }

        const url3 = id ? "/profile/storyTag/" + id : "/profile/storyTag";

        response = await fetch(url3, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (response.status === 403) {
            // Токен истёк или недействителен - пробуем обновить
            const newToken = await refreshAccessToken();
            if (!newToken) return; // если не удалось обновить - выход
            loadProfileInfo();
        }

        result = await response.json();
        const container2 = document.getElementById('posts-container2');

        if (response.ok) {
            if (result.length === 0) {
                container2.innerHTML = '<p style="color: white; font-size: 48px; font-weight: bold; text-align: center; margin: 0; padding: 20px;">Вас не отмечали в историях</p>';
            } else {
                container2.innerHTML = '';
                result.forEach(story => {
                    createStory(story, container2)
                });
            }
        } else {
            console.error('Ошибка:', error);
        }
    } else {
        console.error('Ошибка:', error);
    }
}

function updateProfile(data) {
    // Обновляем аватар
    const avatarImg = document.getElementById('avatar');
    if (avatarImg && data.icon) {
        avatarImg.src = "/img/" + data.icon;
    }

    // Обновляем имя пользователя
    const usernameDiv = document.getElementById('username');
    if (usernameDiv && data.name) {
        usernameDiv.textContent = data.name;
    }
}

function createStory(postData, container) {
    const { id, head, img, text, date, city } = postData;

    // Форматируем дату для атрибута datetime и для отображения
    const dateObj = new Date(date);
    const dateTimeAttr = dateObj.toISOString().split('T')[0]; // yyyy-mm-dd
    // Форматируем дату в удобочитаемый вид, например "7 марта 2024"
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    const dateText = dateObj.toLocaleDateString('ru-RU', options);

    // Создаем элементы
    const a = document.createElement('a');
    a.href = `/story?id=${encodeURIComponent(id)}`;
    a.className = 'post-link';

    const article = document.createElement('article');
    article.className = 'post-card';

    const imgElem = document.createElement('img');
    imgElem.src = '/img/' + img;
    imgElem.className = 'post-image';
    imgElem.alt = head;

    const contentDiv = document.createElement('div');
    contentDiv.className = 'post-content';

    const h2 = document.createElement('h2');
    h2.className = 'post-title';
    h2.textContent = head;

    const metaDiv = document.createElement('div');
    metaDiv.className = 'post-meta';

    const spanCity = document.createElement('span');
    spanCity.textContent = city;

    const timeElem = document.createElement('time');
    timeElem.dateTime = dateTimeAttr;
    timeElem.textContent = dateText;

    const pText = document.createElement('p');
    pText.className = 'post-text';
    pText.textContent = text.substring(0, 65) + ' ...';

    // Собираем структуру
    metaDiv.appendChild(spanCity);
    metaDiv.appendChild(timeElem);

    contentDiv.appendChild(h2);
    contentDiv.appendChild(metaDiv);
    contentDiv.appendChild(pText);

    article.appendChild(imgElem);
    article.appendChild(contentDiv);

    a.appendChild(article);

    container.appendChild(a);
}

// Открыть модальное окно смены пароля
function openPasswordModal() {
    document.getElementById('passwordModal').classList.add('active');
}

// Закрыть модальное окно смены пароля
function closePasswordModal() {
    document.getElementById('passwordModal').classList.remove('active');
    document.getElementById('changePasswordForm').reset();
}

// Обработчик смены пароля (заглушка)
async function handleChangePassword(event) {
    event.preventDefault();

    const current = document.getElementById('currentPassword').value;
    const newPass = document.getElementById('newPassword').value;
    const confirm = document.getElementById('confirmPassword').value;

    if (newPass !== confirm) {
        alert('Новый пароль и подтверждение не совпадают!');
        return;
    }

    const passwordData = {
        oldPassword: current,
        newPassword: newPass
    };

    let token = localStorage.getItem('accessToken');
    let response = await fetch("/profile/changePassword", {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(passwordData)
    });

    if (response.status === 403) {
        // Токен истёк или недействителен - пробуем обновить
        const newToken = await refreshAccessToken();
        if (!newToken) return; // если не удалось обновить - выход
        response = await fetch("/profile/changePassword", {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + newToken
            },
            body: JSON.stringify(passwordData)
        });
    }

    if (response.ok) {
        alert('Пароль успешно изменён!');
        closePasswordModal();
    } else if (response.status === 400) {
        alert('Неверный пароль!');
    } else {
        console.error('Ошибка при изменении пароля');
        closePasswordModal();
    }
    return;
}

// Модальное окно выбора аватара
const avatarModal = document.getElementById('avatarModal');
const avatarImg = document.getElementById('avatar');
const avatarGrid = document.getElementById('avatarGrid');
const avatarOptions = avatarGrid.querySelectorAll('img');

// Открыть модалку выбора аватара при клике на аватар
document.querySelector('.avatar-wrapper').addEventListener('click', () => {
    const params = new URLSearchParams(window.location.search);
    if (!params.get('id')) {
        avatarModal.classList.add('active');
    }
});

// Закрыть модалку выбора аватара
function closeAvatarModal() {
    avatarModal.classList.remove('active');
}

// Выбор аватара из списка
avatarOptions.forEach(img => {
    img.addEventListener('click', async () => {
        avatarImg.src = img.src;

        let token = localStorage.getItem('accessToken');
        let response = await fetch("/profile/changeIcon", {
            method: 'PUT',
            headers: {
                "Content-Type": "text/plain",
                "Authorization": "Bearer " + token
            },
            body: img.getAttribute('data-avatar')
        });
        if (response.status === 403) {
            // Токен истёк или недействителен - пробуем обновить
            const newToken = await refreshAccessToken();
            if (!newToken) return; // если не удалось обновить - выход
            response = await fetch("/profile/changeIcon", {
                method: 'PUT',
                headers: {
                    "Content-Type": "text/plain",
                    "Authorization": "Bearer " + token
                },
                body: img.getAttribute('data-avatar')
            });
        }
        if (!response.ok) {
            console.error('Ошибка при изменении ватара:');
        }
        closeAvatarModal();
    });
});

// Закрывать модальные окна при клике вне контента
window.addEventListener('click', (e) => {
    if (e.target === avatarModal) {
        closeAvatarModal();
    }
    if (e.target === document.getElementById('passwordModal')) {
        closePasswordModal();
    }
});

window.onload = loadProfileInfo;