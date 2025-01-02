function createStatusRow(name, status) {
    const statusClass = status === 'UP' ? 'status-up' : 'status-down';
    return `
        <tr>
            <td>${name}</td>
            <td class="${statusClass}">${status}</td>
        </tr>`;
}

function createAlertRow(name, status, alertExists) {
    const alertButton = alertExists ?
        `<button class="btn btn-info" disabled>Alert Exists</button>
         <button class="btn btn-danger delete-alert" onclick="deleteAlert('${name}')">❌ Delete Alert</button>` :
        `<button class="btn btn-warning" onclick="createAlert('${name}')">Create Alert</button>`;
    return `
        <tr>
            <td>${name}</td>
            <td class="${status === 'UP' ? 'text-success' : 'text-danger'}">${status}</td>
            <td>${alertButton}</td>
        </tr>`;
}

function renderStatus(data) {
    const container = document.getElementById('statusTableBody');
    container.innerHTML = '';

    data.forEach(item => {
        const name = item.name || 'Unknown';
        const status = item.status || 'UNKNOWN';
        const alertExists = item.alert || false;
        container.innerHTML += createAlertRow(name, status, alertExists);
    });
}

function createAlert(serviceName) {
    fetch(new URL(document.URL).origin + "/api/v1/health/alert", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "alert": serviceName
        }),
    }).then(data => {
          refreshDashboard();
    })
      .catch(error => {
          console.error('Error creating alert:', error);
    });
}

function deleteAlert(serviceName) {
    fetch(new URL(document.URL).origin + "/api/v1/health/alert", {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "alert": serviceName
        }),
    }).then(data => {
          refreshDashboard();
    })
      .catch(error => {
          console.error('Error creating alert:', error);
    });
}

function refreshDashboard() {
    fetch(new URL(document.URL).origin + "/api/v1/health/")
        .then(response => response.json())
        .then(data => {
            renderStatus(data);
        })
        .catch(error => {
            console.error('Error fetching health data:', error);
        });
}


refreshDashboard();
