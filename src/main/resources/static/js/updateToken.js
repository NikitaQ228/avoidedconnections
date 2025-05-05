function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}


async function refreshAccessToken() {
    const refreshToken = getCookie('refreshToken');
    if (!refreshToken) {
        // Нет refreshToken - перенаправляем на логин
        window.location.href = "/login";
        return null;
    }
    try {
        const response = await fetch('/auth/refresh', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ refreshToken })
        });

        if (!response.ok) {
            // Не удалось обновить токен - перенаправляем на логин
            window.location.href = "/login";
            return null;
        }
        const data = await response.json();
        // Сохраняем новый accessToken в localStorage
        localStorage.setItem('accessToken', data.accessToken);
        return data.accessToken;
    } catch (error) {
        console.error('Ошибка обновления токена:', error);
        window.location.href = "/login";
        return null;
    }
}
