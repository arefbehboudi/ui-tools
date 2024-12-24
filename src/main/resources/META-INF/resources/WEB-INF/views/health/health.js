function bytesToMB(bytes) {
    return (bytes / (1024 * 1024)).toFixed(2);
}

function createStatusRow(name, status, details, description, isParent = false) {
    const statusClass = status === 'UP' ? 'status-up' : (status === 'UNKNOWN' || status === 'DOWN' ? 'status-down' : 'status-unknown');
    let detailsHtml = '<ul>';
    if (details) {
        for (const [key, value] of Object.entries(details)) {
            if (key === 'total' || key === 'free') {
                detailsHtml += `<li><strong>${key}:</strong> ${bytesToMB(value)} MB</li>`;
            } else if (key !== 'status') {
                if (typeof value !== 'object')
                    detailsHtml += `<li><strong>${key}:</strong> ${value}</li>`;
            }
        }
    }
    detailsHtml += '</ul>';

    const nameHtml = isParent ? `<span class="bold">${name}</span>` : name;
    const indentClass = !isParent ? 'nested-table' : '';
    return `
        <tr class="${indentClass}">
            <td>${nameHtml}</td>
            <td class="${statusClass}">${status}</td>
            <td>${detailsHtml}</td>
            <td>${description || ''}</td>
        </tr>`;
}

function renderStatus(data, container, isParent = true, level = 0) {
    let rowsHtml = '';
    for (const [key, value] of Object.entries(data)) {

        if (value && typeof value === 'object') {
            if (value.details) {
                const status = value.details.status || 'UNKNOWN';
                const description = value.details.description || '';
                rowsHtml += createStatusRow(key, status, value.details, description, level === 0);
                rowsHtml += renderStatus(value.details, container, false, level + 1);
            } else if (typeof value === 'object') {
                rowsHtml += createStatusRow(key, value.status || 'UNKNOWN', value, '', level === 0);
                rowsHtml += renderStatus(value, container, false, level + 1);
            }
        }
    }

    return rowsHtml;
}

function refreshDashboard() {
    const container = document.getElementById('statusTableBody');
    container.innerHTML = '';
    fetch(`${new URL(document.URL).origin}/api/v1/health/`)
        .then(response => response.json())
        .then(data => {
            container.innerHTML = renderStatus(data, container);
        });
}

refreshDashboard();
