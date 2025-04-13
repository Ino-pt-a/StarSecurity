function loadUserData(button) {
    const row = button.closest('tr');

    const [idCell, usernameCell, lastNameCell, ageCell, emailCell, rolesCell] = row.cells;

    const userId = idCell.textContent.trim();
    const username = usernameCell.textContent.trim();
    const lastName = lastNameCell.textContent.trim();
    const age = ageCell.textContent.trim();
    const email = emailCell.textContent.trim();

    // Получение ролей из <span class="role-badge">
    const userRoles = Array.from(rolesCell.querySelectorAll('.role-badge'))
        .map(role => role.textContent.trim());

    const modal = document.querySelector('#editModal');
    modal.querySelector('input[name="id"]').value = userId;
    modal.querySelector('input[name="username"]').value = username;
    modal.querySelector('input[name="lastName"]').value = lastName;
    modal.querySelector('input[name="age"]').value = age;
    modal.querySelector('input[name="email"]').value = email;

    // Выбор ролей в <select multiple>
    const roleSelect = modal.querySelector('select[name="roles"]');
    Array.from(roleSelect.options).forEach(option => {
        option.selected = userRoles.includes(option.textContent.trim());
    });
}
