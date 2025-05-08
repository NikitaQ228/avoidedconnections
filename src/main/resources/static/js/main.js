async function loadMainInfo({ city, query } = {}) {
    let token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = "/login";
        return;
    }

    const url = new URL("/mainInfo", window.location.origin);
    if (city) {
        url.searchParams.set("city", city);
    }
    if (query) {
        url.searchParams.set("query", query);
    }

    const url_str = url.pathname + url.search;
    console.log(url_str);

    let response = await fetch(url_str, {
        headers: { "Authorization": "Bearer " + token }
    });

    if (response.status === 403) {
        // Токен истёк или недействителен - пробуем обновить
        const newToken = await refreshAccessToken();
        if (!newToken) return; // если не удалось обновить - выход
        loadMainInfo();
    }
    if (response.ok) {
        let result = await response.json();
        const container = document.querySelector('.posts-container');
        if (!container) return;
        if (result.length === 0) {
            container.innerHTML = '<p style="color: white; font-size: 48px; font-weight: bold; text-align: center; margin: 0; padding: 20px;">Ничего не найдено</p>';
        } else {
            container.innerHTML = '';
            result.forEach(story => {
                createStory(story)
            });
        }
    }
}

function createStory(postData) {
    const container = document.querySelector('.posts-container');
    if (!container) return;

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

window.onload = loadMainInfo;

document.getElementById('searchForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // предотвращаем стандартную отправку формы

    // Получаем значения полей
    const form = event.target;
    const query = form.query.value.trim();
    const city = form.city.value.trim();

    loadMainInfo({ query, city });
});