const signInBtn = document.getElementById("signIn");
const signUpBtn = document.getElementById("signUp");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");

signInBtn.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
});

signUpBtn.addEventListener("click", () => {
    container.classList.add("right-panel-active");
});

fistForm.addEventListener("submit", async function(event) {
    event.preventDefault();

    const name = document.getElementById('name1').value;
    const password = document.getElementById('password11').value;
    const password2 = document.getElementById('password12').value;

    if (password != password2) {
        const errorMessageElement = document.getElementById('error-message1');
        errorMessageElement.textContent = 'Passwords don\'t match';
    }
    else {
        const userData = {
            name: name,
            password: password
        };

        try {
            const response = await fetch('/auth/signUp', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            });

            if (response.status === 201) {
                try {
                    const response = await fetch('/auth/signIn', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(userData)
                    });

                    if (response.ok) {
                        const { accessToken, refreshToken } = await response.json();

                        // Сохраняем accessToken в localStorage
                        localStorage.setItem('accessToken', accessToken);

                        // Сохраняем refreshToken в cookie
                        const maxAge = 24 * 3600; // 1 день в секундах
                        document.cookie = `refreshToken=${refreshToken}; Path=/; Secure; SameSite=Strict; Max-Age=${maxAge}`;
                        window.location.href = '/';
                    }
                } catch (error) {
                    console.error('Ошибка при выполнении запроса:', error);
                }
            } else if (response.status === 409) {
                const errorMessageElement = document.getElementById('error-message1');
                errorMessageElement.textContent = 'A user with that name already exists';
            }
        } catch (error) {
            console.error('Ошибка при выполнении запроса:', error);
        }
    }
});

secondForm.addEventListener("submit", async function(event) {
    event.preventDefault();

    const name = encodeURIComponent(document.getElementById('name2').value);
    const password = encodeURIComponent(document.getElementById('password2').value);

    const userData = {
        name: name,
        password: password
    };

    try {
        const response = await fetch('/auth/signIn', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            const { accessToken, refreshToken } = await response.json();

            // Сохраняем accessToken в localStorage
            localStorage.setItem('accessToken', accessToken);

            // Сохраняем refreshToken в cookie
            const maxAge = 24 * 3600; // 1 день в секундах
            document.cookie = `refreshToken=${refreshToken}; Path=/; Secure; SameSite=Strict; Max-Age=${maxAge}`;
            window.location.href = '/';
        }
        if (response.status === 401) {
            const errorMessageElement = document.getElementById('error-message2');
            errorMessageElement.textContent = 'Invalid username or password.';
        }
    } catch (error) {
        console.error('Ошибка при выполнении запроса:', error);
    }
});
