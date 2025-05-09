// Получаем элементы
var modal = document.getElementById("myModal");
var addStory = document.getElementById("addStory");
var span = document.getElementById("closeModal");
// Получаем форму внутри модального окна
var form = modal.querySelector("form"); // Замените на правильный селектор для вашей формы

// Открыть модальное окно
addStory.onclick = function () {
    modal.style.display = "block";
    // history.pushState(null, null, 'addStory');
}

// Закрыть окно по клику на "крестик"
span.onclick = function () {
    modal.style.display = "none";
    // history.back();

    // Сбрасываем форму
    if (form) {
        form.reset();
    }
}

// Закрыть окно по клику вне окна
window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
        // history.back();

        // Сбрасываем форму
        if (form) {
            form.reset();
        }
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const textarea = document.getElementById('storyText');
    if (textarea) {
        // Установим начальную высоту при загрузке
        textarea.style.height = 'auto';
        textarea.style.height = (textarea.scrollHeight) + 'px';

        // Обработчик на ввод текста
        textarea.addEventListener('input', function () {
            // Сбрасываем высоту, чтобы правильно рассчитать новую
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });
    }
});

// Глобальные переменные
let selectedUsers = [];
let users = [];

document.addEventListener('DOMContentLoaded', function () {
    // Элементы DOM
    const userInput = document.getElementById('userTagInput');
    const dropdownMenu = document.getElementById('userDropdown');
    const selectedTags = document.getElementById('selectedTags');
    const selectedUsersContainer = document.getElementById('selectedUsersContainer');

    // Загрузка пользователей с сервера Spring Boot
    function loadUsers() {
        // Показываем индикатор загрузки в инпуте
        userInput.placeholder = "Загрузка пользователей...";
        userInput.disabled = true;

        fetch('/profile/users')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка загрузки пользователей: ' + response.status);
                }
                return response.json();
            })
            .then(data => {
                users = data;
                // Если API возвращает массив в другой структуре, адаптируем:
                // например, если данные в формате { content: [...] }
                if (data.content && Array.isArray(data.content)) {
                    users = data.content;
                }

                userInput.placeholder = "Начните вводить имя пользователя...";
                userInput.disabled = false;
                console.log('Загружено пользователей:', users.length);
            })
            .catch(error => {
                console.error('Ошибка при загрузке пользователей:', error);
                userInput.placeholder = "Ошибка загрузки. Нажмите для повтора...";
                userInput.disabled = false;

                // Добавляем возможность повторить при клике на поле
                userInput.addEventListener('click', function retryHandler() {
                    if (users.length === 0) {
                        loadUsers();
                        userInput.removeEventListener('click', retryHandler);
                    }
                });
            });
    }

    // Загружаем пользователей при загрузке страницы
    loadUsers();

    // Поиск пользователей на сервере, если требуется
    function searchUsers(query) {
        // Для небольших списков можно фильтровать локально
        if (users.length < 100) {
            return Promise.resolve(
                users.filter(u => {
                    // Предполагаем, что у пользователя есть поля id, name (или username, firstName+lastName)
                    const userName = u.name || u.username || `${u.firstName} ${u.lastName}`;
                    return userName.toLowerCase().includes(query.toLowerCase());
                })
            );
        }

        // Для больших списков лучше выполнить поиск на сервере
        return fetch(`/users/search?query=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                // Обработка ответа в зависимости от API
                return Array.isArray(data) ? data : (data.content || []);
            })
            .catch(error => {
                console.error('Ошибка поиска пользователей:', error);
                return [];
            });
    }

    function renderDropdown(filtered) {
        dropdownMenu.innerHTML = '';
        if (filtered.length === 0 || userInput.value.trim() === '') {
            dropdownMenu.style.display = 'none';
            return;
        }

        filtered.forEach(user => {
            if (selectedUsers.includes(user.id)) return; // Не показывать выбранных

            // Формируем имя пользователя в зависимости от структуры данных API
            const userName = user.name || user.username || `${user.firstName} ${user.lastName}`;
            const userAvatar = user.icon;

            const div = document.createElement('div');
            div.className = 'dropdown-item user-item';
            div.innerHTML = `
        <img class="user-avatar" src="/img/${userAvatar}" alt="">
        <span>${userName}</span>
      `;
            div.addEventListener('click', () => {
                selectUser(user);
            });
            dropdownMenu.appendChild(div);
        });

        dropdownMenu.style.display = 'block';
    }

    function selectUser(user) {
        if (!selectedUsers.includes(user.id)) {
            selectedUsers.push(user.id);
            renderSelectedTags();
            updateHiddenInputs();
        }
        userInput.value = '';
        dropdownMenu.style.display = 'none';
        userInput.focus();
    }

    function removeUser(userId) {
        selectedUsers = selectedUsers.filter(id => id !== userId);
        renderSelectedTags();
        updateHiddenInputs();
    }

    function renderSelectedTags() {
        selectedTags.innerHTML = '';
        selectedUsers.forEach(userId => {
            const user = users.find(u => u.id === userId);
            if (!user) return;
            // Формируем имя пользователя (адаптируйте под структуру вашего API)
            const userName = user.name || user.username || `${user.firstName} ${user.lastName}`;
            const userAvatar = user.icon;
            const tag = document.createElement('span');
            tag.className = 'tag';
            tag.innerHTML = `
        <img class="user-avatar" src="/img/${userAvatar}" alt="">
        ${userName}
        <span class="tag-remove" title="Удалить">&times;</span>
      `;
            tag.querySelector('.tag-remove').addEventListener('click', () => removeUser(user.id));
            selectedTags.appendChild(tag);
        });
    }

    function updateHiddenInputs() {
// Для отправки id выбранных пользователей на сервер
        selectedUsersContainer.innerHTML = '';
        selectedUsers.forEach(id => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'users[]';
            input.value = id;
            selectedUsersContainer.appendChild(input);
        });
    }

    // Обработчик ввода
    userInput.addEventListener('input', function () {
        const searchValue = this.value.trim().toLowerCase();
        if (searchValue === '' || users.length === 0) {
            dropdownMenu.style.display = 'none';
            return;
        }
    // Локальный поиск (можно заменить на api-поиск)
        const filtered = users.filter(u => {
            const userName = u.name || u.username || `${u.firstName} ${u.lastName}`;
            return userName.toLowerCase().includes(searchValue) && !selectedUsers.includes(u.id);
        });
        renderDropdown(filtered);
    });

    // Скрыть выпадающее меню при потере фокуса
    userInput.addEventListener('blur', function () {
        setTimeout(() => {
            dropdownMenu.style.display = 'none';
        }, 100);
    });

    // Показать дропдаун при фокусе, если есть ввод
    userInput.addEventListener('focus', function () {
        if (this.value.trim() !== '' && users.length > 0) {
            const searchValue = this.value.trim().toLowerCase();
            const filtered = users.filter(u => {
                const userName = u.name || u.username || `${u.firstName} ${u.lastName}`;
                return userName.toLowerCase().includes(searchValue) && !selectedUsers.includes(u.id);
            });
            renderDropdown(filtered);
        }
    });

    // Enter для выбора первого пользователя
    userInput.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const searchValue = this.value.trim().toLowerCase();
            const filtered = users.filter(u => {
                const userName = u.name || u.username || `${u.firstName} ${u.lastName}`;
                return userName.toLowerCase().includes(searchValue) && !selectedUsers.includes(u.id);
            });
            if (filtered.length > 0) {
                selectUser(filtered);
            }
        }
    });
});

async function getFileBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = function (e) {
            // e.target.result будет в формате "data:image/png;base64,...."
            resolve(e.target.result);
        };
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
}

async function addStoryFun(event) {
    event.preventDefault();

    const storyTitle = document.getElementById('storyTitle').value;
    const storyCity = document.getElementById('storyCity').value;
    const storyText = document.getElementById('storyText').value;

    // Получаем файл из input
    const fileInput = document.getElementById('storyImage');
    const file = fileInput.files[0];
    let storyImage = null;
    if (file) {
        storyImage = await getFileBase64(file); // теперь это строка base64 с префиксом
    }

    // Функция для получения массива байтов из файла
    async function getFileBytes(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const arrayBuffer = e.target.result;
                const byteArray = Array.from(new Uint8Array(arrayBuffer)); // Преобразуем в обычный массив JS
                resolve(byteArray);
            };
            reader.onerror = reject;
            reader.readAsArrayBuffer(file);
        });
    }

    // Если файл выбран – получаем байты
    if (file) {
        storyImage = await getFileBytes(file);
    }

    // Массив для хранения отмеченных пользователей
    const taggedUsers = [];

    // Предполагается, что selectedUsers и users определены где-то выше
    selectedUsers.forEach(userId => {
        const user = users.find(u => u.id === userId);
        if (user) {
            const userName = user.name || user.username || `${user.firstName} ${user.lastName}`;
            taggedUsers.push({name: userName});
        }
    });

    const storyData = {
        head: storyTitle,
        img: storyImage, // байтовый массив
        text: storyText,
        city: storyCity,
        usersTag: taggedUsers
    };

    console.log('Отправляемые данные:\n', JSON.stringify(storyData, null, 2));

    let token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = "/login";
        return;
    }

    // Отправляем данные на сервер
    fetch('/addStory/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + token,
        },
        body: JSON.stringify(storyData),
    })
        .then(async response => {
            if (response.status === 403) {
                const newToken = await refreshAccessToken();
                if (!newToken) return;
                addStoryFun(event);
            }
            if (!response.ok) {
                throw new Error('Ошибка при отправке истории: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log('История успешно создана:', data);
            document.getElementById('addStoryForm').reset();
            document.getElementById('selectedTags').innerHTML = '';
            document.getElementById('selectedUsersContainer').innerHTML = '';
            alert('История успешно создана!');
            window.location.href = '/profile';
        });

    // Возвращаем false, чтобы форма не отправлялась
    return false;
}

