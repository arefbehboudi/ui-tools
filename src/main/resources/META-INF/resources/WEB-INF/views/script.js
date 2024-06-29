document.addEventListener("DOMContentLoaded", async function () {

    const apiPath = "/api/v1/flyway/";


    var url = new URL(document.URL);

    const data = await getData(url.origin + apiPath);
    const migrationInfo = data["migrationInfo"];
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
    document.getElementById("locations").textContent = locations.join(", ");
    document.getElementById("schemas").textContent = schemas.join(", ");

    const migrationInfoTable = document.getElementById("migrationInfo");
    migrationInfo.forEach(info => {
        const row = document.createElement("tr");
        var date = new Date(info.installedOn)
        var dateFormatted = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear()
        row.innerHTML = `
            <td>${info.version.rawVersion}</td>
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
});