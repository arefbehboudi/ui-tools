document.addEventListener("DOMContentLoaded", async function () {

    const apiPath = "/api/v1/flyway/";

    var url = new URL(document.URL).origin + apiPath;

    const data = await getData(url);
    const migrationInfo = data["migrationInfo"] === undefined ? [] : data["migrationInfo"];
    const datasource = data["datasource"];
    const encoding = data["encoding"];
    const outOfOrder = data["outOfOrder"];
    const initOnMigrate = data["initOnMigrate"];
    const cleanOnValidationError = data["cleanOnValidationError"];
    const validateOnMigrate = data["validateOnMigrate"];
    const table = data["table"];
    const baselineDescription = data["baselineDescription"];
    const sqlMigrationPrefix = data["sqlMigrationPrefix"];
    const locations = data["locations"];
    const schemas = data["schemas"];


    document.getElementById("datasource").textContent = datasource;
    document.getElementById("encoding").textContent = encoding;
    document.getElementById("initOnMigrate").innerHTML = initOnMigrate ? '<span class="text-success"><i class="fas fa-check"></i></span>' : '<span class="text-danger"><i class="fas fa-times"></i></span>';
    document.getElementById("outOfOrder").innerHTML = outOfOrder ? '<span class="text-success"><i class="fas fa-check"></i>True</span>' : '<span class="text-danger"><i class="fas fa-times">False</i></span>';
    document.getElementById("cleanOnValidationError").innerHTML = cleanOnValidationError ? '<span class="text-success"><i class="fas fa-check"></i></span>' : '<span class="text-danger"><i class="fas fa-times"></i></span>';
    document.getElementById("validateOnMigrate").innerHTML = validateOnMigrate ? '<span class="text-success"><i class="fas fa-check"></i></span>' : '<span class="text-danger"><i class="fas fa-times"></i></span>';
    document.getElementById("table").textContent = table;
    document.getElementById("baselineDescription").textContent = baselineDescription;
    document.getElementById("sqlMigrationPrefix").textContent = sqlMigrationPrefix;
    document.getElementById("locations").textContent = locations === undefined ? "" : locations.join(", ");
    document.getElementById("schemas").textContent = schemas === undefined ? "" : schemas.join(", ");

    const migrationInfoTable = document.getElementById("migrationInfo");
    migrationInfo.forEach(info => {
        const row = document.createElement("tr");
        var date = new Date(info.installedOn)
        var dateFormatted = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear()
        row.innerHTML = `
            <td>${info.rawVersion}</td>
            <td>${info.description}</td>
            <td>${info.type}</td>
            <td>${info.script}</td>
            <td>${info.state}</td>
            <td>${dateFormatted}</td>
            <td>${Math.floor(info.executionTime / 60)}min</td>
            <td>${info.checksum}</td>
        `;
        migrationInfoTable.appendChild(row);
    });


    document.getElementById('migrateBtn').addEventListener('click', async function() {
        const result = await sendPostRequest( url + 'migrate', {});
        var msg = '';

        if(result["success"])
            msg = `Migrate successfully, Operation: ${result["operation"]}, TotalMigrationTime: ${result["totalMigrationTime"]}`;
        else
            msg = `Migrate failed, ErrorCode: ${result["errorCode"]}, Error: ${result["exception"]}`;

        alert(msg);
    });

    document.getElementById('undoBtn').addEventListener('click', async function() {
        const result = await sendPostRequest( url + 'undo', {});
        var msg = '';

        if(result["success"])
            msg = "Undo successfully";
        else
            msg = `Undo failed, ErrorCode: ${result["errorCode"]}, Error: ${result["exception"]}`;

        alert(msg);
    });

    document.getElementById('repairBtn').addEventListener('click', async function() {
        const result = await sendPostRequest( url + 'repair', {});
        var msg = '';

        if(result["success"])
            msg = "Repair successfully";
        else
            msg = `Repair failed, ErrorCode: ${result["errorCode"]}, Error: ${result["exception"]}`;

        alert(msg);
    });

    document.getElementById('validateBtn').addEventListener('click', async function() {
        const result = await sendPostRequest( url + 'validate', {});
        var msg = '';

        if(result["success"])
            msg = "Validate successfully";
        else
            msg = `Validate failed, ErrorCode: ${result["errorCode"]}, Error: ${result["exception"]}`;

        alert(msg);

    });

    document.getElementById('cleanBtn').addEventListener('click', async function() {
        const result = await sendPostRequest( url + 'clean', {});
        var msg = '';

        if(result["success"])
            msg = "Clean successfully";
        else
            msg = `Clean failed, ErrorCode: ${result["errorCode"]}, Error: ${result["exception"]}`;

        alert(msg);
    });

    async function getData(url) {
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }

            var json = await response.json();
            return json
        } catch (error) {
            throw new Error(error.message);
        }
    }

    async function sendPostRequest(url, data) {
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error:', error);
            return null;
        }
    }
});