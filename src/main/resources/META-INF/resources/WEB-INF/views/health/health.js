document.addEventListener("DOMContentLoaded", function() {
    function bytesToMB(bytes) {
        return (bytes / (1024 * 1024)).toFixed(2);
    }

    function createStatusRow(name, status, details, description, isParent = false) {
        const statusClass = status === 'UP' ? 'status-up' : (status === 'UNKNOWN' ? 'status-unknown' : 'status-down');
        let detailsHtml = '<ul>';
        if (details) {
            for (const [key, value] of Object.entries(details)) {
                if (key === 'total' || key === 'free') {
                    detailsHtml += `<li><strong>${key}:</strong> ${bytesToMB(value)} MB</li>`;
                } else if (key !== 'status') {
                    detailsHtml += `<li><strong>${key}:</strong> ${value}</li>`;
                }
            }
        }
        detailsHtml += '</ul>';

        const nameHtml = isParent ? `<span class="bold">${name}</span>` : name;
        const indentClass = !isParent ? 'nested-table' : '';
        const row = `
        <tr class="${indentClass}">
            <td>${nameHtml}</td>
            <td class="${statusClass}">${status}</td>
            <td>${detailsHtml}</td>
            <td>${description || ''}</td>
        </tr>`;
        return row;
    }

    function renderStatus(data, container) {
        let rowsHtml = '';
        for (const [key, value] of Object.entries(data)) {
            if (value.details) {
                const status = value.details.status || 'UNKNOWN';
                const description = value.details.description || '';
                rowsHtml += createStatusRow(key, status, value.details, description, true);
            }
        }
        container.innerHTML = rowsHtml;
    }

    function refreshDashboard() {
        const container = document.getElementById('statusTableBody');
        container.innerHTML = '';
        fetch(`${new URL(document.URL).origin}/api/v1/health/`  )
            .then(response => response.json())
            .then(data => renderStatus(data, container));
    }

    refreshDashboard();
});