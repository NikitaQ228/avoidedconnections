// Получаем элементы
var modal = document.getElementById("myModal");
var addStory = document.getElementById("addStory");
var span = document.getElementById("closeModal");


// Открыть модальное окно
addStory.onclick = function() {
    modal.style.display = "block";
    history.pushState(null, null, 'addStory');
}

// Закрыть окно по клику на "крестик"
span.onclick = function() {
    modal.style.display = "none";
    history.back();
}

// Закрыть окно по клику вне окна
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
        history.back();
    }
}

// Глобальные переменные
let selectedUsers = [];
let users = [];

document.addEventListener('DOMContentLoaded', function() {
    // Элементы DOM
    const userInput = document.getElementById('userTagInput');
    const dropdownMenu = document.getElementById('userDropdown');
    const selectedTags = document.getElementById('selectedTags');
    const selectedUsersContainer = document.getElementById('selectedUsersContainer');

    // Массив выбранных пользователей (ID)
    // let selectedUsers = [];

    // Массив всех пользователей
    // let users = [];

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
    userInput.addEventListener('input', function() {
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
    userInput.addEventListener('blur', function() {
        setTimeout(() => {
            dropdownMenu.style.display = 'none';
        }, 100);
    });

    // Показать дропдаун при фокусе, если есть ввод
    userInput.addEventListener('focus', function() {
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
    userInput.addEventListener('keydown', function(e) {
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

function sanitizeFilename(filename, options = {}) {
    // Настройки по умолчанию
    const defaults = {
        transliterate: true,
        toLowerCase: true,
        replacement: '-'
    };

    // Объединение настроек по умолчанию с переданными параметрами
    const settings = { ...defaults, ...options };

    // Получение имени и расширения файла
    const lastDotIndex = filename.lastIndexOf('.');
    const extension = lastDotIndex !== -1 ? filename.slice(lastDotIndex) : '';
    const name = lastDotIndex !== -1 ? filename.slice(0, lastDotIndex) : filename;

    // Словарь для транслитерации
    const transliterationMap = {
        'а': 'a', 'б': 'b', 'в': 'v', 'г': 'g', 'д': 'd', 'е': 'e', 'ё': 'e',
        'ж': 'zh', 'з': 'z', 'и': 'i', 'й': 'y', 'к': 'k', 'л': 'l', 'м': 'm',
        'н': 'n', 'о': 'o', 'п': 'p', 'р': 'r', 'с': 's', 'т': 't', 'у': 'u',
        'ф': 'f', 'х': 'h', 'ц': 'ts', 'ч': 'ch', 'ш': 'sh', 'щ': 'sch', 'ъ': '',
        'ы': 'y', 'ь': '', 'э': 'e', 'ю': 'yu', 'я': 'ya',
        'А': 'A', 'Б': 'B', 'В': 'V', 'Г': 'G', 'Д': 'D', 'Е': 'E', 'Ё': 'E',
        'Ж': 'Zh', 'З': 'Z', 'И': 'I', 'Й': 'Y', 'К': 'K', 'Л': 'L', 'М': 'M',
        'Н': 'N', 'О': 'O', 'П': 'P', 'Р': 'R', 'С': 'S', 'Т': 'T', 'У': 'U',
        'Ф': 'F', 'Х': 'H', 'Ц': 'Ts', 'Ч': 'Ch', 'Ш': 'Sh', 'Щ': 'Sch', 'Ъ': '',
        'Ы': 'Y', 'Ь': '', 'Э': 'E', 'Ю': 'Yu', 'Я': 'Ya'
    };

    let processedName = name;

    // Транслитерация, если включена
    if (settings.transliterate) {
        processedName = processedName.split('').map(char =>
            transliterationMap[char] !== undefined ? transliterationMap[char] : char
        ).join('');
    }

    // Приведение к нижнему регистру, если включено
    if (settings.toLowerCase) {
        processedName = processedName.toLowerCase();
    }

    // Удаление всех символов, кроме букв, цифр, подчеркивания и дефиса
    // Замена пробелов и других недопустимых символов на указанный символ замены
    processedName = processedName
        .replace(/[^\w\-\.]/g, settings.replacement)  // Замена недопустимых символов
        .replace(/\s+/g, settings.replacement)        // Замена пробелов
        .replace(new RegExp(`${settings.replacement}+`, 'g'), settings.replacement) // Удаление повторяющихся символов замены
        .replace(new RegExp(`^${settings.replacement}|${settings.replacement}$`, 'g'), ''); // Удаление символа замены в начале и конце

    return processedName + extension;
}

document.querySelector('input[type="file"]').addEventListener('change', function() {
    if (this.files.length === 1) {
        var file_name = sanitizeFilename(this.files[0].name);

        if (this.files.name !== file_name) {
            var new_file = new File([this.files], file_name, {type: this.files.type});
            var dt = new DataTransfer();
            dt.items.add(new_file);
            this.files = dt.files;
        }
    }
});

// document.getElementById('storyImage').addEventListener('change', function(event) {
//     const file = event.target.files[0];
//     file.name = sanitizeFilename(file.name)
//     const imagePreview = document.getElementById('imagePreview');
//
//     // Предпросмотр
//     if (file) {
//         const reader = new FileReader();
//         reader.onload = function(e) {
//             imagePreview.innerHTML = `<img src="${e.target.result}" alt="Preview" style="max-width: 200px; max-height: 200px;">`;
//         };
//         reader.readAsDataURL(file);
//     } else {
//         imagePreview.innerHTML = '';
//     }
//
//     // Загрузка изображения на сервер
//     if (file) {
//         const formData = new FormData();
//         formData.append('image', file);
//
//         let token = localStorage.getItem('accessToken');
//         if (!token) {
//             window.location.href = "/login";
//             return;
//         }
//
//         fetch('/addStory/uploadImage', {
//             method: 'POST',
//             headers: {
//                 "Authorization": "Bearer " + token,
//             },
//             body: formData
//         })
//             .then(async response => {
//                 if (!response.ok) {
//                     throw new Error('Ошибка при отправке истории: ' + response.status);
//                 }
//                 if (response.status === 403) {
//                     // Токен истёк или недействителен - пробуем обновить
//                     const newToken = await refreshAccessToken();
//                     if (!newToken) return; // если не удалось обновить - выход
//                     ???addStoryFun();
//                 }
//                 return response.json();
//
//             })
//             .then(response => response.json())
//             .then(data => {
//                 // Здесь data.url - это адрес загруженного изображения на сервере
//                 console.log('Изображение загружено:', data.url);
//                 // Можно сохранить ссылку в скрытом поле формы, чтобы отправить её вместе с историей
//                 // Например:
//                 document.getElementById('hiddenImageUrl').value = data.url;
//             })
//             // .catch(error => {
//             //     console.error('Ошибка загрузки:', error);
//             // });
//     }
// });

async function uploadFile(file, uploadUrl = '/upload', containerId = 'storyImage') {
    if (!file) {
        alert('Файл не выбран!');
        return;
    }

    let token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = "/login";
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch(uploadUrl, {
            method: 'POST',
            headers: {
                "Authorization": "Bearer " + token,
                // НЕ ДОБАВЛЯТЬ Content-Type вручную при использовании FormData!
            },
            body: formData,
        });

        // Обработка неавторизованности
        if (response.status === 403) {
            const newToken = await refreshAccessToken();
            if (!newToken) return;
            // Повторный вызов с обновлённым токеном и тем же файлом
            return await uploadFile(file, uploadUrl, containerId);
        }

        if (!response.ok) {
            throw new Error('Ошибка при отправке истории: ' + response.status);
        }

        const data = await response.json();

        if (data.fileUrl) {
            document.getElementById('hiddenImageUrl').value = data.fileUrl;
            const imgElement = document.createElement('img');
            imgElement.src = data.fileUrl;
            imgElement.alt = data.fileName || 'Загруженное изображение';
            imgElement.style.maxWidth = '300px';
            document.getElementById(containerId).appendChild(imgElement);
        } else {
            alert('Сервер не прислал ссылку на файл.');
        }
    } catch (error) {
        console.error('Ошибка при загрузке файла:', error);
        alert('Ошибка при загрузке файла: ' + error.message);
    }
}

// Пример использования с input type="file":
document.getElementById('storyImage').addEventListener('change', function(event) {
    const file = event.target.files[0];
    uploadFile(file);
});

function addStoryFun(event) {
    event.preventDefault();

    const storyTitle = document.getElementById('storyTitle').value;
    const storyCity = document.getElementById('storyCity').value;
    const storyText = document.getElementById('storyText').value;
    // const storyImage = document.getElementById('storyImage').files[0].name;
    const storyImage = document.getElementById('hiddenImageUrl').value;

    // Массив для хранения отмеченных пользователей
    const taggedUsers = [];

    // Для каждого выбранного ID пользователя найдем полную информацию о пользователе
    selectedUsers.forEach(userId => {
        const user = users.find(u => u.id === userId);
        if (user) {
            // Получаем имя пользователя из объекта данных
            const userName = user.name || user.username || `${user.firstName} ${user.lastName}`;
            taggedUsers.push({ name: userName });
        }
    });

    const storyData = {
        head: storyTitle,
        img: storyImage,
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
                // Токен истёк или недействителен - пробуем обновить
                const newToken = await refreshAccessToken();
                if (!newToken) return; // если не удалось обновить - выход
                addStoryFun();
            }
            if (!response.ok) {
                throw new Error('Ошибка при отправке истории: ' + response.status);
            }
            return response.json();

        })
        .then(data => {
            console.log('История успешно создана:', data);
            // Очищаем форму или перенаправляем пользователя
            document.getElementById('addStoryForm').reset();
            // Очищаем теги пользователей
            document.getElementById('selectedTags').innerHTML = '';
            document.getElementById('selectedUsersContainer').innerHTML = '';

            // Показываем сообщение об успехе
            alert('История успешно создана!');

            // Можно перенаправить на страницу с историями
            // window.location.href = '/stories';
        })
        // .catch(error => {
        //     console.error('Ошибка:', error);
        //
        //     // Добавьте детальное логирование
        //     if (error.response) {
        //         // Сервер ответил с кодом ошибки
        //         console.error('Ответ сервера:', error.response.status);
        //         console.error('Данные ошибки:', error.response.data);
        //     } else if (error.request) {
        //         // Запрос был сделан, но ответа не получено
        //         console.error('Запрос был отправлен, но ответа не получено:', error.request);
        //     } else {
        //         // Что-то еще вызвало ошибку
        //         console.error('Сообщение ошибки:', error.message);
        //     }
        //
        //     alert('Произошла ошибка при создании истории. Пожалуйста, попробуйте снова.');
        // });

    // Возвращаем false, чтобы форма не отправлялась
    return false;
}
