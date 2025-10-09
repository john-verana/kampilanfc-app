(() => {
    // ---------- Config (flip USE_MOCK to false when backend is ready) ----------
    const CONFIG = {
        USE_MOCK: true,
        API_BASE: '/api/users',
        STORAGE_KEY: 'k championnat.users:v1', // version your storage keys
        CURRENT_USER_KEY: 'k championnat.currentUser',
        REDIRECT_AFTER_MS: 1500,
        LOADER_MIN_MS: 3000, // keep your 3s visual confirmation
    };

    // ---------- DOM ----------
    const form = document.getElementById('registerForm');
    const usernameInput = document.getElementById('username');
    const loader = document.getElementById('loader');
    const message = document.getElementById('message');

    // ---------- Utils ----------
    const delay = (ms) => new Promise((r) => setTimeout(r, ms));
    const usernameRegex = /^[a-zA-Z0-9._]{3,20}$/;

    function setMsg(text) {
        message.textContent = text;
        message.className = 'message fade-in';
    }

    function fadeOutMsg() {
        message.classList.remove('fade-in');
        message.classList.add('fade-out');
    }

    function readUsers() {
        try {
            return JSON.parse(localStorage.getItem(CONFIG.STORAGE_KEY) || '{}');
        } catch {
            return {};
        }
    }

    function writeUsers(map) {
        localStorage.setItem(CONFIG.STORAGE_KEY, JSON.stringify(map));
    }

    function setCurrentUser(username) {
        const payload = { username, lastLoginAt: Date.now() };
        localStorage.setItem(CONFIG.CURRENT_USER_KEY, JSON.stringify(payload));
    }

    // ---------- API Layer (mock + real share same shape) ----------
    async function apiRegister(username) {
        if (CONFIG.USE_MOCK) {
            // MOCK: emulate server work + user existence check
            await delay(300); // tiny network feel separate from loader
            const users = readUsers();
            const exists = Boolean(users[username]);

            if (!exists) {
                users[username] = { createdAt: Date.now(), logins: 0 };
                writeUsers(users);
            } else {
                users[username].logins = (users[username].logins || 0) + 1;
                writeUsers(users);
            }

            return {
                ok: true,
                isNew: !exists,
                message: exists ? 'User already exists.' : 'Registered.',
            };
        }

        // REAL backend path (ready when you are)
        const res = await fetch(`${CONFIG.API_BASE}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username }),
        });
        const text = await res.text();
        // Normalize backend text to our shape
        const isNew = /welcome(?! back)/i.test(text);
        const exists = /already exists/i.test(text);
        return { ok: res.ok, isNew, message: text, exists };
    }

    // ---------- Submit Flow ----------
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        message.className = 'message';
        const raw = (usernameInput.value || '').trim();

        // Validate early (production-friendly message)
        if (!usernameRegex.test(raw)) {
            setMsg(
                'Please use 3–20 characters: letters, numbers, dot (.) or underscore (_).'
            );
            return;
        }

        // Show loader for at least LOADER_MIN_MS to confirm visually
        loader.style.display = 'block';
        const loaderPromise = delay(CONFIG.LOADER_MIN_MS);

        try {
            const result = await apiRegister(raw);
            await loaderPromise; // ensure loader shows long enough
            loader.style.display = 'none';

            if (!result.ok) {
                setMsg('Registration failed. Please try again.');
                return;
            }

            // Unified UX copy
            const welcome = result.isNew ? 'Welcome to Kampilan!' : 'Welcome back!';
            setMsg(welcome);

            // Persist current user (dashboard can greet)
            setCurrentUser(raw);

            // Fade out then redirect
            await delay(CONFIG.REDIRECT_AFTER_MS);
            fadeOutMsg();
            await delay(700);
            window.location.href = '/pages/dashboard.html';
        } catch (err) {
            loader.style.display = 'none';
            setMsg('Server error. Please try again later.');
            // optional: console.error(err);
        }
    });
})();
