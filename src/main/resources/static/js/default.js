function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}


window.refreshAccessToken = async function() {
    const refreshToken = getCookie('refreshToken');
    if (!refreshToken) {
        window.location.href = "/login";
        return null;
    }
    try {
        const response = await fetch('http://localhost:8080/auth/refresh', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ refreshToken })
        });

        if (!response.ok) {
            localStorage.removeItem('accessToken');
            document.cookie = 'refreshToken=; Path=/; Secure; SameSite=Strict; Max-Age=0';
            window.location.href = "/login";
            alert('Сессия истекла. Пожалуйста, войдите снова');
            return null;
        }
        const data = await response.json();
        localStorage.setItem('accessToken', data.accessToken);
        return data.accessToken;
    } catch (error) {
        console.error('Ошибка обновления токена:', error);
        window.location.href = "/login";
        return null;
    }
}


document.addEventListener('DOMContentLoaded', async function() {
    const logoutBtn = document.querySelector('.logout-link');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async function() {
            const refreshToken = getCookie('refreshToken');
            if (!refreshToken) {
                window.location.href = "/login";
                return null;
            }
            const response = await fetch('http://localhost:8080/auth/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain'
                },
                body: refreshToken
            });
            if (!response.ok) {
                console.error(response);
            }
            localStorage.removeItem('accessToken');
            document.cookie = 'refreshToken=; Path=/; Secure; SameSite=Strict; Max-Age=0';
            window.location.href = '/login';
        });
    }
});
