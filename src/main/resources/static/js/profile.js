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
function handleChangePassword(event) {
    event.preventDefault();

    const current = document.getElementById('currentPassword').value;
    const newPass = document.getElementById('newPassword').value;
    const confirm = document.getElementById('confirmPassword').value;

    if (newPass !== confirm) {
        alert('Новый пароль и подтверждение не совпадают!');
        return false;
    }

    // Здесь можно добавить логику отправки на сервер
    alert('Пароль успешно изменён!');

    closePasswordModal();
    return false;
}

// Модальное окно выбора аватара
const avatarModal = document.getElementById('avatarModal');
const avatarImg = document.getElementById('avatar');
const avatarGrid = document.getElementById('avatarGrid');
const avatarOptions = avatarGrid.querySelectorAll('img');

// Открыть модалку выбора аватара при клике на аватар
document.querySelector('.avatar-wrapper').addEventListener('click', () => {
    avatarModal.classList.add('active');
});

// Закрыть модалку выбора аватара
function closeAvatarModal() {
    avatarModal.classList.remove('active');
}

// Выбор аватара из списка
avatarOptions.forEach(img => {
    img.addEventListener('click', () => {
        const selectedSrc = img.getAttribute('data-avatar');
        avatarImg.src = selectedSrc;

        // Обновляем выделение
        avatarOptions.forEach(i => i.classList.remove('selected'));
        img.classList.add('selected');

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

const scrollAmount = 320; // на сколько пикселей листать (примерно ширина карточки + gap)

const container = document.querySelector('.posts-container');
const btnLeft = document.querySelector('.scroll-btn.left');
const btnRight = document.querySelector('.scroll-btn.right');

btnLeft.addEventListener('click', () => {
    container.scrollBy({
        left: -scrollAmount,
        behavior: 'smooth'
    });
});

btnRight.addEventListener('click', () => {
    container.scrollBy({
        left: scrollAmount,
        behavior: 'smooth'
    });
});