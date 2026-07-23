// StaffHub — small progressive-enhancement helpers (no framework required)

document.addEventListener('DOMContentLoaded', function () {

    // Toggle password visibility on any .input-group with a .toggle-visibility button
    document.querySelectorAll('.toggle-visibility').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var input = btn.parentElement.querySelector('input');
            if (!input) return;
            var showing = input.type === 'text';
            input.type = showing ? 'password' : 'text';
            btn.innerHTML = showing ? '<i class="bi bi-eye"></i>' : '<i class="bi bi-eye-slash"></i>';
            btn.setAttribute('aria-label', showing ? 'Show password' : 'Hide password');
        });
    });

    // Confirm before any destructive action
    document.querySelectorAll('[data-confirm]').forEach(function (el) {
        el.addEventListener('click', function (e) {
            var message = el.getAttribute('data-confirm') || 'Are you sure?';
            if (!window.confirm(message)) {
                e.preventDefault();
                e.stopPropagation();
            }
        });
    });

    // Live filter for the staff directory search box (client-side, works
    // alongside server-side filtering once wired up)
    var searchInput = document.querySelector('[data-role="staff-search"]');
    if (searchInput) {
        searchInput.addEventListener('input', function () {
            var term = searchInput.value.trim().toLowerCase();
            document.querySelectorAll('[data-role="staff-row"]').forEach(function (row) {
                var haystack = row.getAttribute('data-search') || '';
                row.style.display = haystack.toLowerCase().indexOf(term) > -1 ? '' : 'none';
            });
        });
    }
});