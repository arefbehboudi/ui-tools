function refreshDashboard() {

    const apiPath = "/api/v1/quartz-scheduler/";

    var url = new URL(document.URL).origin + apiPath;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const jobsTableBody = document.getElementById('jobsTableBody');
            jobsTableBody.innerHTML = ''; // Clear existing data

            data.jobs.forEach(job => {
                const row = document.createElement('tr');

                const jobNameCell = document.createElement('td');
                jobNameCell.textContent = job.name;
                row.appendChild(jobNameCell);

                const triggerNameCell = document.createElement('td');
                triggerNameCell.textContent = job.trigger;
                row.appendChild(triggerNameCell);

                const statusCell = document.createElement('td');
                statusCell.textContent = job.status;
                statusCell.classList.add('status-' + job.status.toLowerCase());
                row.appendChild(statusCell);

                const nextFireTimeCell = document.createElement('td');
                nextFireTimeCell.textContent = new Date(job.nextFireTime).toLocaleString();
                row.appendChild(nextFireTimeCell);

                const previousFireTimeCell = document.createElement('td');
                previousFireTimeCell.textContent = new Date(job.previousFireTime).toLocaleString();
                row.appendChild(previousFireTimeCell);

                jobsTableBody.appendChild(row);
            });

            const triggersTableBody = document.getElementById('triggersTableBody');
            triggersTableBody.innerHTML = ''; // Clear existing data

            data.triggers.forEach(trigger => {
                const row = document.createElement('tr');

                const triggerNameCell = document.createElement('td');
                triggerNameCell.textContent = trigger.name;
                row.appendChild(triggerNameCell);

                const jobNameCell = document.createElement('td');
                jobNameCell.textContent = trigger.jobName;
                row.appendChild(jobNameCell);

                const statusCell = document.createElement('td');
                statusCell.textContent = trigger.status;
                statusCell.classList.add('status-' + trigger.status.toLowerCase());
                row.appendChild(statusCell);

                const nextFireTimeCell = document.createElement('td');
                nextFireTimeCell.textContent = new Date(trigger.nextFireTime).toLocaleString();
                row.appendChild(nextFireTimeCell);

                const previousFireTimeCell = document.createElement('td');
                previousFireTimeCell.textContent = new Date(trigger.previousFireTime).toLocaleString();
                row.appendChild(previousFireTimeCell);

                triggersTableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching scheduler data:', error);
        });
}

refreshDashboard();
